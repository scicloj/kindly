(ns scicloj.kindly.v4.api-test
  (:require [clojure.test :refer [deftest is testing]]
            [scicloj.kindly.v4.api :as kindly]))

(deftest deep-merge-test
  (is (= :foo (kindly/deep-merge :foo))
      "should not break when given non-maps")
  (is (= (kindly/deep-merge :foo :bar {:baz 100})
         {:baz 100})
      "should return the last item when merging is not possible")
  (is (= (kindly/deep-merge {:a {:b {:c 10}}}
                            {:a {:b {:c 11}}})
         {:a {:b {:c 11}}})
      "should update deeply nested paths")
  (is (= (kindly/deep-merge {:a {:b {:c 10}}}
                            {:a {:b nil}})
         {:a {:b nil}})
      "should not merge nil, nil is used as a value for erasure"))

(deftest options-test
  (kindly/set-options! {:foo "bar"})
  (is (= "bar" (-> (kindly/get-options) :foo))
      "setting options should work")
  (kindly/merge-options! {:foo "baz"})
  (is (= "baz" (-> (kindly/get-options) :foo))
      "merging options should work"))
