(ns cards.db
  (:require [cljs.spec :as s]
            [clojure.test.check.generators]
            [cljs.spec.impl.gen :as gen]
            [cards.routes :as routes]
            [bidi.bidi :as bidi]))

(def pages (set (map :handler (bidi/route-seq ["" [routes/routes]]))))
;; (def pages #{:add-card})
(s/def ::name string?)

(s/def ::page pages)
(s/def ::route (s/keys :req-un [::page]))

(s/def ::value (s/and string? (complement clojure.string/blank?)))
(s/def ::tag keyword?)
(s/def ::tags (s/coll-of ::tag))
(s/def ::face (s/keys :req-un [::value ::tags]))
(s/def ::front ::face)
(s/def ::back ::face)
(s/def ::card (s/keys :req-un [::front ::back]))
(s/def ::cards (s/coll-of ::card))

(s/def ::db (s/keys :req-un [::name ::cards] :opt [::route]))

(def default-db
  {:name "Open Cards"
   :route {:page :home}
   :cards (apply concat (gen/sample (s/gen ::cards)))})
