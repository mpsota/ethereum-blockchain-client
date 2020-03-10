(ns ebc.db
  (:require [cljs.spec.alpha :as s]
            [re-frame.core :as rf]))

;; TODO finish spec, and add spec valid? as interceptor

(s/def ::account string?)
(s/def ::number-str (s/and
                      string?
                      #(number? (js/Number %))))
(s/def ::gas-limit ::number-str)
(s/def ::balance ::number-str)
(s/def ::dai-balance ::number-str)

(s/def ::error (s/keys req [ ::message ::stack]))
(s/def ::errors (s/* ::error))
(s/def ::approve-transaction-hash string?)
(s/def ::stream-transaction-hash string?)

(rf/reg-event-db
  :initialize
  (fn [_ _]
      {:web3 nil
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


