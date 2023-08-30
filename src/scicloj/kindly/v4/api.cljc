(ns scicloj.kindly.v4.api
  (:require [scicloj.kindly.v4.kind :as kind]))

(defn consider [value kind]
  (cond (keyword? kind) (fn/attach-kind-to-value value kind)
        (fn? kind) (consider value (kind))))

(def known-kinds
  (->> 'scicloj.kindly.v4.kind
       find-ns
       ns-publics
       vals
       (map #(%))
       set))
