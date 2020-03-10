(ns ebc.web3-client
  (:require
    ["web3" :as Web3]
    ["web3-eth-personal" :as w3-personal]
    ["web3-eth" :as w3-eth]))

(defn connect
      "Connect using MetaMask in the browser"
      []
      (js/window.ethereum.enable)
      (let [web3 (new Web3)]
           (.setProvider web3 Web3/givenProvider)
           web3))

(defn set-default-address
      "Sets default address to use to pay for gas"
      [sender-address]
      (set! (.-defaultAccount (.-eth web3)) sender-address))


(defn convert-to-wei [web3 value]
      (.toWei (.-utils web3) value "ether"))


(defn send-transaction [web3 sender receiver value]
      (.sendTransaction (.-eth web3) #js{:from sender
                                         :to receiver
                                         :value (convert-to-wei web3 (str value))}))



