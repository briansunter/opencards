(ns cards.utils)

(defn not-blank?
  [s]
  (not (clojure.string/blank? s)))

(defn matches-search?
  [s sub]
  (not= -1 (.indexOf s sub)))
