(ns scicloj.kindly.v2.impl
  (:require [scicloj.kindly.v2.generate :as generate]))

(defn attach-kind-to-value [value kind]
  (vary-meta value assoc :kindly/kind kind))

(defn define-kind! [kind]
  (assert kind)
  (assert (keyword? kind))
  (generate/generate-kind!
   kind (fn
          ([]
           kind)
          ([value]
           (attach-kind-to-value value kind)))))

(def !kind->behaviour (atom {}))

^:deprecated
(defn define-kind-behaviour! [kind behaviour]
  (define-kind! kind)
  (swap! !kind->behaviour
         update kind
         merge behaviour))

(defn kind->behaviour [kind]
  (@!kind->behaviour kind))

(defn add-behaviour!
  "This is used in adapter modules for visualization tools.
  It needs to be called once for each tool and kind, on startup time."
  [tool
   {:keys [kind]
    :as options}]
  (assert (map? options))
  (assert kind)
  (define-kind! kind)
  (swap! !kind->behaviour
         update kind
         merge {tool options}))
