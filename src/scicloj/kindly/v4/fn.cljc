(ns scicloj.kindly.v4.fn)

(defn attach-kind-to-value [value kind]
  (if (instance? clojure.lang.IObj value)
    (vary-meta value assoc :kindly/kind kind)
    (attach-kind-to-value [value] kind)))

(defn kind-as-a-fn [kind]
  (let [kind-kw (->> kind
                     name
                     (keyword "kind"))]
    (fn
      ([]
       kind-kw)
      ([value]
       (attach-kind-to-value value kind-kw)))))

(defmacro defkinds [& kind-symbols]
  (->> kind-symbols
       (map (fn [s]
              `(def ~s
                 (kind-as-a-fn (quote ~s)))))
       (cons 'do)))
