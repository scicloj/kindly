(ns scicloj.kindly.v2.api
  (:require [scicloj.kindly.v2.kind :as kind]
            [scicloj.kindly.v2.kindness :as kindness]
            [scicloj.kindly.v2.behaviours :as behaviours]))

(defn define-kind-behaviour! [kind behaviour]
  (behaviours/define-kind-behaviour! kind behaviour))

(defn kind->behaviour [kind]
  (behaviours/kind->behaviour kind))

(defn consider [value kind]
  (vary-meta value assoc :kindly/kind kind))

(defn kinds-set []
  (set (keys @behaviours/*kind->behaviour)))

(defn code->kind [code]
  (when-let [m (some-> code
                       read-string
                       meta)]
    (or (some->> m
                 :tag
                 resolve
                 deref
                 ((kinds-set)))
        (some->> m
                 keys
                 (filter (kinds-set))
                 first))))

(defn value->kind [value]
  (or (-> value
          meta
          :kindly/kind)
      (-> value
          kindness/kind)))

(defn kind
  ([value]
   (kind value nil))
  ([value code]
   (or (-> code
           code->kind)
       (-> value
           value->kind))))
