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
 :tab-bar-index
 (fn [db _]
   (let [page (get-in db [:route :page])]
     (case page
       :cards 0
       :add-card 1
       :home 2
       ))))
