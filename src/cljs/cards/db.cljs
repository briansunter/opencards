(ns cards.db
  (:require [cljs.spec :as s]
            [clojure.test.check.generators]
            [cljs.spec.impl.gen :as gen]
            [cards.routes :as routes]
            [cards.utils :refer [not-blank?]]
            [bidi.bidi :as bidi]
            [bidi-tools.core]))

(def pages (set (map :handler (bidi/route-seq ["" [routes/routes]]))))
(s/def ::non-empty-string (s/and string? not-blank?))

(s/def ::id ::non-empty-string)

(s/def ::content ::non-empty-string)
(s/def ::front ::content)
(s/def ::back ::content)
(s/def ::tag ::non-empty-string)
(s/def ::tags (s/coll-of ::tag))
(s/def ::card (s/keys :req-un [::id ::front ::back ::tags]))

(s/def ::name ::non-empty-string)
(s/def ::page pages)
(s/def ::deck-filter-query-param (s/keys :req-un [::deck-id]))
(s/def :nav/deck string?)

(s/def ::query-params (s/keys :opt-un [:nav/deck]))

(s/def ::route (s/keys :req-un [::page] :opt-un [::query-params]))

(s/def ::drawer-open boolean?)

(s/def ::navigation (s/keys :req-un [::drawer-open ::route]))

(s/def ::tag-query string?)
(s/def ::matching-tags ::tags)
(s/def ::front-text string?)
(s/def ::back-text string?)
(s/def ::add-card-page (s/keys :req-un [::front-text ::back-text]))
(s/def ::cards (s/coll-of ::card))

(s/def ::followed-cards (s/coll-of ::id))
(s/def ::user (s/keys :req-un [::name]))

(s/def ::title ::non-empty-string)
(s/def ::description ::non-empty-string)
(s/def ::card-ids (s/coll-of ::id :kind? set?))

(s/def ::deck (s/keys :req-un [::id ::title ::description ::card-ids]))

(s/def ::decks (s/coll-of ::deck))

(s/def ::form-input-text string?)

(s/def ::add-deck-title ::form-input-text)
(s/def ::add-deck-description ::form-input-text)

(s/def ::add-deck-page (s/keys :req-un [::add-deck-title ::add-deck-description ::tags ::tag-query ::matching-tags]))
(s/def ::scenes (s/keys :req-un [::add-card-page ::add-deck-page]))

(s/def ::db (s/keys :req-un [::name ::cards ::decks ::scenes ::navigation ::tags ::user]))

;; (def db {:navigation {:route {:page :cards}
;;                       :drawer-open true}
;;          :name "Flash Cards"
;;          })

(s/fdef add-cards-to-decks
        :args (s/cat :decks ::decks :cards ::cards)
        :ret ::decks)

(defn- add-cards-to-decks
  [decks cards]
  (let [card-ids (into #{} (map :id) cards)]
    (map #(assoc % :card-ids (set (random-sample .5 card-ids))) decks)))

(defn generate-db
  []
  (let [db (gen/generate (s/gen ::db))
        cards (:cards db)
        decks (:decks db)
        decks-with-cards (add-cards-to-decks decks cards)]
    (assoc db :decks decks-with-cards)))

(def default-db (generate-db))
