(ns scicloj.kindly.v3.api-test
  (:require [scicloj.kindly.v3.api :as kindly]
            [scicloj.kindly.v3.kind :as kind]
            [clojure.test :refer [deftest is]]))

(deftest no-advisors-test
  (let [original-context {:code "(+ 1 2)"
                          :value 3}]
    (-> original-context
        (kindly/advice [])
        (= [original-context])
        is)))

(deftest custom-advisor-test
  (let [original-context {:code "(+ 1 2)"
                          :value 3}
        desired-context {:value [:h1 "This is the value 3!"]
                         :code "(+ 1 2) ; computation!"
                         :kind :kind/hiccup}
        advisor (fn [{:as context
                      :keys [value code]}]
                  (if (= value 3)
                    desired-context
                    context))]
    (-> original-context
        (kindly/advice [advisor])
        (= [desired-context])
        is)))

(deftest multiple-kinds-advisor-test
  (let [original-context {:code "(+ 1 2)"
                          :value 3}
        desired-contexts [{:value [:h1 "This is the value 3!"]
                           :code "(+ 1 2) ; computation!"
                           :kind :kind/hiccup}
                          {:value {:the-value-is 3}
                           :code "(+ 1 2) ; computation!"
                           :kind :kind/pprint}]
        advisor (fn [{:as context
                      :keys [value code]}]
                  (if (= value 3)
                    desired-contexts
                    context))]
    (-> original-context
        (kindly/advice [advisor])
        (= desired-contexts)
        is)))

(deftest multiple-advisors-test
  (let [original-context {:code "(+ 1 2)"
                          :value 3}
        desired-context1 {:value [:h1 "This is the value 3!"]
                          :code "(+ 1 2) ; computation!"
                          :kind :kind/hiccup}
        desired-context2 {:value [:h1 "This is the value 3!"]
                          :code "(+ 1 2) ; computation!!"
                          :kind :kind/hiccup}
        advisor1 (fn [{:as context
                       :keys [value code]}]
                   (if (= value 3)
                     desired-context1
                     context))
        advisor2 (fn [{:as context
                       :keys [value code]}]
                   (if (= value 3)
                     desired-context2
                     context))]
    (-> original-context
        (kindly/advice [advisor1
                        advisor2])
        (= [desired-context1
            desired-context2])
        is)))
