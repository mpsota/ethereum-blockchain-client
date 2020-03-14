(ns ebc.subs
  (:require [re-frame.core :as rf]))

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

(rf/reg-sub
  :balance
  (fn [db _]
    (:balance db)))

(rf/reg-sub
  :field-value
  (fn [db [_ form-name field-name]]
    (get-in db [form-name field-name])))

(rf/reg-sub
  :get-db-value
  (fn [db [_ field-name]]
    (get-in db [field-name])))

(rf/reg-sub
  :errors
  (fn [db _]
    (->> (map js/JSON.stringify (:errors db))
         (interpose [:br]))))