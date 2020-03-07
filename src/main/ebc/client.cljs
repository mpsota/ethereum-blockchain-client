(ns ebc.client
  (:require
    [ebc.devtools :as devtools]
    [re-frame.core :as rf]
    ["web3" :as Web3]
    ["web3-eth-personal" :as w3-personal]
    ["web3-eth" :as w3-eth]))

(defonce web3 (atom nil))
(defonce balance (atom nil))
(defonce local-accounts (atom []))



;(defonce web3 (atom nil))



(defonce receiver "0xE15cEDc7fBe317eE989B57d7229149bE3210DF71") ;; debug only
(defonce address "0xDF769Af9BEEB64c8F2e9682867061129E94B0fB2") ;; debug only




#_(client/send-transaction (:web3 @re-frame.db/app-db) (:current-account @re-frame.db/app-db)
                           (:receiver (:send-directly-form @re-frame.db/app-db))
                           (:amount (:send-directly-form @re-frame.db/app-db)))

(defn get-accounts [fn]
      (.getAccounts (.-eth @web3) fn))


; (.getBalance (.-eth web3) address (fn [e v] (js/console.log (.fromWei (.-utils web3) v))))

(defn start []
      (connect)
      (get-accounts (fn [_e accounts]
                        (print accounts _e)
                        (reset! local-accounts accounts))))

#_(def f (w3/eth web3 address (fn [_e bal]
                                 (println bal)
                                 #_(reset! @balance bal))))
