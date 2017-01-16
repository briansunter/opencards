(ns cards.db
  (:require [cljs.spec :as s]
            [clojure.test.check.generators]
            [cljs.spec.impl.gen :as gen]
            [cards.routes :as routes]
            [cards.utils :refer [not-blank?]]
            [bidi.bidi :as bidi]))

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
(s/def ::route (s/keys :req-un [::page]))

(s/def ::drawer-open boolean?)

(s/def ::navigation (s/keys :req-un [::drawer-open ::route]))

(s/def ::tag-query string?)
(s/def ::matching-tags ::tags)
(s/def ::front-text string?)
(s/def ::back-text string?)
(s/def ::add-card-page (s/keys :req-un [::tags ::tag-query ::matching-tags ::front-text ::back-text]))

(s/def ::cards (s/coll-of ::card))

(s/def ::followed-cards (s/coll-of ::id))
(s/def ::user (s/keys :req-un [::name]))

(s/def ::deck (s/keys :req-un [::name]))
(s/def ::decks (s/coll-of ::deck))

(s/def ::db (s/keys :req-un [::name ::cards ::decks ::add-card-page ::navigation ::tags ::user]))

(def db {:navigation {:route {:page :home}
                      :drawer-open true}})

(def default-db (merge (gen/generate (s/gen ::db))))
