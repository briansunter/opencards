(ns cards.views.add-deck
  (:require [re-frame.core :as re-frame]
            [cljs-react-material-ui.reagent :as ui]
            [cljs-react-material-ui.icons :as ic]
            [reagent.core :as r]))

(defn add-deck-panel
  []
  (let []
    [ui/paper {:z-depth 2
               :style {:padding 10
                       :overflow "hidden"}}
     [ui/text-field {:floating-label-text "Deck Name"
                     :style {:padding 10
                             :font-size 26
                             :multi-line true
                             :width "100%"}}]
     [ui/text-field {:floating-label-text "Deck Description"
                     :multi-line true
                     :rows 3
                     :style {:padding 10
                             :width "100%"}}]
     [ui/raised-button {:primary true
                        :label "upload image"
                        }]]))
