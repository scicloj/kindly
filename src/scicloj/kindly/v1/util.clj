(ns scicloj.kindly.v1.util)

(defn map-coll->key-vector-map
  [coll]
  (reduce (fn [new-map key]
            (assoc new-map key (vec (map key coll))))
          {}
          (keys (first coll))))

(defn wrap [wrapper v]
  (if (vector? v)
    (->> v
         (map (fn [x]
                [wrapper x]))
         (into [:div]))
    [wrapper v]))
