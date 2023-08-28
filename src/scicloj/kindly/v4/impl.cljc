(ns scicloj.kindly.v4.impl)

(defn attach-kind-to-value [value kind]
  (vary-meta value assoc :kindly/kind kind))

(defn form->kind [form]
  (when-let [m (some-> form meta)]
    (or (some->> m
                 :tag
                 resolve
                 deref
                 namespace
                 (= "kind"))
        (some->> m
                 keys
                 (filter #(-> %
                              namespace
                              (= "kind")))
                 first))))

(defn value->kind [value]
  (-> value
      meta
      :kindly/kind))

(defn check-predicate-kinds [value predicate-kinds]
  (->> predicate-kinds
       (map (fn [[predicate k]]
              (when (predicate value)
                k)))
       (filter identity)
       first))

(defn kind-as-a-fn [kind]
  (fn
    ([]
     kind)
    ([value]
     (attach-kind-to-value value kind))))
