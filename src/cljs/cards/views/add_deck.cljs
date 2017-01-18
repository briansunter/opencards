(ns cards.views.add-deck
  (:require [re-frame.core :as re-frame]
            [cljs-react-material-ui.reagent :as ui]
            [cljs-react-material-ui.icons :as ic]
            [reagent.core :as r]))

(defn target-value
  [tv]
  (-> tv .-target .-value))

(defn add-deck-panel
  []
  (let [title (re-frame/subscribe [:add-deck-title])
        description (re-frame/subscribe [:add-deck-description])]
    [ui/paper {:z-depth 2
               :style {:padding 10
                       :overflow "hidden"}}
     [ui/text-field {:floating-label-text "Deck Name"
                     :value @title
                     :on-change #(re-frame/dispatch [:add-deck-update-title (target-value %)])
                     :style {:padding 10
                             :font-size 26
                             :multi-line true
                             :width "100%"}}]
     [ui/text-field {:floating-label-text "Deck Description"
                     :value @description
                     :on-change  #(re-frame/dispatch [:add-deck-update-description (target-value %)])
                     :multi-line true
                     :rows 3
                     :style {:padding 10
                             :width "100%"}}]
     [ui/raised-button {:primary true
                        :label "upload image"
                        }]]))
