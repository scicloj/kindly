(ns scicloj.kindly.goldly-setup
  (:require [scicloj.kindly.v2.api :as kindly]
            [scicloj.kindly.v2.kind :as kind :refer [vega]]))

(kindly/add-behaviour!
 :tool/goldly
 {:kind :kind/vega
  :wrap (fn [v]
          ^:R ['user/vega v])
  :valid? (fn [v]
            (-> v :data some?))})


(defn value->vis-spec [v]
  (cond (kindly/kind? v) (-> v
                             (kindly/wrap :tool/goldly))
        :else ^:R ['user/hiccup [:p (pr-str v)]]))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

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

(def plot1
  (random-vega-plot 9))



(-> plot1
    (kindly/wrap :tool/goldly))


(-> plot1
    value->vis-spec)


(-> 27
    value->vis-spec)


[plot1 27]
