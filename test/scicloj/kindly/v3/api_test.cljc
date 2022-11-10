(ns scicloj.kindly.v3.api-test
  (:require [scicloj.kindly.v3.api :as kindly]
            [tech.v3.dataset :as ds]

            [clojure.test :refer [deftest is]]))

(deftest map-string
  (is (=  [{:kind  :kind/clojure.core.string}]
          (kindly/advice {:value "string"})))
  (is (=  [{:kind :kind/clojure.core.map}]
          (kindly/advice {:value {}}))))

(deftest unknown
  (is (=  []
          (kindly/advice {:value nil}))))


(def vl {:$schema "https://vega.github.io/schema/vega-lite/v5.json"
         :data {:url "data/cars.json"}
         :description "A scatterplot showing horsepower and miles per gallons for various cars."
         :encoding {:x {:field "Horsepower" :type "quantitative"}
                    :y {:field "Miles_per_Gallon" :type "quantitative"}}
         :mark "point"})

(deftest vltest
  ;;  is a map and vl
  (is (= [{:kind :kind/clojure.core.map}
          {:kind :kind/vega.org-vega-lite}]

         (kindly/advice {:value
                         (with-meta
                           vl
                           {:kind :kind/vega.org-vega-lite})}))))

(deftest dataset
  (is (= [{:kind :kind/clojure.core.map}
          {:kind :kind/tech.ml.dataset}]
         (kindly/advice {:value (ds/->dataset {})}))))


(deftest tex-with-form-metadata
  (is (= [{:kind :kind/clojure.core.string}
          {:kind :kind/www.latex-project.org-latex}]
         (kindly/advice {:value "\\alpha"
                         :form (with-meta
                                 '(def tex "\\alpha")
                                 {:kind :kind/www.latex-project.org-latex})}))))



(deftest unknown-ignored
  (is (= [{:kind :kind/clojure.core.map}]
         (kindly/advice {:value
                         (with-meta
                           vl
                           {:kind :kind/unknown})}))))

(comment
  (kindly/kinds))
  ;; => (:kind/clojure.core.map
  ;;     :kind/clojure.core.string
  ;;     :kind/tech.ml.dataset
  ;;     :kind/vega.org-vega-lite
  ;;     :kind/www.latex-project.org-latex)
