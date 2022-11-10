(ns scicloj.kindly.v3.api
  (:require [scicloj.kindly.v3.impl :as impl]))

(def KINDS {:kind/clojure.core.map (fn [{:keys [value]}] (map? value))

            :kind/clojure.core.string (fn [{:keys [value]}] (string? value))

            :kind/tech.ml.dataset (fn [{:keys [value]}] ((requiring-resolve 'tech.v3.dataset/dataset?) value))

            :kind/vega.org-vega-lite (fn [{:keys [value]}]  (= :kind/vega.org-vega-lite
                                                             (-> value meta :kind)))

            :kind/www.latex-project.org-latex (fn [{:keys [form value]}]
                                                (or
                                                 (= :kind/www.latex-project.org-latex
                                                    (-> form meta :kind))
                                                 (= :kind/www.latex-project.org-latex
                                                    (-> value meta :kind))))})




(defn advice
  "Advises what `kind` of data is passed in.
  context can have keys:
  `value` the data value to advice on
  `form` the form which created the value

  Kindly will guess which kind of value it is.
  It will return a map, which keys are a strict subset of all kinds known to Kindly,
  (returned by `kinds` function).

  "
  [context]
  (->>
   (map
    (fn [kind pred] (when (pred context)
                     {:kind kind}))
    (keys KINDS)
    (vals KINDS))
   (remove nil?)))


(defn kinds []
  (keys KINDS))


(requiring-resolve 'tech.v3.dataset/dataset?)
