(ns ebc.ui
  (:require
    ;[ebc.client :as client]
    [reagent.core :as r]
    [re-frame.core :as rf]
    [clojure.string :as str]
    ["semantic-ui-react" :refer [Grid Grid.Row Grid.Column Divider
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

(defn send-directly [] ;; send directly form
      [:> Form [:label "Connect"]
       [:> Form.Group {:widths "equal"}
        [accounts-dropdown]
        [:> Form.Input {:fluid true :label "Available Amount" :value @(rf/subscribe [:balance])
                        }]
        ]
       [:> Form.Group {:widths "equal"}
        [:> Form.Input {:fluid true :label "Receiver"
                        :value @(rf/subscribe [:field-value :send-directly-form :receiver])
                        :onChange #(rf/dispatch [:set-field-value :send-directly-form :receiver (.-value %2)])}]
        [:> Form.Input {:fluid true :label "Amount"
                        :value @(rf/subscribe [:field-value :send-directly-form :amount])
                        :onChange #(rf/dispatch [:set-field-value :send-directly-form :amount (.-value %2)])}]
        ]
       [:> Form.Group {:widths "equal"}
        [:> Form.Button {:onClick #(rf/dispatch [:send-form :send-directly-form]) :content "Send!"}]]])

(defn approve-sablier-contract [] ;; send directly form
      [:> Form [:label "Approve Sablier contract"]
       [:> Form.Group {:widths "equal"}
        [:> Form.Input {:fluid true :label "Allowed Amount" :value @(rf/subscribe [:field-value :approve-sablier-contract-form :allowed-amount])}]
        ]
       [:> Form.Group {:widths "equal"}
        [:> Form.Button {:onClick #(rf/dispatch [:send-form :approve-sablier-contract-form]) :content "Approve Sablier contract!"}]]])

(defn create-sablier-stream []
      ; function createStream(address recipient, uint256 deposit, address tokenAddress, uint256 startTime, uint256 stopTime) returns (uint256)
      [:> Form [:label "Create Sablier stream"]
       [:> Form.Group {:widths "equal"}
      [:> Form.Input {:fluid true :label "deposit"
                      :value @(rf/subscribe [:field-value :create-sablier-stream-form :deposit])
                      :onChange #(rf/dispatch [:set-field-value :create-sablier-stream-form :deposit (.-value %2)])}]
      [:> Form.Input {:fluid true :label "token address"
                      :value @(rf/subscribe [:field-value :create-sablier-stream-form :token-address])
                      :onChange #(rf/dispatch [:set-field-value :create-sablier-stream-form :token-address (.-value %2)])}]
      [:> Form.Input {:fluid true :label "start-time - number of seconds to add from now"
                      :value @(rf/subscribe [:field-value :create-sablier-stream-form :time-from-now])
                      :onChange #(rf/dispatch [:set-field-value :create-sablier-stream :time-from-now (.-value %2)])}]
      [:> Form.Input {:fluid true :label "duration"
                      :value @(rf/subscribe [:field-value :create-sablier-stream-form :duration])
                      :onChange #(rf/dispatch [:set-field-value :create-sablier-stream-form :duration (.-value %2)])}]]
        [:> Form.Group {:widths "equal"}
         [:> Form.Button {:onClick #(rf/dispatch [:send-form :create-sablier-stream-form]) :content "Create Sablier Stream!"}]]])

(defn connect-button
      []
      [:> Form
       [:label "Connect"]
       [:> Form.Field
        [:> Button {:onClick (fn [e]
                                 (dispatch-example-event "clicked")
                                 (rf/dispatch [:connect-web3]))}
            "Connect!"]]])

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
       [:> Grid.Row {:columns 1}
        [:> Grid.Column {}
         [send-directly]]]
       [:> Grid.Row {:columns 3}
        [:> Grid.Column {} ]
        [:> Grid.Column {}
         [approve-sablier-contract]
         ]
        [:Grid.Column {} ]]
       [:> Grid.Row {:columns 3}
        [:> Grid.Column {} ]
        [:> Grid.Column {}
         [create-sablier-stream]
         ]
        [:Grid.Column {} ]]
       ]
      )
