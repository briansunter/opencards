(ns cards.scenes.deck-cards.view
  (:require [re-frame.core :as re-frame]
            [cljsjs.material-ui]
            [cljs-react-material-ui.core :refer [get-mui-theme color]]
            [cljs-react-material-ui.reagent :as ui]
            [cljs-react-material-ui.icons :as ic]
            [reagent.core :as r]
            [cards.routes :refer [path-for-page]]))

(defn cards-panel
  []
  (let [cards (re-frame/subscribe [:filtered-cards])]
    [:div
     [ui/floating-action-button {:secondary true
                                 :href (path-for-page :add-card)
                                 :style {:bottom 0
                                         :z-index 5
                                         :right 0
                                         :margin-right 20
                                         :margin-bottom 20
                                         :position "fixed"}}
      (ic/content-add)]
     [ui/list
      (for [c @cards]
        [:div
         [ui/list-item {:key (hash (:front c))
                        :primary-text (:front c)}]
         [ui/divider]])]]))
