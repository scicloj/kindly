(ns scicloj.kindly.v3.defaults
  (:require [scicloj.kindly.v3.api :as kindly]
            [scicloj.kindly.v3.impl :as impl]
            [scicloj.kindly.v3.kindness :as kindness]))

(defn code->kind [code]
  (when-let [m (some-> code
                       read-string
                       meta)]
    (or (some->> m
                 :tag
                 resolve
                 deref
                 namespace
                 (= "kind"))
        (some->> m
                 keys
                 (filter #(-> %
                              namespace
                              (= "kind")))
                 first))))

(defn value->kind [value]
  (or (-> value
          meta
          :kindly/kind)
      (-> value
          kindness/kind)))

(defn kind
  ([value code]
   (or (-> code
           code->kind)
       (-> value
           value->kind))))

(defn advice [{:as context
               :keys [value code]}]
  (assoc context
         :kind (kind value code)))

(defn setup! []
  (kindly/add-advice! advice))
