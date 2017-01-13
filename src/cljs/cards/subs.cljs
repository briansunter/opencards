(ns cards.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]))

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
 :tag-query
 (fn [db _]
   (get-in db [:add-card-page :tag-query])))

(defn matches-search?
  [s sub]
  (not= -1 (.indexOf s sub)))

(re-frame/reg-sub
 :matching-tags
 (fn [db _]
   (let [all-tags (:tags db)
         tag-query (get-in db [:add-card-page :tag-query])
         matching-tags (filter #(matches-search? % tag-query) all-tags)]
     matching-tags
     )))

(re-frame/reg-sub
 :tab-bar-index
 (fn [db _]
   (let [page (get-in db [:route :page])]
     (case page
       :cards 0
       :add-card 1
       :home 2))))
