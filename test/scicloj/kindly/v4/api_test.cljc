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
         {:a {:b {:c 10}}})
      "should merge nil")
  (is (= (kindly/deep-merge {:a {:b {:c 10}}}
                            nil)
         {:a {:b {:c 10}}})
      "nils are ignored")
  (is (= (kindly/deep-merge {:a {:b {:c 10}}}
                            {:a {:b {:c 9 :d 11}}}
                            {:a {:b {:d 12}}})
         {:a {:b {:c 9 :d 12}}})
      "more than two arguments are allowed")
  (is (= (kindly/deep-merge)
         {})
      "no arguments mean an empty map")
  (is (= (kindly/deep-merge {:a 1} ^:replace {:b 2})
         {:b 2})))

(deftest options-test
  (is (get-in (meta (kindly/attach-meta-to-value 4 {:kindly/kind :code}))
              [:kindly/options :wrapped-value])
      "wrapped values should have annotations in metadata")

(is (not= (meta (kindly/attach-meta-to-value  4  {:kindly/kind :code}))
          (meta (kindly/attach-meta-to-value [4] {:kindly/kind :code})))
    "wrapped values should be distinguishable from single-element vectors")

  (kindly/set-options! {:foo "bar"})
  (is (= "bar" (-> (kindly/get-options) :foo))
      "setting options should work")
  (kindly/merge-options! {:foo "baz"})
  (is (= "baz" (-> (kindly/get-options) :foo))
      "merging options should work"))
