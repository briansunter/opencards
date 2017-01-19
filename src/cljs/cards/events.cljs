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
   (-> (assoc-in db [:navigation :route] route)
       (assoc-in [:navigation :drawer-open] false))))

(re-frame/reg-event-db
 :search-for-tag
 cards-interceptors
 (fn [db [_ tag-query]]
   (assoc-in db [:scenes :add-card-page :tag-query] tag-query)))

(re-frame/reg-cofx
 :uuid
 (fn [coeffects _]
   (assoc coeffects :uuid (str (random-uuid)))))

(re-frame/reg-event-db
 :add-card/update-front-text
 cards-interceptors
 (fn [db [_ text]]
   (assoc-in db [:scenes :add-card-page :front-text] text)))

(re-frame/reg-event-db
 :add-card/update-back-text
 cards-interceptors
 (fn [db [_ text]]
   (assoc-in db [:scenes :add-card-page :back-text] text)))

(re-frame/reg-event-db
 :add-deck/add-chip
 cards-interceptors
 (fn [db [_ tag]]
   (update-in db [:scenes :add-deck-page :tags] #(conj % tag))))

(re-frame/reg-event-db
 :add-deck/delete-chip
 cards-interceptors
 (fn [db [_ tag]]
   (update-in db [:scenes :add-deck-page :tags] #(remove (partial = tag) % ))))

(re-frame/reg-event-db
 :set-nav-drawer-open
 (fn [db [_ open]]
   (assoc-in db [:navigation :drawer-open] open)))

(re-frame/reg-event-db
 :toggle-nav-drawer
 (fn [db _]
   (update-in db [:navigation :drawer-open] not)))

(re-frame/reg-event-fx
 :add-card/create-card
 [cards-interceptors (re-frame/inject-cofx :uuid)]
 (fn [cofx event]
   (let [[_ front back tags] event
         {:keys [db uuid]} cofx]
     {:db (-> (update db :cards #(conj % {:id uuid :front front :back back :tags tags}))
              (assoc-in [:scenes :add-card-page :front-text] "")
              (assoc-in [:scenes :add-card-page :back-text] ""))})))

(re-frame/reg-event-fx
 :add-deck
 [cards-interceptors (re-frame/inject-cofx :uuid)]
 (fn [cofx event]
   (let [[_ title description] event
         {:keys [db uuid]} cofx]
     {:db (-> (update db :decks #(conj % {:id uuid :title title :description description}))
              (assoc-in [:scenes :add-deck-page :add-deck-description] "")
              (assoc-in [:scenes :add-deck-page :add-deck-title] "")
              (assoc-in [:scenes :add-deck-page :tags] []))})))

(re-frame/reg-event-db
 :add-deck-update-title
 [cards-interceptors]
 (fn [db [_ title]]
   (assoc-in db [:scenes :add-deck-page :add-deck-title] title)))

(re-frame/reg-event-db
 :add-deck-update-description
 [cards-interceptors]
 (fn [db [_ description]]
   (assoc-in db [:scenes :add-deck-page :add-deck-description] description)))
