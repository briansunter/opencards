(ns cards.views.add-card
  (:require
   [cljsjs.material-ui]
   [cljs-react-material-ui.core :refer [get-mui-theme color]]
   [cljs-react-material-ui.reagent :as ui]
   [cljs-react-material-ui.icons :as ic]
   [re-frame.core :as re-frame]
   [reagent.core :as r]))

(defn add-card-view
  []
  [:div
   [ui/text-field {:name "front" :hint-text "Front"}]
   [ui/text-field {:name "back" :hint-text "Back"}]])
