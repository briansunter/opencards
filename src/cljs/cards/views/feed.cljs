(ns cards.views.feed
  (:require
   [cards.routes :refer [path-for-page]]
   [cljsjs.material-ui]
   [cljs-react-material-ui.core :refer [color]]
   [cljsjs.material-ui-chip-input]
   [cljs-react-material-ui.reagent :as ui]
   [re-frame.core :as re-frame]
   [cljs-react-material-ui.icons :as ic]
   [reagent.core :as r]))

(def chip-input (r/adapt-react-class js/MaterialUIChipInput))

(defn suggested-card-feed-item
  [front-content back-content tags]
  [ui/card
   [ui/card-header {:title "Suggested Card"
                    :avatar (r/as-element [ui/avatar {:icon (ic/av-library-add)}])
                    :style {:font-size 30
                            :background-color (color :grey300)
                            :width "100%"}}]
   [:div {:style {:word-break "break-all"
                  :display "flex"
                  :justify-content "center"
                  :flex-direction "row"}}
    [ui/paper
     {:z-depth 2
      :style {:margin-right 10
              :width "50%"
              :display "flex"
              :justify-content "center"
              :align-items "center"
              :min-height 200
              :padding 20}}
     [:p front-content]]
    [ui/paper
     {:z-depth 2
      :style {
              :width "50%"
              :display "flex"
              :justify-content "center"
              :align-items "center"
              :margin-left 10
              :min-height 100
              :padding 20}}
     [:p back-content]]]
   [ui/card-text
    {:children (r/as-element [:div {:style {:display "flex"
                                            :flex-direction "row"
                                            :flex-wrap "wrap"
                                            :max-width "100%"}}
                              (map (fn [t] [ui/chip {:style {:margin 5}} t]) tags)])}]
   [ui/card-actions [ui/raised-button {:primary true
                                       :label "Follow Card"
                                       :style {:margin 10}}]]])

(defn feed-panel []
  (let [cards (re-frame/subscribe [:all-cards])]
    (fn []
      [ui/list
       (for [c @cards]
         [suggested-card-feed-item (:front c) (:back c) (:tags c)])])))

