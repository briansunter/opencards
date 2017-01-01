(ns cards.events
    (:require [re-frame.core :as re-frame]
              [cards.db :as db]))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/reg-event-db
 :set-route
 (fn [db [_ route]]
   (assoc db :route route)))
