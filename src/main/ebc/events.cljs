(ns ebc.events
  (:require [ebc.db :as db]
            [re-frame.core :as rf]
            [ebc.web3-client :as client]))

;(def foo (client/slurp "/tmp/foo"))

(rf/reg-event-db
  :initialize
  (fn [_ _]
      {:web3 nil
       :text "Nothing"
       :accounts ["test"]
       :current-account ""
       :balance 0
       :send-directly-form {:receiver "0xE15cEDc7fBe317eE989B57d7229149bE3210DF71"
                            :amount "0.001"}}))

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
      (assoc-in db [form-name field-name] new-value)))

(rf/reg-event-fx
  :send-form
  (fn [{:keys [db]} [_ form-name]]
      (js/console.log "send-form" [(:web3 db) (:current-account db) (:receiver (get db form-name)) (:amount (get db form-name))])
      {:send-money [(:web3 db) (:current-account db) (:receiver (get db form-name)) (:amount (get db form-name))]
       :db db}))

(rf/reg-fx
  :send-money
  (fn [[web3 sender receiver amount]]
      (js/console.log "Send" web3 receiver amount)
      (client/send-transaction web3 sender receiver amount)))

(rf/reg-event-fx
  :set-current-account
  (fn [{:keys [db]} [_ account]]
      {:get-balance-fx {:web3 (:web3 db)
                        :account account}
       :db (assoc db :current-account account)}))

(rf/reg-fx
  :get-balance-fx
  (fn [value] ;; add spec
      (.getBalance (.-eth (:web3 value)) (:account value)
                   (fn [e v]
                       (let [balance (.fromWei (.-utils (:web3 value)) v)]
                            (rf/dispatch [:get-balance balance])
                            (js/console.log balance))))))

(rf/reg-event-db
  :get-balance
  (fn [db [_ balance]]
      (assoc db :balance balance)))
