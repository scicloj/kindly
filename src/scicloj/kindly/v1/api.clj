(ns scicloj.kindly.v1.api
  (:require [scicloj.kindly.v1.kind :as kind]
            [scicloj.kindly.v1.defs :as defs]
            [scicloj.kindly.v1.kindness :as kindness]))

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

(defn to-hiccup [value]
  (let [{:keys [value->hiccup]}
        (value->behaviour value)]
    (value->hiccup value)))
