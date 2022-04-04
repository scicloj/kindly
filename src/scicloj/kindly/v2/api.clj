(ns scicloj.kindly.v2.api
  (:require [scicloj.kindly.v2.kind :as kind]
            [scicloj.kindly.v2.kindness :as kindness]
            [scicloj.kindly.v2.impl :as impl]))

(defn define-kind! [kind]
  (impl/define-kind! kind))

(defn define-kind-behaviour! [kind behaviour]
  (impl/define-kind-behaviour! kind behaviour))

(defn kind->behaviour [kind]
  (impl/kind->behaviour kind))

(defn consider [value kind]
  (cond (keyword? kind) (impl/attach-kind-to-value value kind)
        (fn? kind) (consider value (kind))))

(defn kinds-set []
  (set (keys @impl/*kind->behaviour)))

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
