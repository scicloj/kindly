(ns scicloj.kindly.v3.impl
  (:require [scicloj.kindly.v3.kind]))

(def *kinds-set (atom #{}))

(defn attach-kind-to-value [value kind]
  (vary-meta value assoc :kindly/kind kind))

(defn define-kind! [kind]
  (swap! kinds-set conj kind)
  (intern 'scicloj.kindly.v3.kind
          (symbol (name kind))
          (fn
            ([]
             kind)
            ([value]
             (attach-kind-to-value value kind)))))
