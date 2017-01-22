(ns cards.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]
            [cards.utils :refer [not-blank? matches-search?]]))

(re-frame/reg-sub
 :name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 :active-panel
 (fn [db _]
   (get-in db [:navigation :route :page])))

(re-frame/reg-sub
 :active-panel-title
 (fn [db _]
   (let [page (get-in db [:navigation :route :page])
         default-title (:name db)]
     (case page
       :feed "Feed"
       :home "Home"
       :decks "My Decks"
       :cards "My Cards"
       :add-card "Add Card"
       :add-deck "Add Deck"
       default-title))))

(re-frame/reg-sub
 :all-cards
 (fn [db _]
   (:cards db)))

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
 :tag-query
 (fn [db _]
   (get-in db [:scenes :add-card-page :tag-query])))

(re-frame/reg-sub
 :matching-tags
 (fn [db _]
   (let [all-tags (:tags db)
         tag-query (get-in db [:scenes :add-card-page :tag-query])
         matching-tags (filter #(matches-search? % tag-query) all-tags)]
     matching-tags)))

(re-frame/reg-sub
 :add-card-front-text
 (fn [db _]
   (get-in db [:scenes :add-card-page :front-text])))

(re-frame/reg-sub
 :add-card-back-text
 (fn [db _]
   (get-in db [:scenes :add-card-page :back-text])))

(re-frame/reg-sub
 :tab-bar-index
 (fn [db _]
   (let [page (get-in db [:navigation :route :page])]
     (case page
       :feed 0
       :add-card 1
       :home 2))))

(re-frame/reg-sub
 :decks
 (fn [db _]
   (:decks db)))

(re-frame/reg-sub
 :app-drawer-open
 (fn [db _]
   (get-in db [:navigation :drawer-open])))

(re-frame/reg-sub
 :add-deck-button-enabled
 (fn [db _]
   (let [{:keys [add-deck-title add-deck-description]} (get-in db [:scenes :add-deck-page])]
     (not-any? clojure.string/blank? [add-deck-title add-deck-description]))))

(re-frame/reg-sub
 :add-deck-title
 (fn [db _]
   (get-in db [:scenes :add-deck-page :add-deck-title])))

(re-frame/reg-sub
 :add-deck-description
 (fn [db _]
   (get-in db [:scenes :add-deck-page :add-deck-description])))
