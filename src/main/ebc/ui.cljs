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

(defn accounts [] ;; send directly form
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

(defn connect-button
      []
      [:> Form
       [:label "Connect"]
       [:> Form.Field
        [:> Button {:onClick (fn [e]
                                 (dispatch-example-event "clicked")
                                 (rf/dispatch [:connect-web3]))}
            "Connect!"]]])

(defn example-text []
      (-> @(rf/subscribe [:text])))


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
         [accounts]]]
       [:> Grid.Row {:columns 3}
        [:> Grid.Column {} ]
        [:> Grid.Column {}
         ]
        [:Grid.Column {} [example-text]]]
       ]
      )
