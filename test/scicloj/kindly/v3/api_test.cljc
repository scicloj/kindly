(ns scicloj.kindly.v3.api-test
  (:require [scicloj.kindly.v3.api :as kindly]
            [scicloj.kindly.v3.defaults :as defaults]
            [scicloj.kindly.v3.kind :as kind]
            [clojure.test :refer [deftest is]]))

(deftest no-advices-test
  (let [original-context {:code "(+ 1 2)"
                          :value 3}]
    (-> original-context
        (kindly/advice [])
        (= original-context)
        is)))

(deftest custom-advice-test
  (let [original-context {:code "(+ 1 2)"
                          :value 3}
        desired-context {:value [:h1 "This is the value 3!"]
                         :code "(+ 1 2) ; computation!"
                         :kind :kind/hiccup}
        advice (fn [{:as context
                     :keys [value code]}]
                 (if (= value 3)
                   desired-context
                   context))]
    (-> original-context
        (kindly/advice [advice])
        (= desired-context)
        is)))

(deftest multiple-advices-test
  (let [original-context {:code "(+ 1 2)"
                          :value 3}
        desired-context1 {:value [:h1 "This is the value 3!"]
                          :code "(+ 1 2) ; computation!"
                          :kind :kind/hiccup}
        desired-context2 {:value [:h1 "This is the value 3!"]
                          :code "(+ 1 2) ; computation!!"
                          :kind :kind/hiccup}
        advice1 (fn [{:as context
                      :keys [value code]}]
                  (if (= value 3)
                    desired-context1
                    context))
        advice2 (fn [{:as context
                      :keys [value code]}]
                  (if (= value 3)
                    desired-context2
                    context))]
    (-> original-context
        (kindly/advice [advice1
                        advice2])
        (= desired-context1)
        is)))
