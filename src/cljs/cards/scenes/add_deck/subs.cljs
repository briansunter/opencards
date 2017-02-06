(ns cards.scenes.add-deck.subs
  (:require [re-frame.core :as re-frame]))

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
