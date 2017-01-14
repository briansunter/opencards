(ns cards.views.index
  (:require
   [cards.views.home :refer [home-panel]]
   [cards.views.add-card :refer [add-card-panel]]
   [cards.views.feed :refer [feed-panel]]
   [cards.views.navigation :refer [navigation]]
   [re-frame.core :as re-frame]))

(defn- panels [panel-name]
  (case panel-name
    :home home-panel
    :cards feed-panel
    :add-card add-card-panel
    [:div [:a "not found"]]))

(defn main-panel []
  (let [active-panel (re-frame/subscribe [:active-panel])]
    (fn []
      [navigation (panels @active-panel)])))
