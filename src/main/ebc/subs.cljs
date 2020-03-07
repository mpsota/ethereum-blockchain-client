(ns ebc.subs
  (:require [re-frame.core :as rf]))

(rf/reg-sub
  :text
  (fn [db _]

      (:text db)))

(rf/reg-sub
  :web3
  (fn [db _]
      (:web3 db)))

(rf/reg-sub
  :accounts
  (fn [db _]
      (map (fn [acc]
               {:key acc :value acc :text acc})
           (:accounts db))))

#_(rf/reg-sub
  :current-account
  (fn [db _]
      (.getBalance (.-eth (:web3 db)) (:current-account db) (fn [e v]
                                                                (js/console.log (.fromWei (.-utils (:web3 db)) v))))))

(rf/reg-sub
  :balance
  (fn [db _]
      (:balance db)))

(rf/reg-sub
  :field-value
  (fn [db [_ form-name field-name]]
      (get-in db [form-name field-name])))