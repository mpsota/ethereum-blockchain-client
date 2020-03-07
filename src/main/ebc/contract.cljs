(ns ebc.contract)


(def contract-address "0xc04Ad234E01327b24a831e3718DBFcbE245904CC")
(def contract-abi [])
;;Create Stream  <250K
;Withdraw from Stream ;<90K
;Cancel Stream <120K
;Create "Compounding" Stream <475K
;Withdraw from "Compounding" Stream <280K
;Cancel "Compounding" Stream <200K

; (def contract (.-Contract (.-eth web3) contract-abi contract-address))
; (.methods contract)
; (.call (.name ..

const sablier = new web3.eth.Contract(0xabcd..., sablierABI);
const recipient = 0xcdef...;
const deposit = "2999999999999998944000";
const now = Math.round(new Date().getTime() / 1000);
const startTime = now + 3600;
const stopTime = now + 2592000 + 3600;

const token = new web3.eth.Contract(0xcafe..., erc20ABI);
const approveTx = await token.methods.approve(sablier.options.address, deposit).send();

const createStreamTx = await sablier.methods.createStream(recipient, deposit, token.address, startTime, stopTime).send();