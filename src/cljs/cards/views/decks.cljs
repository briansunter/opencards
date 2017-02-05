(ns cards.views.decks
  (:require [re-frame.core :as re-frame]
            [cljsjs.material-ui]
            [cljs-react-material-ui.core :refer [get-mui-theme color]]
            [cljs-react-material-ui.reagent :as ui]
            [cljs-react-material-ui.icons :as ic]
            [reagent.core :as r]
            [cards.routes :refer [path-for-page]]))

(defn decks-panel
  []
  (let [decks (re-frame/subscribe [:decks])]
    [:div
     [ui/floating-action-button {:secondary true
                                 :href (path-for-page :add-deck)
                                 :style {:bottom 0
                                         :z-index 5
                                         :right 0
                                         :margin-right 20
                                         :margin-bottom 20
                                         :position "fixed"}}
      (ic/content-add)]
     [ui/list
      (for [d @decks]
        [:div
         [ui/list-item {:key (hash (:title d))
                        :href (path-for-page :cards {:deck (:id d)})
                        :primary-text (:title d)}]
         [ui/divider]])]]))
