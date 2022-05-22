(ns scicloj.kindly.notebook1
  (:require [scicloj.kindly.v2.kind
             :as kind :refer [vega]]))

(defn vega-point-plot [data]
  (vega
   {:data {:values data},
    :mark "point"
    :encoding
    {:size {:field "w" :type "quantitative"}
     :x {:field "x", :type "quantitative"},
     :y {:field "y", :type "quantitative"},
     :fill {:field "z", :type "nominal"}}}))

(defn random-data [n]
  (->> (repeatedly n #(- (rand) 0.5))
       (reductions +)
       (map-indexed (fn [x y]
                      {:w (rand-int 9)
                       :z (rand-int 9)
                       :x x
                       :y y}))))

(defn random-vega-plot [n]
  (-> n
      random-data
      vega-point-plot))

(random-vega-plot 9)
