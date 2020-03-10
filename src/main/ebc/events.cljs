(ns ebc.events
  (:require [ebc.db :as db]
            [re-frame.core :as rf]
            [ebc.web3-client :as client]
            [ebc.contract :as contract]))

;(def foo (client/slurp "/tmp/foo"))

(rf/reg-event-db
  :initialize
  (fn [_ _]
      {:web3 nil
       :text "Nothing"
       :transaction-hash ""
       :errors []
       :accounts []
       :current-account ""
       :receiver "0xE15cEDc7fBe317eE989B57d7229149bE3210DF71"
       :gas-limit "10000000"
       :balance 0
       :dai-balance 0
       :send-directly-form {:amount "0.001"}
       :approve-sablier-contract-form {:allowed-amount "0"}
       :create-sablier-stream-form {:deposit "3600"
                                    :time-since-now "3600"
                                    :duration "600"}
       :stream-transaction-hash nil
       :approve-transaction-hash nil}))

(rf/reg-event-db
  :connect-web3
  (fn [db [_ _]]
      (rf/dispatch [:set-web3 (client/connect)])
      db))

(rf/reg-event-db
  :send-text
  (fn [db [_ new-text]]
      (assoc db :text new-text)))

(rf/reg-event-db
  :set-web3
  (fn [db [_ web3]]
      (assoc db :web3 web3)))

(rf/reg-event-db
  :get-accounts
  (fn [db [_ accounts]]
      (assoc db :accounts accounts)))

(rf/reg-event-db
  :set-field-value
  (fn [db [_ form-name field-name new-value]]
      (js/console.log new-value)
      (assoc-in db [form-name field-name] new-value)))

(rf/reg-event-db
  :set-db-value
  (fn [db [_ field-name new-value]]
      (assoc-in db [field-name] new-value)))

(rf/reg-event-fx
  :send-form
  (fn [{:keys [db]} [_ form-name]]
      (case form-name
            :send-directly-form {:send-money [(:web3 db)
                                              (:current-account db)
                                              (:receiver db)
                                              (:amount (get db form-name))]
                                 :db db}
            :approve-sablier-contract-form {:approve-sablier-contract [(:web3 db)
                                                                       (:current-account db)
                                                                       (:allowed-amount (get db form-name))
                                                                       (:gas-limit db)]}
            :create-sablier-stream-form {:create-sablier-stream [(:web3 db)
                                                                 (:current-account db)
                                                                 (:receiver db)
                                                                 (:deposit (get db form-name))
                                                                 (:time-since-now (get db form-name))
                                                                 (:duration (get db form-name))]})))

(rf/reg-fx
  :send-money
  (fn [[web3 sender receiver amount]]
      (js/console.log "Send" web3 receiver amount)
      (client/send-transaction web3 sender receiver amount)))

(rf/reg-fx
  :approve-sablier-contract
  (fn [[web3 sender amount gas-limit]]
      (js/console.log "Approve sablier contract" web3 sender amount gas-limit)
      (let [transaction-hash-fn #(rf/dispatch [:set-db-value :approve-transaction-hash %1])
            error-fn #(rf/dispatch [:add-error %1])
            receipt-fn #(js/console.log "Receipt:" %1)]
      (contract/approve-dai-sablier-contract web3 sender amount gas-limit transaction-hash-fn
                                             receipt-fn error-fn)

      )))

(rf/reg-fx
  :create-sablier-stream
  (fn [[web3 sender receiver deposit time-since-now duration]]
      (js/console.log web3 sender receiver deposit time-since-now duration)
      (let [transaction-hash-fn #(rf/dispatch [:set-db-value :stream-transaction-hash %1])
            error-fn #(rf/dispatch [:add-error %1])
            receipt-fn #(js/console.log "Receipt:" %1)]
           (contract/create-sablier-stream web3 sender receiver deposit time-since-now duration transaction-hash-fn receipt-fn error-fn))))

(rf/reg-event-fx
  :set-current-account
  (fn [{:keys [db]} [_ account]]
      {:set-default-address {:web3 (:web3 db)
                             :account account}
       :get-balance-fx {:web3 (:web3 db)
                        :account account}
       :db (assoc db :current-account account)}))

(rf/reg-fx
  :get-balance-fx
  (fn [value] ;; add spec
      ;; set dai balance
      (let [error-fn (fn [e v]
                         (js/console.log e)
                         (when e
                               (rf/dispatch [:add-error e]))
                         (when v
                               (rf/dispatch [:set-db-value :dai-balance v])))]
           (contract/get-dai-balance (:web3 value) (:account value) (:gas-limit value) error-fn))
      ;; set eth balance
      (.getBalance (.-eth (:web3 value)) (:account value)
                   (fn [e v]
                       (let [balance (.fromWei (.-utils (:web3 value)) v)]
                            (rf/dispatch [:get-balance balance])
                            (js/console.log balance))))))

(rf/reg-fx
  :set-default-address
  (fn [value]
      (client/set-default-address (:web3 value) (:account value))))

(rf/reg-event-db
  :get-balance
  (fn [db [_ balance]]
      (assoc db :balance balance)))

(rf/reg-event-db
  :set-transaction-hash
  (fn [db [_ hash]]
      (assoc db :transaction-hash hash)))

(rf/reg-event-db
  :add-error
  (fn [db [_ error]]
      (js/console.log "Add error:" error)
      (update-in db [:errors] #(conj %1 error))))