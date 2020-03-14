(ns ebc.ui
  (:require
    [reagent.core :as r]
    [re-frame.core :as rf]
    [clojure.string :as str]
    ["semantic-ui-react" :refer [Grid Grid.Row Grid.Column Divider Container
                                 Segment Rail
                                 Header Header.Content
                                 Button Form Form.Input Form.Button Form.Group Form.Field Form.Dropdown Form.Select Input
                                 Table Table.Body Table.Header Table.HeaderCell Table.Cell Table.Row]]
    [clojure.string :as string]))

(defn connect-button
  "Connects with MetaMask"
  []
  [:> Button {:onClick #(rf/dispatch [:connect-web3])} "Connect"])

(defn accounts-dropdown []
  "Dropdown with all available accounts, after MetaMask connected to the page"
  [:> Form.Select {;:style {:width "400px"}
                   :label     "Sender's account"
                   :search    true
                   :fluid     true
                   :selection true
                   :options   @(rf/subscribe [:accounts])
                   :onChange  (fn [_e v]
                                (rf/dispatch [:set-current-account (.-value v)])
                                (rf/dispatch [:get-balance]))}])
(defn common-fields
  "Fields which are used by all other forms - Sender, Recipient and Gas limit"
  []
  [:> Form [:label "Connect"]
   [:> Form.Group {:widths "equal"}
    [accounts-dropdown]
    [:> Form.Input {:fluid    true :label "Receiver"
                    :value    @(rf/subscribe [:get-db-value :receiver])
                    :onChange #(rf/dispatch [:set-db-value :receiver (.-value %2)])}]
    [:> Form.Input {:fluid    true :label "Gas limit"
                    :value    @(rf/subscribe [:get-db-value :gas-limit])
                    :onChange #(rf/dispatch [:set-db-value :gas-limit (.-value %2)])}]
    ]]
  )

(defn send-directly []
  "Sends ETH directly between accounts"
  [:> Form
   [:> Form.Group {:widths "equal"}
    [:> Container
     [:> Header {:as "h5"} "Available ETH Amount"]
     [:p @(rf/subscribe [:balance])]]
    [:> Form.Input {:fluid    true :label "Amount"
                    :value    @(rf/subscribe [:field-value :send-directly-form :amount])
                    :onChange #(rf/dispatch [:set-field-value :send-directly-form :amount (.-value %2)])}]]
   [:> Form.Group {:widths "equal"}
    [:> Form.Button {:onClick #(rf/dispatch [:send-form :send-directly-form]) :content "Send eth directly!"}]]])

(defn transaction-hash-container [transaction]
  "Display transaction hash, after transaction is sent"
  (when-let [hash @(rf/subscribe [:get-db-value transaction])]
    [:> Container
     [:> Header {:as "h5"} "Transaction hash:"]
     [:a {:target "_blank"
          :href   (str "https://rinkeby.etherscan.io/tx/" hash)} hash]]))

(defn approve-sablier-contract
  "Create and approve contract which sets the maximum number of tokens which may be streamd"
  []
  [:> Form
   [:> Form.Group {:widths "equal"}
    [:> Container
     [:> Header {:as "h5"} "Available Amount"]
     [:p @(rf/subscribe [:get-db-value :dai-balance])]]
    [:> Form.Input {:fluid    true :label "Allowed Amount"
                    :value    @(rf/subscribe [:field-value :approve-sablier-contract-form :allowed-amount])
                    :onChange #(rf/dispatch [:set-field-value :approve-sablier-contract-form :allowed-amount (.-value %2)])}]
    ]
   [:> Form.Group {:widths "equal"}
    [:> Form.Button {:onClick #(rf/dispatch [:send-form :approve-sablier-contract-form]) :content "Approve Sablier contract!"}]]
   [transaction-hash-container :approve-transaction-hash]])

(defn create-sablier-stream []
  "Stream deposit at now+start-time for duration time."
  [:> Form
   [:> Form.Group {:widths "equal"}
    [:> Form.Input {:fluid    true :label "deposit"
                    :value    @(rf/subscribe [:field-value :create-sablier-stream-form :deposit])
                    :onChange #(rf/dispatch [:set-field-value :create-sablier-stream-form :deposit (.-value %2)])}]
    [:> Form.Input {:fluid    true :label "start-time"
                    :value    @(rf/subscribe [:field-value :create-sablier-stream-form :time-since-now])
                    :onChange #(rf/dispatch [:set-field-value :create-sablier-stream-form :time-since-now (.-value %2)])}]
    [:> Form.Input {:fluid    true :label "duration"
                    :value    @(rf/subscribe [:field-value :create-sablier-stream-form :duration])
                    :onChange #(rf/dispatch [:set-field-value :create-sablier-stream-form :duration (.-value %2)])}]]
   [:> Form.Group {:widths "equal"}
    [:> Form.Button {:onClick #(rf/dispatch [:send-form :create-sablier-stream-form]) :content "Create Sablier Stream!"}]]
   [transaction-hash-container :stream-transaction-hash]])

(defn make-header-sub-row [size title]
  [:> Grid.Row {:columns 1}
   [:> Grid.Column
    [:> Header {:as size} title]]])

(defn make-left-rail [& text]
  [:> Rail {:attached true :position "left"}
   [:> Segment (interpose [:br] text)]])

(defn index []
  [:> Grid {:columns 3
            ;:centered true
            :style   {:width      "65%"
                      :text-align "center"
                      :margin     "auto"}}
   [:> Grid.Row]
   [:> Grid.Row
    [:> Grid.Column]
    [:> Grid.Column
     [:> Container
      [:> Header {:as "h1"} "Ethereum Blockchain Client"]
      [:p {} "Backend-less, server-less app using Sablier, the protocol for real-time finance on the Ethereum blockchain"]]]
    [:> Grid.Column]]
   [:> Grid.Row {:columns 1}
    [:> Grid.Column
     [:> Divider]]]
   [:> Grid.Row {:columns 3}
    [:> Grid.Column]
    [make-left-rail "Click the button to connect with MetaMask"]
    [:> Grid.Column {}
     [connect-button]]
    [:> Grid.Column]]
   [make-header-sub-row "h3" "Set common fields"]
   [:> Grid.Row {:columns 1}
    [make-left-rail
     "1) Select account to use,"
     "2) Input recipient address,"
     "3) Input accepted gas cost (create stream action's typical cost is <250K)"]
    [:> Grid.Column {}
     [common-fields]]]
   [make-header-sub-row "h3" "Approve Sablier contract"]
   [:> Grid.Row {:columns 3}
    [make-left-rail "Limit maximum number of tokens to stream and execute the contract."]
    [:> Grid.Column {}]
    [:> Grid.Column {}
     [approve-sablier-contract]]
    [:Grid.Column {}]]
   [make-header-sub-row "h3" "Create Sablier Stream"]
   [:> Grid.Row {:columns 1}
    [make-left-rail "Create stream:"
     "1) Deposit - number of tokens to stream, *must* be a multiple of the duration"
     "2) Start time - Number of seconds since now, when streaming is going to start"
     "3) Duration - Time in which all tokens are going to be streamed"]
    [:> Grid.Column {}
     [create-sablier-stream]]]
   [:> Grid.Row {:columns 1}
    [make-left-rail "Here you will see (hope not) errors which occured during contract creation."]
    [:> Grid.Column {}
     [:> Header {:as "h3"} "Errors: "]
     (when-not (empty? @(rf/subscribe [:errors]))
       [:p {:class "ui negative message"} @(rf/subscribe [:errors])])]]
   [:> Grid.Row {:columns 1}
    [:> Grid.Column
     [:> Divider]]]
   [make-header-sub-row "h3" "Send ETH directly to another address"]
   [:> Grid.Row {:columns 1}
    [make-left-rail "Here you can send ETH, directly to previously defined recipient"]
    [:> Grid.Column {}
     [send-directly]]]
   ]
  )
