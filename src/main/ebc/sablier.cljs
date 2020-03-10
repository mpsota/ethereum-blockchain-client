(ns ebc.sablier)

(def sablier-abi-str "[
   {
     \"constant\": true,
     \"inputs\": [],
     \"name\": \"nextStreamId\",
     \"outputs\": [
       {
         \"internalType\": \"uint256\",
         \"name\": \"\",
         \"type\": \"uint256\"
       }
     ],
     \"payable\": false,
     \"stateMutability\": \"view\",
     \"type\": \"function\"
   },
   {
     \"constant\": false,
     \"inputs\": [],
     \"name\": \"unpause\",
     \"outputs\": [],
     \"payable\": false,
     \"stateMutability\": \"nonpayable\",
     \"type\": \"function\"
   },
   {
     \"constant\": true,
     \"inputs\": [
       {
         \"internalType\": \"address\",
         \"name\": \"account\",
         \"type\": \"address\"
       }
     ],
     \"name\": \"isPauser\",
     \"outputs\": [
       {
         \"internalType\": \"bool\",
         \"name\": \"\",
         \"type\": \"bool\"
       }
     ],
     \"payable\": false,
     \"stateMutability\": \"view\",
     \"type\": \"function\"
   },
   {
     \"constant\": true,
     \"inputs\": [],
     \"name\": \"paused\",
     \"outputs\": [
       {
         \"internalType\": \"bool\",
         \"name\": \"\",
         \"type\": \"bool\"
       }
     ],
     \"payable\": false,
     \"stateMutability\": \"view\",
     \"type\": \"function\"
   },
   {
     \"constant\": false,
     \"inputs\": [],
     \"name\": \"initialize\",
     \"outputs\": [],
     \"payable\": false,
     \"stateMutability\": \"nonpayable\",
     \"type\": \"function\"
   },
   {
     \"constant\": false,
     \"inputs\": [
       {
         \"internalType\": \"address\",
         \"name\": \"account\",
         \"type\": \"address\"
       }
     ],
     \"name\": \"addPauser\",
     \"outputs\": [],
     \"payable\": false,
     \"stateMutability\": \"nonpayable\",
     \"type\": \"function\"
   },
   {
     \"constant\": false,
     \"inputs\": [],
     \"name\": \"pause\",
     \"outputs\": [],
     \"payable\": false,
     \"stateMutability\": \"nonpayable\",
     \"type\": \"function\"
   },
   {
     \"constant\": true,
     \"inputs\": [],
     \"name\": \"owner\",
     \"outputs\": [
       {
         \"internalType\": \"address\",
         \"name\": \"\",
         \"type\": \"address\"
       }
     ],
     \"payable\": false,
     \"stateMutability\": \"view\",
     \"type\": \"function\"
   },
   {
     \"constant\": true,
     \"inputs\": [],
     \"name\": \"isOwner\",
     \"outputs\": [
       {
         \"internalType\": \"bool\",
         \"name\": \"\",
         \"type\": \"bool\"
       }
     ],
     \"payable\": false,
     \"stateMutability\": \"view\",
     \"type\": \"function\"
   },
   {
     \"constant\": false,
     \"inputs\": [
       {
         \"internalType\": \"address\",
         \"name\": \"sender\",
         \"type\": \"address\"
       }
     ],
     \"name\": \"initialize\",
     \"outputs\": [],
     \"payable\": false,
     \"stateMutability\": \"nonpayable\",
     \"type\": \"function\"
   },
   {
     \"constant\": true,
     \"inputs\": [],
     \"name\": \"cTokenManager\",
     \"outputs\": [
       {
         \"internalType\": \"contract ICTokenManager\",
         \"name\": \"\",
         \"type\": \"address\"
       }
     ],
     \"payable\": false,
     \"stateMutability\": \"view\",
     \"type\": \"function\"
   },
   {
     \"constant\": true,
     \"inputs\": [],
     \"name\": \"fee\",
     \"outputs\": [
       {
         \"internalType\": \"uint256\",
         \"name\": \"mantissa\",
         \"type\": \"uint256\"
       }
     ],
     \"payable\": false,
     \"stateMutability\": \"view\",
     \"type\": \"function\"
   },
   {
     \"constant\": false,
     \"inputs\": [
       {
         \"internalType\": \"address\",
         \"name\": \"newOwner\",
         \"type\": \"address\"
       }
     ],
     \"name\": \"transferOwnership\",
     \"outputs\": [],
     \"payable\": false,
     \"stateMutability\": \"nonpayable\",
     \"type\": \"function\"
   },
   {
     \"inputs\": [
       {
         \"internalType\": \"address\",
         \"name\": \"cTokenManagerAddress\",
         \"type\": \"address\"
       }
     ],
     \"payable\": false,
     \"stateMutability\": \"nonpayable\",
     \"type\": \"constructor\"
   },
   {
     \"anonymous\": false,
     \"inputs\": [
       {
         \"indexed\": true,
         \"internalType\": \"uint256\",
         \"name\": \"streamId\",
         \"type\": \"uint256\"
       },
       {
         \"indexed\": false,
         \"internalType\": \"uint256\",
         \"name\": \"exchangeRate\",
         \"type\": \"uint256\"
       },
       {
         \"indexed\": false,
         \"internalType\": \"uint256\",
         \"name\": \"senderSharePercentage\",
         \"type\": \"uint256\"
       },
       {
         \"indexed\": false,
         \"internalType\": \"uint256\",
         \"name\": \"recipientSharePercentage\",
         \"type\": \"uint256\"
       }
     ],
     \"name\": \"CreateCompoundingStream\",
     \"type\": \"event\"
   },
   {
     \"anonymous\": false,
     \"inputs\": [
       {
         \"indexed\": true,
         \"internalType\": \"uint256\",
         \"name\": \"streamId\",
         \"type\": \"uint256\"
       },
       {
         \"indexed\": false,
         \"internalType\": \"uint256\",
         \"name\": \"senderInterest\",
         \"type\": \"uint256\"
       },
       {
         \"indexed\": false,
         \"internalType\": \"uint256\",
         \"name\": \"recipientInterest\",
         \"type\": \"uint256\"
       },
       {
         \"indexed\": false,
         \"internalType\": \"uint256\",
         \"name\": \"sablierInterest\",
         \"type\": \"uint256\"
       }
     ],
     \"name\": \"PayInterest\",
     \"type\": \"event\"
   },
   {
     \"anonymous\": false,
     \"inputs\": [
       {
         \"indexed\": true,
         \"internalType\": \"address\",
         \"name\": \"tokenAddress\",
         \"type\": \"address\"
       },
       {
         \"indexed\": true,
         \"internalType\": \"uint256\",
         \"name\": \"amount\",
         \"type\": \"uint256\"
       }
     ],
     \"name\": \"TakeEarnings\",
     \"type\": \"event\"
   },
   {
     \"anonymous\": false,
     \"inputs\": [
       {
         \"indexed\": true,
         \"internalType\": \"uint256\",
         \"name\": \"fee\",
         \"type\": \"uint256\"
       }
     ],
     \"name\": \"UpdateFee\",
     \"type\": \"event\"
   },
   {
     \"anonymous\": false,
     \"inputs\": [
       {
         \"indexed\": false,
         \"internalType\": \"address\",
         \"name\": \"account\",
         \"type\": \"address\"
       }
     ],
     \"name\": \"Paused\",
     \"type\": \"event\"
   },
   {
     \"anonymous\": false,
     \"inputs\": [
       {
         \"indexed\": false,
         \"internalType\": \"address\",
         \"name\": \"account\",
         \"type\": \"address\"
       }
     ],
     \"name\": \"Unpaused\",
     \"type\": \"event\"
   },
   {
     \"anonymous\": false,
     \"inputs\": [
       {
         \"indexed\": true,
         \"internalType\": \"address\",
         \"name\": \"account\",
         \"type\": \"address\"
       }
     ],
     \"name\": \"PauserAdded\",
     \"type\": \"event\"
   },
   {
     \"anonymous\": false,
     \"inputs\": [
       {
         \"indexed\": true,
         \"internalType\": \"address\",
         \"name\": \"account\",
         \"type\": \"address\"
       }
     ],
     \"name\": \"PauserRemoved\",
     \"type\": \"event\"
   },
   {
     \"anonymous\": false,
     \"inputs\": [
       {
         \"indexed\": true,
         \"internalType\": \"address\",
         \"name\": \"previousOwner\",
         \"type\": \"address\"
       },
       {
         \"indexed\": true,
         \"internalType\": \"address\",
         \"name\": \"newOwner\",
         \"type\": \"address\"
       }
     ],
     \"name\": \"OwnershipTransferred\",
     \"type\": \"event\"
   },
   {
     \"anonymous\": false,
     \"inputs\": [
       {
         \"indexed\": true,
         \"internalType\": \"uint256\",
         \"name\": \"streamId\",
         \"type\": \"uint256\"
       },
       {
         \"indexed\": true,
         \"internalType\": \"address\",
         \"name\": \"sender\",
         \"type\": \"address\"
       },
       {
         \"indexed\": true,
         \"internalType\": \"address\",
         \"name\": \"recipient\",
         \"type\": \"address\"
       },
       {
         \"indexed\": false,
         \"internalType\": \"uint256\",
         \"name\": \"deposit\",
         \"type\": \"uint256\"
       },
       {
         \"indexed\": false,
         \"internalType\": \"address\",
         \"name\": \"tokenAddress\",
         \"type\": \"address\"
       },
       {
         \"indexed\": false,
         \"internalType\": \"uint256\",
         \"name\": \"startTime\",
         \"type\": \"uint256\"
       },
       {
         \"indexed\": false,
         \"internalType\": \"uint256\",
         \"name\": \"stopTime\",
         \"type\": \"uint256\"
       }
     ],
     \"name\": \"CreateStream\",
     \"type\": \"event\"
   },
   {
     \"anonymous\": false,
     \"inputs\": [
       {
         \"indexed\": true,
         \"internalType\": \"uint256\",
         \"name\": \"streamId\",
         \"type\": \"uint256\"
       },
       {
         \"indexed\": true,
         \"internalType\": \"address\",
         \"name\": \"recipient\",
         \"type\": \"address\"
       },
       {
         \"indexed\": false,
         \"internalType\": \"uint256\",
         \"name\": \"amount\",
         \"type\": \"uint256\"
       }
     ],
     \"name\": \"WithdrawFromStream\",
     \"type\": \"event\"
   },
   {
     \"anonymous\": false,
     \"inputs\": [
       {
         \"indexed\": true,
         \"internalType\": \"uint256\",
         \"name\": \"streamId\",
         \"type\": \"uint256\"
       },
       {
         \"indexed\": true,
         \"internalType\": \"address\",
         \"name\": \"sender\",
         \"type\": \"address\"
       },
       {
         \"indexed\": true,
         \"internalType\": \"address\",
         \"name\": \"recipient\",
         \"type\": \"address\"
       },
       {
         \"indexed\": false,
         \"internalType\": \"uint256\",
         \"name\": \"senderBalance\",
         \"type\": \"uint256\"
       },
       {
         \"indexed\": false,
         \"internalType\": \"uint256\",
         \"name\": \"recipientBalance\",
         \"type\": \"uint256\"
       }
     ],
     \"name\": \"CancelStream\",
     \"type\": \"event\"
   },
   {
     \"constant\": false,
     \"inputs\": [
       {
         \"internalType\": \"uint256\",
         \"name\": \"feePercentage\",
         \"type\": \"uint256\"
       }
     ],
     \"name\": \"updateFee\",
     \"outputs\": [],
     \"payable\": false,
     \"stateMutability\": \"nonpayable\",
     \"type\": \"function\"
   },
   {
     \"constant\": false,
     \"inputs\": [
       {
         \"internalType\": \"address\",
         \"name\": \"tokenAddress\",
         \"type\": \"address\"
       },
       {
         \"internalType\": \"uint256\",
         \"name\": \"amount\",
         \"type\": \"uint256\"
       }
     ],
     \"name\": \"takeEarnings\",
     \"outputs\": [],
     \"payable\": false,
     \"stateMutability\": \"nonpayable\",
     \"type\": \"function\"
   },
   {
     \"constant\": true,
     \"inputs\": [
       {
         \"internalType\": \"uint256\",
         \"name\": \"streamId\",
         \"type\": \"uint256\"
       }
     ],
     \"name\": \"getStream\",
     \"outputs\": [
       {
         \"internalType\": \"address\",
         \"name\": \"sender\",
         \"type\": \"address\"
       },
       {
         \"internalType\": \"address\",
         \"name\": \"recipient\",
         \"type\": \"address\"
       },
       {
         \"internalType\": \"uint256\",
         \"name\": \"deposit\",
         \"type\": \"uint256\"
       },
       {
         \"internalType\": \"address\",
         \"name\": \"tokenAddress\",
         \"type\": \"address\"
       },
       {
         \"internalType\": \"uint256\",
         \"name\": \"startTime\",
         \"type\": \"uint256\"
       },
       {
         \"internalType\": \"uint256\",
         \"name\": \"stopTime\",
         \"type\": \"uint256\"
       },
       {
         \"internalType\": \"uint256\",
         \"name\": \"remainingBalance\",
         \"type\": \"uint256\"
       },
       {
         \"internalType\": \"uint256\",
         \"name\": \"ratePerSecond\",
         \"type\": \"uint256\"
       }
     ],
     \"payable\": false,
     \"stateMutability\": \"view\",
     \"type\": \"function\"
   },
   {
     \"constant\": true,
     \"inputs\": [
       {
         \"internalType\": \"uint256\",
         \"name\": \"streamId\",
         \"type\": \"uint256\"
       }
     ],
     \"name\": \"deltaOf\",
     \"outputs\": [
       {
         \"internalType\": \"uint256\",
         \"name\": \"delta\",
         \"type\": \"uint256\"
       }
     ],
     \"payable\": false,
     \"stateMutability\": \"view\",
     \"type\": \"function\"
   },
   {
     \"constant\": true,
     \"inputs\": [
       {
         \"internalType\": \"uint256\",
         \"name\": \"streamId\",
         \"type\": \"uint256\"
       },
       {
         \"internalType\": \"address\",
         \"name\": \"who\",
         \"type\": \"address\"
       }
     ],
     \"name\": \"balanceOf\",
     \"outputs\": [
       {
         \"internalType\": \"uint256\",
         \"name\": \"balance\",
         \"type\": \"uint256\"
       }
     ],
     \"payable\": false,
     \"stateMutability\": \"view\",
     \"type\": \"function\"
   },
   {
     \"constant\": true,
     \"inputs\": [
       {
         \"internalType\": \"uint256\",
         \"name\": \"streamId\",
         \"type\": \"uint256\"
       }
     ],
     \"name\": \"isCompoundingStream\",
     \"outputs\": [
       {
         \"internalType\": \"bool\",
         \"name\": \"\",
         \"type\": \"bool\"
       }
     ],
     \"payable\": false,
     \"stateMutability\": \"view\",
     \"type\": \"function\"
   },
   {
     \"constant\": true,
     \"inputs\": [
       {
         \"internalType\": \"uint256\",
         \"name\": \"streamId\",
         \"type\": \"uint256\"
       }
     ],
     \"name\": \"getCompoundingStream\",
     \"outputs\": [
       {
         \"internalType\": \"address\",
         \"name\": \"sender\",
         \"type\": \"address\"
       },
       {
         \"internalType\": \"address\",
         \"name\": \"recipient\",
         \"type\": \"address\"
       },
       {
         \"internalType\": \"uint256\",
         \"name\": \"deposit\",
         \"type\": \"uint256\"
       },
       {
         \"internalType\": \"address\",
         \"name\": \"tokenAddress\",
         \"type\": \"address\"
       },
       {
         \"internalType\": \"uint256\",
         \"name\": \"startTime\",
         \"type\": \"uint256\"
       },
       {
         \"internalType\": \"uint256\",
         \"name\": \"stopTime\",
         \"type\": \"uint256\"
       },
       {
         \"internalType\": \"uint256\",
         \"name\": \"remainingBalance\",
         \"type\": \"uint256\"
       },
       {
         \"internalType\": \"uint256\",
         \"name\": \"ratePerSecond\",
         \"type\": \"uint256\"
       },
       {
         \"internalType\": \"uint256\",
         \"name\": \"exchangeRateInitial\",
         \"type\": \"uint256\"
       },
       {
         \"internalType\": \"uint256\",
         \"name\": \"senderSharePercentage\",
         \"type\": \"uint256\"
       },
       {
         \"internalType\": \"uint256\",
         \"name\": \"recipientSharePercentage\",
         \"type\": \"uint256\"
       }
     ],
     \"payable\": false,
     \"stateMutability\": \"view\",
     \"type\": \"function\"
   },
   {
     \"constant\": false,
     \"inputs\": [
       {
         \"internalType\": \"uint256\",
         \"name\": \"streamId\",
         \"type\": \"uint256\"
       },
       {
         \"internalType\": \"uint256\",
         \"name\": \"amount\",
         \"type\": \"uint256\"
       }
     ],
     \"name\": \"interestOf\",
     \"outputs\": [
       {
         \"internalType\": \"uint256\",
         \"name\": \"senderInterest\",
         \"type\": \"uint256\"
       },
       {
         \"internalType\": \"uint256\",
         \"name\": \"recipientInterest\",
         \"type\": \"uint256\"
       },
       {
         \"internalType\": \"uint256\",
         \"name\": \"sablierInterest\",
         \"type\": \"uint256\"
       }
     ],
     \"payable\": false,
     \"stateMutability\": \"nonpayable\",
     \"type\": \"function\"
   },
   {
     \"constant\": true,
     \"inputs\": [
       {
         \"internalType\": \"address\",
         \"name\": \"tokenAddress\",
         \"type\": \"address\"
       }
     ],
     \"name\": \"getEarnings\",
     \"outputs\": [
       {
         \"internalType\": \"uint256\",
         \"name\": \"\",
         \"type\": \"uint256\"
       }
     ],
     \"payable\": false,
     \"stateMutability\": \"view\",
     \"type\": \"function\"
   },
   {
     \"constant\": false,
     \"inputs\": [
       {
         \"internalType\": \"address\",
         \"name\": \"recipient\",
         \"type\": \"address\"
       },
       {
         \"internalType\": \"uint256\",
         \"name\": \"deposit\",
         \"type\": \"uint256\"
       },
       {
         \"internalType\": \"address\",
         \"name\": \"tokenAddress\",
         \"type\": \"address\"
       },
       {
         \"internalType\": \"uint256\",
         \"name\": \"startTime\",
         \"type\": \"uint256\"
       },
       {
         \"internalType\": \"uint256\",
         \"name\": \"stopTime\",
         \"type\": \"uint256\"
       }
     ],
     \"name\": \"createStream\",
     \"outputs\": [
       {
         \"internalType\": \"uint256\",
         \"name\": \"\",
         \"type\": \"uint256\"
       }
     ],
     \"payable\": false,
     \"stateMutability\": \"nonpayable\",
     \"type\": \"function\"
   },
   {
     \"constant\": false,
     \"inputs\": [
       {
         \"internalType\": \"address\",
         \"name\": \"recipient\",
         \"type\": \"address\"
       },
       {
         \"internalType\": \"uint256\",
         \"name\": \"deposit\",
         \"type\": \"uint256\"
       },
       {
         \"internalType\": \"address\",
         \"name\": \"tokenAddress\",
         \"type\": \"address\"
       },
       {
         \"internalType\": \"uint256\",
         \"name\": \"startTime\",
         \"type\": \"uint256\"
       },
       {
         \"internalType\": \"uint256\",
         \"name\": \"stopTime\",
         \"type\": \"uint256\"
       },
       {
         \"internalType\": \"uint256\",
         \"name\": \"senderSharePercentage\",
         \"type\": \"uint256\"
       },
       {
         \"internalType\": \"uint256\",
         \"name\": \"recipientSharePercentage\",
         \"type\": \"uint256\"
       }
     ],
     \"name\": \"createCompoundingStream\",
     \"outputs\": [
       {
         \"internalType\": \"uint256\",
         \"name\": \"\",
         \"type\": \"uint256\"
       }
     ],
     \"payable\": false,
     \"stateMutability\": \"nonpayable\",
     \"type\": \"function\"
   },
   {
     \"constant\": false,
     \"inputs\": [
       {
         \"internalType\": \"uint256\",
         \"name\": \"streamId\",
         \"type\": \"uint256\"
       },
       {
         \"internalType\": \"uint256\",
         \"name\": \"amount\",
         \"type\": \"uint256\"
       }
     ],
     \"name\": \"withdrawFromStream\",
     \"outputs\": [
       {
         \"internalType\": \"bool\",
         \"name\": \"\",
         \"type\": \"bool\"
       }
     ],
     \"payable\": false,
     \"stateMutability\": \"nonpayable\",
     \"type\": \"function\"
   },
   {
     \"constant\": false,
     \"inputs\": [
       {
         \"internalType\": \"uint256\",
         \"name\": \"streamId\",
         \"type\": \"uint256\"
       }
     ],
     \"name\": \"cancelStream\",
     \"outputs\": [
       {
         \"internalType\": \"bool\",
         \"name\": \"\",
         \"type\": \"bool\"
       }
     ],
     \"payable\": false,
     \"stateMutability\": \"nonpayable\",
     \"type\": \"function\"
   }
 ]")

