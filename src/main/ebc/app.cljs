(ns ebc.app
  (:require [ebc.events]
            [ebc.subs]
            [ebc.ui :as ui]
            [reagent.dom :as rdom]
            [re-frame.core :as rf]))

(defn ^:export init []
  (rf/dispatch-sync [:initialize])
  (rdom/render [ui/index] (js/document.getElementById "main")))