(ns cards.scenes.all-decks.subs
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :decks
 (fn [db _]
   (:decks db)))
