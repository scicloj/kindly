(ns scicloj.kindly.v2.behaviours
  (:require [scicloj.kindly.v2.kind]))

(def *kind->behaviour (atom {}))

(defn define-kind-behaviour! [kind behaviour]
  (swap! *kind->behaviour
         update kind
         merge behaviour)
  (intern 'scicloj.kindly.v2.kind (symbol (name kind)) kind))

(defn kind->behaviour [kind]
  (@*kind->behaviour kind))
