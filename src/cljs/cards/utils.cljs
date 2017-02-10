(ns cards.utils)

(defn not-blank?
  [s]
  (not (clojure.string/blank? s)))

(defn matches-search?
  [s sub]
  (not= -1 (.indexOf s sub)))

(defn url-encode
  [string]
  (some-> string str (js/encodeURIComponent) (.replace "+" "%20")))

(defn map->query
  [m]
  (some->> (seq m)
           (map (fn [[k v]]
                  [(url-encode (name k))
                   "="
                   (url-encode (str v))]))
           (interpose "&")
           flatten
           (apply str)))
