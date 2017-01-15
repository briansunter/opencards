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
   (get-in db [:route :page])))

(re-frame/reg-sub
 :all-cards
 (fn [db _]
   (:cards db)))

(re-frame/reg-sub
 :add-card-tags
 (fn [db _]
   (get-in db [:add-card-page :tags])))

(re-frame/reg-sub
 :add-card-create-button-enabled
 (fn [db _]
   (let [front (get-in db [:add-card-page :front-text])
         back (get-in db [:add-card-page :back-text])
         tags (get-in db [:add-card-page :tags])]
     (and (not-blank? front) (not-blank? back) (not-empty tags)))))

(re-frame/reg-sub
 :tag-query
 (fn [db _]
   (get-in db [:add-card-page :tag-query])))


(re-frame/reg-sub
 :matching-tags
 (fn [db _]
   (let [all-tags (:tags db)
         tag-query (get-in db [:add-card-page :tag-query])
         matching-tags (filter #(matches-search? % tag-query) all-tags)]
     matching-tags)))

(re-frame/reg-sub
 :add-card-front-text
 (fn [db _]
   (get-in db [:add-card-page :front-text])))

(re-frame/reg-sub
 :add-card-back-text
 (fn [db _]
   (get-in db [:add-card-page :back-text])))

(re-frame/reg-sub
 :tab-bar-index
 (fn [db _]
   (let [page (get-in db [:route :page])]
     (case page
       :feed 0
       :add-card 1
       :home 2))))
