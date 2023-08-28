(ns scicloj.kindly.v4.advisors
  (:require [scicloj.kindly.v4.impl :as impl]))

(defn predicate-based-advisor
  ([{:keys [predicate-kinds]}]
   (fn [{:as context :keys [form value kind]}]
     (assoc context
            :kind
            (or kind
                (impl/form->kind form)
                (impl/value->kind value)
                (impl/check-predicate-kinds value
                                            predicate-kinds))))))

(def default-predicate-kinds
  [[(fn [v]
      (-> v
          type
          pr-str
          (= "tech.v3.dataset.impl.dataset.Dataset")))
    :kind/dataset]
   [(fn [v]
      (some-> v
              meta
              :test
              fn?))
    :kind/test]
   [var? :kind/var]
   [map? :kind/map]
   [set? :kind/set]
   [vector? :kind/vector]
   [sequential? :kind/seq]
   [(fn [v]
      (-> v
          type
          pr-str
          (= "java.awt.image.BufferedImage")))
    :kind/buffered-image]])

(defn default-advisor []
  (predicate-based-advisor
   {:predicate-kinds default-predicate-kinds}))
