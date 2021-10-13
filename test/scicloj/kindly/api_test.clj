(ns scicloj.kindly.resolve-test
  (:require [clojure.test :refer [deftest is]]
            [scicloj.kindly.kind :as kind]
            [scicloj.kindly.kindness :as kindness]
            [scicloj.kindly.api :as kindly]))

(def div-with-meta-tag-1
  (read-string
   "^kind/hiccup [:div]"))

(def div-with-meta-tag-2
  ^:hiccup [:div])

(deftest metadata->kind-test
  ;; TODO: Why does it fail?
  ;; (-> div-with-meta-tag-1
  ;;     meta
  ;;     kindly/metadata->kind
  ;;     (= kind/hiccup)
  ;;     is)
  (-> div-with-meta-tag-2
      meta
      kindly/metadata->kind
      (= kind/hiccup)
      is))

(deftest value->kind-test
  (-> [:div]
      (with-meta
        {:kindly/kind :hiccup})
      kindly/value->kind
      (= :hiccup)
      is)
  (-> [:div]
      kindly/value->kind
      nil?
      is))

(def dummy-behaviour
  {:render-src?   true
   :value->hiccup identity})

(deftype DummyType []
  kindness/Kindness
  (->behaviour [this]
    dummy-behaviour))

(kindly/assoc-kind-behaviour!
 :dummy dummy-behaviour)

(deftest value->behaviour-test
  (-> (DummyType.)
      kindly/value->behaviour
      (= dummy-behaviour)
      is)
  (-> [3]
      (with-meta {:kindly/kind :dummy})
      kindly/value->behaviour
      (= dummy-behaviour)
      is)
  (-> [3]
      kindly/value->behaviour
      nil?
      is))

