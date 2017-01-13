(ns cards.db
  (:require [cljs.spec :as s]
            [clojure.test.check.generators]
            [cljs.spec.impl.gen :as gen]
            [cards.routes :as routes]
            [bidi.bidi :as bidi]))

(def pages (set (map :handler (bidi/route-seq ["" [routes/routes]]))))

(s/def ::tags (s/coll-of ::tag))
(s/def ::content (s/and string? (complement clojure.string/blank?)))
(s/def ::face (s/keys :req-un [::content ::tags]))
(s/def ::front ::face)
(s/def ::back ::face)
(s/def ::card (s/keys :req-un [::front ::back ::tags]))
(s/def ::cards (s/coll-of ::card))

(s/def ::name string?)
(s/def ::page pages)
(s/def ::route (s/keys :req-un [::page]))

(s/def ::tag string?)
(s/def ::tag-query string?)
(s/def ::matching-tags ::tags)
(s/def ::add-card-page (s/keys :req-un [::tag-query ::matching-tags]))
(s/def ::db (s/keys :req-un [::name ::cards ::add-card-page ::route ::tags] :opt [::route]))

(def default-db (gen/generate (s/gen ::db)))
