(ns cards.views.index
  (:require
   [cards.scenes.home.view :refer [home-panel]]
   [cards.scenes.add-card.view :refer [add-card-panel]]
   [cards.scenes.feed.view :refer [feed-panel]]
   [cards.scenes.all-decks.view :refer [decks-panel]]
   [cards.scenes.add-deck.view :refer [add-deck-panel]]
   [cards.scenes.deck-cards.view :refer [cards-panel]]
   [cards.views.navigation :refer [theme]]
   [re-frame.core :as re-frame]))

(defn- panels [panel-name]
  (case panel-name
    :home home-panel
    :feed feed-panel
    :decks decks-panel
    :add-deck add-deck-panel
    :cards cards-panel
    :deck-cards cards-panel
    :add-card add-card-panel
    [:div [:a "not found"]]))

(defn main-panel []
  (let [active-panel (re-frame/subscribe [:active-panel])]
    [theme (panels @active-panel)]))
