(ns cards.views.feed
  (:require
   [cards.routes :refer [path-for-page]]
   [cljsjs.material-ui]
   [cljsjs.material-ui-chip-input]
   [cljs-react-material-ui.reagent :as ui]
   [re-frame.core :as re-frame]
   [reagent.core :as r]))

(def chip-input (r/adapt-react-class js/MaterialUIChipInput))

(defn suggested-card-feed-item
  [front-content back-content tags]
  [:div
   [ui/divider {:style {:height 30}}]
   [ui/card
    {:container-style {:padding 10}}
    [ui/card-header {:title "Suggested Card" :style {:font-size 30}}]
    [:div {:style {:word-break "break-all"
                   :display "flex"
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
       :style {:display "flex"
               :justify-content "center"
               :align-items "center"
               :margin-left 10
               :min-height 100
               :width "50%"
               :padding 20}}
      [:p back-content]]]
    [ui/card-text
     {:children (r/as-element [:div {:style {:display "flex"
                                             :flex-direction "row"
                                             :flex-wrap "wrap"
                                             :max-width "100%"}}(map (fn [t] [ui/chip {:style {:margin 5}} t]) tags)])}]
    [ui/card-actions [ui/raised-button {:primary true
                                        :label "Follow Card"
                                        :style {:margin 10}}]]]])

(defn feed-panel []
  (let [cards (re-frame/subscribe [:all-cards])]
    (fn []
      [ui/list
       (for [c @cards]
         [suggested-card-feed-item (:front c) (:back c) (:tags c)])])))


