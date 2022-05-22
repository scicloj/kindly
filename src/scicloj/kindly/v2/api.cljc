(ns scicloj.kindly.v2.api
  (:require [scicloj.kindly.v2.kind :as kind]
            [scicloj.kindly.v2.kindness :as kindness]
            [scicloj.kindly.v2.impl :as impl]))

(defn define-kind! [kind]
  (impl/define-kind! kind))

(defn define-kind-behaviour! [kind behaviour]
  (impl/define-kind-behaviour! kind behaviour))

(defn add-behaviour! [tool options]
  (impl/add-behaviour! tool options))

(defn kind->behaviour [kind]
  (impl/kind->behaviour kind))

(defn consider [value kind]
  (cond (keyword? kind) (impl/attach-kind-to-value value kind)
        (fn? kind) (consider value (kind))))

(defn value->kind [value]
  (-> value
      meta
      :kindly/kind))

(defn kind? [value]
  (-> value->kind
      kind
      some?))
