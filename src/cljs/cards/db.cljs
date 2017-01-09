(ns cards.db
  (:require [cljs.spec :as s]
            [cards.routes :as routes]
            [bidi.bidi :as bidi]))

(def pages (set (map :handler (bidi/route-seq  ["" [routes/routes]]))))
(s/def ::name string?)
(s/def ::page pages)
(s/def ::route (s/keys :req-un [::page]))
(s/def ::db (s/keys :req-un [::name ::route]))

(def default-db
  {:name "re-frame"
   :route {:page :home}})
