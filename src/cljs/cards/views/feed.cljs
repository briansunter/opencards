(ns cards.views.feed
  (:require
  [cards.routes :refer [path-for-page]]
  [cljsjs.material-ui]
  [cljs-react-material-ui.reagent :as ui]
  [re-frame.core :as re-frame]))

(defn feed-panel []
  (let [cards (re-frame/subscribe [:all-cards])]
    (fn []
      [ui/selectable-list
       (for [c @cards]
         [:div {:key (:id c)}
          [ui/list-item
           {:primary-text (:front c)
            :href (path-for-page :add-card)}]
          [ui/divider]])])))
