(ns cards.scenes.deck-cards.subs
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :filtered-cards
 (fn [db _]
   (let [{:keys [cards decks]} db
         current-deck-id-query (get-in db [:navigation :route :query-params :deck])
         current-deck-id (get-in db [:navigation :route :route-params :deck-id])
         current-deck (first (filter #(= current-deck-id (:id %)) decks))
         card-ids (set (:card-ids current-deck))
         deck-cards (filter #(card-ids (:id %)) cards)]
     (if current-deck-id
       deck-cards
       cards))))
