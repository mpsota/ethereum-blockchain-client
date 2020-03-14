(ns ebc.contract
  (:require [ebc.sablier :refer [sablier-abi test-dai-abi]]
            ["web3" :as Web3]
            [promesa.core :as p]))

;; To get free test DAI you should mint contract at https://rinkeby.etherscan.io/address/0xc3dbf84abb494ce5199d5d4d815b10ec29529ff8#writeContract
;; source: https://twitter.com/PaulRBerg/status/1198276654566723584

(def sablier-contract-address "0xc04Ad234E01327b24a831e3718DBFcbE245904CC") ;;sablier contract address
(def dai-token-address "0xc3dbf84Abb494ce5199D5d4D815b10EC29529ff8")
(defonce receiver "0xE15cEDc7fBe317eE989B57d7229149bE3210DF71") ;; debug only
(defonce address "0xDF769Af9BEEB64c8F2e9682867061129E94B0fB2") ;; debug only
(def gas-limit 10000000)

;;Create Stream  <250K
;Withdraw from Stream ;<90K
;Cancel Stream <120K
;Create "Compounding" Stream <475K
;Withdraw from "Compounding" Stream <280K
;Cancel "Compounding" Stream <200K

; (def contract (.-Contract (.-eth web3) contract-abi contract-address))
; (.methods contract)
; (.call (.name ..

;; DAI token to approve send

(defn make-dai-token-contract [web3 sender gas-limit]
  (let [eth (.-eth web3)]
    (.setProvider web3 Web3/givenProvider)
    (new (.-Contract eth) test-dai-abi dai-token-address #js{:from sender
                                                             :gas  gas-limit})))


(defn approve-dai-sablier-contract
  "Approves `amount` of dai tokens to be send from `sender` account to sablier-contract-address"
  [web3 sender amount gas-limit transaction-hash-fn receipt-fn error-fn]
  (js/console.log sender amount gas-limit)
  (let [contract (make-dai-token-contract web3 sender gas-limit)
        promise (.send (.approve (.-methods contract) sablier-contract-address amount))]
    (doto promise
      (.on "transactionHash" transaction-hash-fn)
      (.on "receipt" receipt-fn)
      (.on "error" error-fn))))

(defn get-dai-balance [web3 sender gas-limit callback-fn]
  "Return balance (DAI tokens)"
  (let [contract (make-dai-token-contract web3 sender gas-limit)]
    (.call (.balanceOf (.-methods contract) sender) callback-fn)))

;; Stream using Sablier protocol

(defn make-sablier-contract [web3]
  (let [eth (.-eth web3)]
    (.setProvider web3 Web3/givenProvider)
    (new (.-Contract eth) sablier-abi sablier-contract-address)
    ))

(defn create-sablier-stream [web3 sender receiver deposit time-since-now duration transaction-hash-fn receipt-fn error-fn]
  ;; deposit must be a multiple of the difference between the stop time and the start time,
  (let [epoch-now (Math/round (/ (.getTime (new js/Date.)) 1000))
        start-time (+ epoch-now (js/Number time-since-now))
        end-time (+ (js/Number duration) start-time)]
    (js/console.log deposit start-time end-time)
    (let [promise (.send (.createStream (.-methods (make-sablier-contract web3))
                                        receiver deposit dai-token-address start-time end-time)
                         #js{:from sender :gas gas-limit})]
      (doto promise
        (.on "transactionHash" transaction-hash-fn)
        (.on "receipt" receipt-fn)
        (.on "error" error-fn)))))

;; debug functions

(def tx "0x047dabc9d3ed35d2d5e29f8e760e73a81b54bdb51a05ac4dce958bedee57b863")

(defn withdraw-from-stream [web3]
  (.call (.withdrawFromStream (.-methods (make-sablier-contract web3))
                              "22"
                              "360000060")
         #(js/console.log %2)))

(defn balance-of-stream [web3 owner]
  (.call (.balanceOf (.-methods (make-sablier-contract web3))
                     "22"
                     owner)
         #(js/console.log %2)))

(defn delta-of [web3 id]
  (.call (.deltaOf (.-methods (make-sablier-contract web3))
                   id)
         #(js/console.log %2)))
