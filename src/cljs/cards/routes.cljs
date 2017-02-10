(ns cards.routes
  (:import goog.History)
  (:require
   [goog.events :as events]
   [accountant.core :as accountant]
   [goog.history.EventType :as EventType]
   [re-frame.core :as re-frame]
   [bidi.bidi :as bidi]
   [cards.utils :refer [map->query]]
   [bidi-tools.core :as bidi-tools]))

(def routes
  ["/" {"" :home
        "feed"  :feed
        "cards" {"" :cards
                 "/add" :add-card}
        "decks/" {"" :decks
                  [:deck-id "/cards"] :deck-cards
                 "/add" :add-deck}}
   true :not-found])


(def path-for-page
   (partial bidi/path-for routes))

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
