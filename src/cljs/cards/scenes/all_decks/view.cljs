(ns cards.scenes.all-decks.view
  (:require [re-frame.core :as re-frame]
            [cljsjs.material-ui]
            [cards.views.navigation :refer [main-app-bar] :as nav]
            [cljs-react-material-ui.core :refer [get-mui-theme color]]
            [cljs-react-material-ui.reagent :as ui]
            [cljs-react-material-ui.icons :as ic]
            [cards.views.navigation :refer [main-app-bar] :as nav]
            [reagent.core :as r]
            [cards.routes :refer [path-for-page]]))

(defn decks-app-bar
  []
  [main-app-bar #::nav{:title "Decks"}])

(defn decks-panel
  []
  (let [decks (re-frame/subscribe [:decks])]
    [:div
     [decks-app-bar]
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
                        :href (path-for-page :deck-cards :deck-id (:id d))
                        :primary-text (:title d)}]
         [ui/divider]])]]))
