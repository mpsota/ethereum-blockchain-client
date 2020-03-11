# ethereum-blockchain-client
Server-less application using Sablier, the protocol for real-time finance on the Ethereum blockchain
## Goals
- Deploy a static website
- Connected to the Ethereum blockchain Rinkeby via MetaMask
- Allowing a user to stream `n` Token to an address `a` over `h` hours
## Usage
To use this app you need:
- MetaMask plugin in your browser
- Ethereum Rinkeby testnet account
- test ETH to cover gas cost (get it from: https://faucet.rinkeby.io/ ) 
- TestDAI tokens to test the streaming features (you can mint them at: https://rinkeby.etherscan.io/address/0xc3dbf84abb494ce5199d5d4d815b10ec29529ff8#writeContract ) 

When ready just do:
```
$ npm install
$ npx shadow-cljs watch app
enter http://localhost:8000
```
## Demo
- https://mpsota.github.io/ethereum-blockchain-client/index.html