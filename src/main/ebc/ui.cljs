(ns ebc.ui
  (:require
    ;[ebc.client :as client]
    [reagent.core :as r]
    [re-frame.core :as rf]
    [clojure.string :as str]
    ["semantic-ui-react" :refer [Grid Grid.Row Grid.Column Divider Container
                                 Header Header.Content
                                 Button Form Form.Input Form.Button Form.Group Form.Field Form.Dropdown Form.Select Input
                                 Table Table.Body Table.Header Table.HeaderCell Table.Cell Table.Row]]
    [clojure.string :as string]))

(defn dispatch-example-event [text]
      (rf/dispatch [:send-text text]))

(defn dispatch-account-event [accounts]
      )

(defn accounts-dropdown []
      [:> Form.Select { ;:style {:width "400px"}
                         :label "Sender's account"
                         :search    true
                         :fluid     true
                         :selection true
                         :options   @(rf/subscribe [:accounts])
                         :onChange  (fn [_e v]
                                        (rf/dispatch [:set-current-account (.-value v)])
                                        (rf/dispatch [:get-balance]))}])
(defn common-fields [] ;; fields which are shared between forms
      [:> Form [:label "Connect"]
       [:> Form.Group {:widths "equal"}
        [accounts-dropdown]
        [:> Form.Input {:fluid true :label "Receiver"
                        :value @(rf/subscribe [:get-db-value :receiver])
                        :onChange #(rf/dispatch [:set-db-value :receiver (.-value %2)])}]
        [:> Form.Input {:fluid true :label "Gas limit"
                        :value @(rf/subscribe [:get-db-value :gas-limit])
                        :onChange #(rf/dispatch [:set-db-value :gas-limit (.-value %2)])}]
        ]]
      )

(defn send-directly [] ;; send directly form
      [:> Form
       [:> Form.Group {:widths "equal"}
        [:> Container
         [:> Header {:as "h5"} "Available ETH Amount"]
         [:p @(rf/subscribe [:balance])]]
        [:> Form.Input {:fluid true :label "Amount"
                        :value @(rf/subscribe [:field-value :send-directly-form :amount])
                        :onChange #(rf/dispatch [:set-field-value :send-directly-form :amount (.-value %2)])}]]
       [:> Form.Group {:widths "equal"}
        [:> Form.Button {:onClick #(rf/dispatch [:send-form :send-directly-form]) :content "Send eth directly!"}]]])

(defn transaction-hash-container [transaction]
      (when-let [hash @(rf/subscribe [:get-db-value transaction])]
                [:> Container
                 [:> Header {:as "h5"} "Transaction hash:"]
                 [:a {:target "_blank"
                      :href (str "https://rinkeby.etherscan.io/tx/" hash)} hash]]))

(defn approve-sablier-contract [] ;; approve the contract with max allowed tokens
      [:> Form
       [:> Form.Group {:widths "equal"}
        [:> Container
         [:> Header {:as "h5"} "Available Amount"]
         [:p @(rf/subscribe [:get-db-value :dai-balance])]]
        [:> Form.Input {:fluid true :label "Allowed Amount"
                        :value @(rf/subscribe [:field-value :approve-sablier-contract-form :allowed-amount])
                        :onChange #(rf/dispatch [:set-field-value :approve-sablier-contract-form :allowed-amount (.-value %2)])}]
        ]
       [:> Form.Group {:widths "equal"}
        [:> Form.Button {:onClick #(rf/dispatch [:send-form :approve-sablier-contract-form]) :content "Approve Sablier contract!"}]]
       [transaction-hash-container :approve-transaction-hash]])

(defn create-sablier-stream []
      ; function createStream(address recipient, uint256 deposit, address tokenAddress, uint256 startTime, uint256 stopTime) returns (uint256)
      [:> Form
       [:> Form.Group {:widths "equal"}
      [:> Form.Input {:fluid true :label "deposit"
                      :value @(rf/subscribe [:field-value :create-sablier-stream-form :deposit])
                      :onChange #(rf/dispatch [:set-field-value :create-sablier-stream-form :deposit (.-value %2)])}]
      [:> Form.Input {:fluid true :label "start-time - #seconds since now"
                      :value @(rf/subscribe [:field-value :create-sablier-stream-form :time-since-now])
                      :onChange #(rf/dispatch [:set-field-value :create-sablier-stream-form :time-since-now (.-value %2)])}]
      [:> Form.Input {:fluid true :label "duration"
                      :value @(rf/subscribe [:field-value :create-sablier-stream-form :duration])
                      :onChange #(rf/dispatch [:set-field-value :create-sablier-stream-form :duration (.-value %2)])}]]
        [:> Form.Group {:widths "equal"}
         [:> Form.Button {:onClick #(rf/dispatch [:send-form :create-sablier-stream-form]) :content "Create Sablier Stream!"}]]
       [transaction-hash-container :stream-transaction-hash]])


(defn connect-button
      []
      [:> Button {:onClick (fn [e]
                               (dispatch-example-event "clicked")
                               (rf/dispatch [:connect-web3]))}
       "Connect"])

(defn make-sub-row [size title]
      [:> Grid.Row {:columns 1}
       [:> Grid.Column
        [:> Header {:as size} title]]])


(defn index []
      (when-let [web3 @(rf/subscribe [:web3])]
            (.getAccounts (.-eth web3) (fn [_e v]
                                                 (rf/dispatch [:get-accounts v]))))
      [:> Grid {:columns 3
                :style   {:width      "75%"
                          :text-align "center"
                          :margin     "auto"}}
       [:> Grid.Row]
       [:> Grid.Row
        [:> Grid.Column]
        [:> Grid.Column
         [:> Header {:as "h1"} "Ethereum Blockchain Client"]]
        [:> Grid.Column]]
       [:> Grid.Row {:columns 1}
        [:> Grid.Column
         [:> Divider]]]
       [:> Grid.Row {:columns 3}
        [:> Grid.Column ]
        [:> Grid.Column {}
         [connect-button]]
        [:Grid.Column]]
       [make-sub-row "h3" "Set common fields"]
       [:> Grid.Row {:columns 1}
        [:> Grid.Column {}
         [common-fields]]]
       [make-sub-row "h3" "Send ETH directly to another address"]
       [:> Grid.Row {:columns 1}
        [:> Grid.Column {}
         [send-directly]]]
       [make-sub-row "h3" "Approve Sablier contract"]
       [:> Grid.Row {:columns 3}
        [:> Grid.Column {} ]
        [:> Grid.Column {}
         [approve-sablier-contract]]
        [:Grid.Column {} ]]
       [make-sub-row "h3" "Create Sablier Stream"]
       [:> Grid.Row {:columns 1}
        [:> Grid.Column {}
         [create-sablier-stream]]]
       [:> Grid.Row {:columns 1}
        [:> Grid.Column {}
         [:> Header {:as "h3"} "Errors: "]
         [:p @(rf/subscribe [:errors])]
         ]
        ]
       ]
      )
