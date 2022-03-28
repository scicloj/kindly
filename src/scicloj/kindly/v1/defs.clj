(ns scicloj.kindly.v1.defs
  (:require [scicloj.kindly.v1.view :as view]
            [scicloj.kindly.v1.util :as util]
            [scicloj.kindly.v1.kind :as kind]))

(def *kind->behaviour (atom {}))

(defn assoc-kind-behaviour! [kind behaviour]
  (swap! *kind->behaviour
         assoc kind behaviour)
  (intern 'scicloj.kindly.v1.kind (symbol (name kind)) kind))

(assoc-kind-behaviour! :naive
  {:render-src?   true
   :value->hiccup #'view/value->naive-hiccup})

(assoc-kind-behaviour! :md-nocode
  {:render-src?   false
   :value->hiccup #'view/markdowns->hiccup})

(assoc-kind-behaviour! :md
  {:render-src?   true
   :value->hiccup #'view/markdowns->hiccup})

(assoc-kind-behaviour! :hiccup-nocode
  {:render-src?   false
   :value->hiccup identity})

(assoc-kind-behaviour! :hiccup
  {:render-src?   true
   :value->hiccup identity})

(assoc-kind-behaviour! :vega
  {:render-src?   true
   :value->hiccup (partial vector :p/vega)})

(assoc-kind-behaviour! :quil
  {:render-src?   true
   :value->hiccup (partial vector :p/quil)})

(assoc-kind-behaviour! :sci
  {:render-src?   true
   :value->hiccup (partial vector :p/sci)})

(assoc-kind-behaviour! :math
  {:render-src?   true
   :value->hiccup (partial util/wrap :p/math)})

(assoc-kind-behaviour! :code
  {:render-src?   true
   :value->hiccup (partial util/wrap :p/code)})

(assoc-kind-behaviour! :void
  {:render-src?   true
   :value->hiccup (constantly nil)})

(assoc-kind-behaviour! :hidden
  {:render-src?   false
   :value->hiccup (constantly nil)})

(assoc-kind-behaviour! :dataset-grid
  {:render-src?   true
   :value->hiccup #'view/dataset->grid-hiccup})

(assoc-kind-behaviour! :dataset
  {:render-src?   true
   :value->hiccup #'view/dataset->md-hiccup})

(assoc-kind-behaviour! :clojure-test
  {:render-src?   true
   :value->hiccup #'view/test-boolean->hiccup})

(assoc-kind-behaviour! :midje
  {:render-src?   true
   :value->hiccup #'view/test-boolean->hiccup})

(assoc-kind-behaviour! :html
  {:render-src?   true
   :value->hiccup (partial vector :p/html)})
