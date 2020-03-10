(ns ebc.app
  (:require [ebc.events]
            [ebc.subs]
            [ebc.ui :as ui]
            [reagent.dom :as rdom]
            [re-frame.core :as rf]
            [devtools.core :as devtools]))

(devtools/install!)
(enable-console-print!)

(.log js/console (range 200))

(defn render []
      (rdom/render [ui/index] (js/document.getElementById "main")))

(defn ^:dev/after-load clear-cache-and-render!
      []
      ;; The `:dev/after-load` metadata causes this function to be called
      ;; after shadow-cljs hot-reloads code. We force a UI update by clearing
      ;; the Reframe subscription cache.
      (rf/clear-subscription-cache!)
      (render))


(defn ^:export init []
      (rf/dispatch-sync [:initialize])
      (render))