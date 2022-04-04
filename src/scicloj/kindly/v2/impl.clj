(ns scicloj.kindly.v2.impl
  (:require [scicloj.kindly.v2.kind]))

(defn attach-kind-to-value [value kind]
  (vary-meta value assoc :kindly/kind kind))

(defn define-kind! [kind]
  (intern 'scicloj.kindly.v2.kind
          (symbol (name kind))
          (fn
            ([]
             kind)
            ([value]
             (attach-kind-to-value value kind)))))

(def *kind->behaviour (atom {}))

(defn define-kind-behaviour! [kind behaviour]
  (define-kind! kind)
  (swap! *kind->behaviour
         update kind
         merge behaviour))

(defn kind->behaviour [kind]
  (@*kind->behaviour kind))
