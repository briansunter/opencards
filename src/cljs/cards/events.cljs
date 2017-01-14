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

(re-frame/reg-cofx
 :uuid
 (fn [coeffects _]
   (assoc coeffects :uuid (str (random-uuid)))))

(re-frame/reg-event-db
 :update-add-card-front-text
 cards-interceptors
 (fn [db [_ text]]
   (assoc-in db [:add-card-page :front-text] text)))

(re-frame/reg-event-db
 :update-add-card-back-text
 cards-interceptors
 (fn [db [_ text]]
   (assoc-in db [:add-card-page :back-text] text)))

(re-frame/reg-event-fx
 :create-card
 [cards-interceptors (re-frame/inject-cofx :uuid)]
 (fn [cofx event]
   (let [[_ front back tags] event
         db (:db cofx)
         new-uuid (:uuid cofx)]
     {:db (-> (update db :cards #(conj % {:id new-uuid :front front :back back :tags tags}))
              (assoc-in [:add-card-page :front-text] "")
              (assoc-in [:add-card-page :back-text] ""))})))
