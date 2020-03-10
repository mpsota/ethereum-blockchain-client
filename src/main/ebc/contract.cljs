(ns ebc.contract
  (:require [ebc.sablier :refer [sablier-abi erc20-abi test-dai-abi]]
            ["web3" :as Web3]
            [promesa.core :as p]))
;; To get free test DAI you should mint contract at https://rinkeby.etherscan.io/address/0xc3dbf84abb494ce5199d5d4d815b10ec29529ff8#writeContract
;; source: https://twitter.com/PaulRBerg/status/1198276654566723584

(def sablier-contract-address "0xc04Ad234E01327b24a831e3718DBFcbE245904CC") ;;sablier
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

(defn connect []
      (js/window.ethereum.enable)
      (let [web3 (new Web3)]
           (.setProvider web3 Web3/givenProvider)
           (set! (.-defaultAccount (.-eth web3)) address) ;; move to function called after selection
           web3))

(defn make-sablier-contract [web3]
  (let [eth (.-eth web3)]
       (.setProvider web3 Web3/givenProvider)
       (new (.-Contract eth) sablier-abi sablier-contract-address)
       ))

(defn make-erc20-contract [web3 sender amount]
      (let [eth (.-eth web3)]
           (.setProvider web3 Web3/givenProvider)
           (let [contract (new (.-Contract eth) test-dai-abi dai-token-address #js{:from sender
                                                                                   :gas gas-limit})]
                (.send (.approve (.-methods contract) sablier-contract-address amount)
                       (fn [e r]
                           (js/console.log e r)))))) ;;allowance?

(defn make-erc20-contract-old [web3]
      (let [;web3 (new Web3)
            eth (.-eth web3)]
           (.setProvider web3 Web3/givenProvider)
           (new (.-Contract eth) test-dai-abi dai-token-address #js{:from address
                                                                    :gas gas-limit})
           ))
#_(doto (.send (.approve (.-methods (make-erc20-contract (:web3 @re-frame.db/app-db))) contract-address "3600"))
      (.on "receipt" js/console.log)
      (.on "error" js/console.log))

(defn create-sablier-stream [web3 token-address]
      (let [now (Math/round (/ (.getTime (new js/Date.)) 1000))
            deposit "3600" ;; must be a multiple of the difference between the stop time and the start time,
            start-time (+ now 3600)
            end-time (+ 600 start-time)]
           (.send (.createStream (.-methods (make-sablier-contract web3))
                                 receiver deposit token-address start-time end-time)
                  #js{:from address :gas gas-limit})
           ))
;(.-address c)
; (js/console.log (.-createStream (.-methods (make-sablier-contract))))

     ;(make-sablier-contract (:web3 @re-frame.db/app-db))
     ;(def contract (Contract (.-eth (:web3 @re-frame.db/app-db)) contract-address sablier-abi))

     ;const sablier = new web3.eth.Contract(0xabcd..., sablierABI);
     ;const recipient = 0xcdef...;
     ;const deposit = "2999999999999998944000";
     ;const now = Math.round(new Date().getTime() / 1000);
     ;const startTime = now + 3600;
     ;const stopTime = now + 2592000 + 3600;

     ;const token = new web3.eth.Contract(0xcafe..., erc20ABI);
     ;const approveTx = await token.methods.approve(sablier.options.address, deposit).send();

     ;const createStreamTx = await sablier.methods.createStream(recipient, deposit, token.address, startTime, stopTime).send();