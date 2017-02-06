(ns cards.scenes.feed.subs
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :all-cards
 (fn [db _]
   (:cards db)))
