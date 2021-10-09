(ns scicloj.kindly.util-test
  (:require [clojure.test :refer [deftest is]]
            [scicloj.kindly.util :as util]))

(deftest map-coll->key-vector-map-test
  (-> [{:x 1 :y "a" :z true}
       {:x 2 :z false}]
      util/map-coll->key-vector-map
      (= {:x [1 2],
          :y ["a" nil],
          :z [true false]})
      is))

(deftest wrap-test
  (->> "hello-friend"
       (util/wrap :h1)
       (= [:h1 "hello-friend"])
       is)
  (->> ["hello" "friend"]
       (util/wrap :h1)
       (= [:div
           [:h1 "hello"]
           [:h1 "friend"]])
       is))
