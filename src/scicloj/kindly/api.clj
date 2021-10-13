(ns scicloj.kindly.api
  (:require [scicloj.kindly.kind :as kind]
            [scicloj.kindly.defs :as defs]
            [scicloj.kindly.kindness :as kindness]))

(defn consider [value kind]
  (vary-meta value assoc :kindly/kind kind))

(defn kinds-set []
  (set (keys @defs/*kind->behaviour)))

(defn metadata->kind [m]
  (or (some->> m
               :tag
               resolve
               deref
               ((kinds-set)))
      (some->> m
               keys
               (filter (kinds-set))
               first)))

(defn value->kind [value]
  (-> value
      meta
      :kindly/kind))

(defn kind->behaviour [kind]
  (@defs/*kind->behaviour kind))

(defn value->behaviour [value]
  (or (-> value
          value->kind
          kind->behaviour)
      (-> value
          kindness/->behaviour)))

(defn assoc-kind-behaviour! [kind behaviour]
  (defs/assoc-kind-behaviour! kind behaviour))

