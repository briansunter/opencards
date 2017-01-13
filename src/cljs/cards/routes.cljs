(ns cards.routes
  (:import goog.History)
  (:require
   [goog.events :as events]
   [pushy.core :as pushy]
   [accountant.core :as accountant]
   [goog.history.EventType :as EventType]
   [re-frame.core :as re-frame]
   [bidi.bidi :as bidi]))

(defn set-page!
  [match]
  (let [current-page (:handler match)
        route-params (:route-params match)]
    (re-frame/dispatch [:set-route {:page current-page
                                    :route-params route-params}])))

(def routes
  ["/" {"" :home
        "cards" {"" :cards
                 "/add" :add-card}}
   true :not-found])

(def history (pushy/pushy set-page! (partial bidi/match-route routes)))

(defn app-routes []
  (pushy/start! history)
  (accountant/configure-navigation!
   {:nav-handler (fn [path]
                   (let [match (bidi/match-route routes path)]
                     (set-page! match)))
    :path-exists? (fn [path]
                    (boolean (bidi/match-route routes path)))})
  (accountant/dispatch-current!))
