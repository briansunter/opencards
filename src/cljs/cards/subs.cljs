(ns cards.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]
            [cards.utils :refer [not-blank? matches-search?]]))

(re-frame/reg-sub
 :active-panel
 (fn [db _]
   (get-in db [:navigation :route :page])))

(re-frame/reg-sub
 :active-panel-title
 (fn [db _]
   (let [page (get-in db [:navigation :route :page])
         current-deck-id (get-in db [:navigation :route :route-params :deck-id])
         decks (:decks db)
         current-deck (first (filter #(= current-deck-id (:id %)) decks))
         default-title (:name db)]
     (case page
       :feed "Feed"
       :home "Home"
       :decks "My Decks"
       :cards "My Cards"
       :deck-cards (:title current-deck)
       :add-card "Add Card"
       :add-deck "Add Deck"
       default-title))))

(re-frame/reg-sub
 :add-deck-tags
 (fn [db _]
   (get-in db [:scenes :add-deck-page :tags])))

(re-frame/reg-sub
 :add-card-button-enabled
 (fn [db _]
   (let [front (get-in db [:scenes :add-card-page :front-text])
         back (get-in db [:scenes :add-card-page :back-text])]
     (and (not-blank? front) (not-blank? back)))))


(re-frame/reg-sub
 :tab-bar-index
 (fn [db _]
   (let [page (get-in db [:navigation :route :page])]
     (case page
       :feed 0
       :add-card 1
       :home 2))))

(re-frame/reg-sub
 :app-drawer-open
 (fn [db _]
   (get-in db [:navigation :drawer-open])))
