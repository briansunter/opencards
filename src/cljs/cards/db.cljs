(ns cards.db
  (:require [cljs.spec :as s]
            [clojure.test.check.generators]
            [cljs.spec.impl.gen :as gen]
            [cards.routes :as routes]
            [cards.utils :refer [not-blank?]]
            [bidi.bidi :as bidi]))

(def pages (set (map :handler (bidi/route-seq ["" [routes/routes]]))))

(s/def ::id (s/and string? not-blank?))

(s/def ::content (s/and string? not-blank?))
(s/def ::front ::content)
(s/def ::back ::content)
(s/def ::tag (s/and string? not-blank?))
(s/def ::tags (s/coll-of ::tag))
(s/def ::card (s/keys :req-un [::id ::front ::back ::tags]))


(s/def ::name string?)
(s/def ::page pages)
(s/def ::route (s/keys :req-un [::page]))

(s/def ::tag-query string?)
(s/def ::matching-tags ::tags)
(s/def ::front-text string?)
(s/def ::back-text string?)
(s/def ::add-card-page (s/keys :req-un [::tags ::tag-query ::matching-tags ::front-text ::back-text]))

(s/def ::cards (s/coll-of ::card))
(s/def ::db (s/keys :req-un [::name ::cards ::add-card-page ::route ::tags] :opt [::route]))

(def default-db (gen/generate (s/gen ::db)))
