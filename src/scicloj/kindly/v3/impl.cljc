(ns scicloj.kindly.v3.impl
  (:require [scicloj.kindly.v3.kind]))

(defn attach-kind-to-value [value kind]
  (vary-meta value assoc :kindly/kind kind))

(defn define-kind! [kind]
  (intern 'scicloj.kindly.v3.kind
          (symbol (name kind))
          (fn
            ([]
             kind)
            ([value]
             (attach-kind-to-value value kind)))))
