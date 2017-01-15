(ns cards.routes
  (:import goog.History)
  (:require
   [goog.events :as events]
   [accountant.core :as accountant]
   [goog.history.EventType :as EventType]
   [re-frame.core :as re-frame]
   [bidi.bidi :as bidi]))

(def routes
  ["/" {"" :home
        "feed"  :feed
        "cards" {"/add" :add-card}}
   true :not-found])

(defn path-for-page
  [page]
  (bidi/path-for routes page))

(defn set-page!
  [match]
  (let [current-page (:handler match)
        route-params (:route-params match)]
    (re-frame/dispatch [:set-route {:page current-page
                                    :route-params route-params}])))
(defn app-routes []
  (accountant/configure-navigation!
   {:nav-handler (fn [path]
                   (let [match (bidi/match-route routes path)]
                     (set-page! match)))
    :path-exists? (fn [path]
                    (boolean (bidi/match-route routes path)))}))