(def test-dai-abi (.parse js/JSON "[{\"constant\":true,\"inputs\":[],\"name\":\"name\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"internalType\":\"address\",\"name\":\"spender\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"value\",\"type\":\"uint256\"}],\"name\":\"approve\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"totalSupply\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"internalType\":\"address\",\"name\":\"from\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"to\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"value\",\"type\":\"uint256\"}],\"name\":\"transferFrom\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"decimals\",\"outputs\":[{\"internalType\":\"uint8\",\"name\":\"\",\"type\":\"uint8\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"internalType\":\"address\",\"name\":\"spender\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"addedValue\",\"type\":\"uint256\"}],\"name\":\"increaseAllowance\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"internalType\":\"address\",\"name\":\"to\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"value\",\"type\":\"uint256\"}],\"name\":\"mint\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"internalType\":\"address\",\"name\":\"owner\",\"type\":\"address\"}],\"name\":\"balanceOf\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"symbol\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"internalType\":\"address\",\"name\":\"spender\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"subtractedValue\",\"type\":\"uint256\"}],\"name\":\"decreaseAllowance\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"internalType\":\"address\",\"name\":\"to\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"value\",\"type\":\"uint256\"}],\"name\":\"transfer\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"internalType\":\"address\",\"name\":\"owner\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"spender\",\"type\":\"address\"}],\"name\":\"allowance\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"from\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"to\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"value\",\"type\":\"uint256\"}],\"name\":\"Transfer\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"owner\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"spender\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"value\",\"type\":\"uint256\"}],\"name\":\"Approval\",\"type\":\"event\"}]"))
(def sablier-abi (.parse js/JSON sablier-abi-str))