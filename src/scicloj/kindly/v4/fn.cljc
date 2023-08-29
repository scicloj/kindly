(ns scicloj.kindly.v4.fn)

(defn attach-kind-to-value [value kind]
  (if (instance? clojure.lang.IObj value)
    (vary-meta value assoc :kindly/kind kind)
    (attach-kind-to-value [value] kind)))

(defn kind-as-a-fn [kind]
  (fn
    ([]
     kind)
    ([value]
     (attach-kind-to-value value kind))))
