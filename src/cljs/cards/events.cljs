(ns cards.events
    (:require [re-frame.core :as re-frame]
              [cards.db :as db]
              [cljs.spec :as s]))

(defn check-and-throw
  "throw an exception if db doesn't match the spec"
  [a-spec db]
  (when-not (s/valid? a-spec db)
    (throw (ex-info (str "spec check failed: " (s/explain-str a-spec db)) {}))))

(def check-spec-interceptor (re-frame/after (partial check-and-throw ::db/db)))

(def cards-interceptors [check-spec-interceptor])

(re-frame/reg-event-db
 :initialize-db
 cards-interceptors
 (fn  [_ _]
   db/default-db))

(re-frame/reg-event-db
 :set-route
 cards-interceptors
 (fn [db [_ route]]
   (assoc db :route route)))

(re-frame/reg-event-db
 :search-for-tag
 cards-interceptors
 (fn [db [_ tag-query]]
   (assoc-in db [:add-card-page :tag-query] tag-query)))
