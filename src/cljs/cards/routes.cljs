(ns cards.routes
  (:import goog.History)
  (:require
   [goog.events :as events]
   [accountant.core :as accountant]
   [goog.history.EventType :as EventType]
   [re-frame.core :as re-frame]
   [bidi.bidi :as bidi]
   [bidi-tools.core :as bidi-tools]))

(def routes
  ["/" {"" :home
        "feed"  :feed
        "cards" {"" :cards
                 "/add" :add-card}
        "decks" {"" :decks
                 "/add" :add-deck}}
   true :not-found])


(defn- url-encode
  [string]
  (some-> string str (js/encodeURIComponent) (.replace "+" "%20")))

(defn- map->query
  [m]
  (some->> (seq m)
           sort
           (map (fn [[k v]]
                  [(url-encode (name k))
                   "="
                   (url-encode (str v))]))
           (interpose "&")
           flatten
           (apply str)))

(defn path-for-page
  ([page]
   (bidi/path-for routes page))
  ([page params]
   (str (bidi/path-for routes page) "?" (map->query params))))

(defn set-page!
  [match]
  (let [current-page (:handler match)
        route-params (:route-params match)
        query-params (:query-params match)]
    (re-frame/dispatch [:set-route {:page current-page
                                    :route-params route-params
                                    :query-params query-params}])))
(defn app-routes []
  (accountant/configure-navigation!
   {:nav-handler (fn [path]
                   (let [match (bidi-tools/match-route-with-query routes path)]
                     (set-page! match)))
    :path-exists? (fn [path]
                    (boolean (bidi/match-route routes path)))})
  (accountant/dispatch-current!))
