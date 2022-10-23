(ns scicloj.kindly.v3.api
  (:require [scicloj.kindly.v3.impl :as impl]))

(def *advices
  (atom []))

(defn advice
  ([context]
   (advice context @*advices))
  ([{:as context}
    advices]
   (loop [current-context context
          remaining-advices advices]
     (if (:kind current-context)
       current-context
       (if-let [advice1 (first remaining-advices)]
         (recur (advice1 context)
                (rest remaining-advices))
         current-context)))))

(defn consider [value kind]
  (cond (keyword? kind) (impl/attach-kind-to-value value kind)
        (fn? kind) (consider value (kind))))

(defn add-kind! [kind]
  (impl/define-kind! kind)
  kind)

(defn known-kinds []
  @impl/*kinds-set)

(defn add-advice! [advice]
  (swap! *advices conj advice))

(defn set-advices! [advices]
  (reset! *advices advices))

(defn set-only-advice! [advice]
  (set-advices! [advice]))
