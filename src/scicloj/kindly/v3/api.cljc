(ns scicloj.kindly.v3.api
  (:require [scicloj.kindly.v3.impl :as impl]))

(def *advisors
  (atom []))

(defn advice
  ([context]
   (advice context @*advisors))
  ([{:as context}
    advisors]
   (loop [current-context context
          remaining-advisors advisors]
     (if (:kind current-context)
       current-context
       (if-let [advisor1 (first remaining-advisors)]
         (recur (advisor1 context)
                (rest remaining-advisors))
         current-context)))))

(defn consider [value kind]
  (cond (keyword? kind) (impl/attach-kind-to-value value kind)
        (fn? kind) (consider value (kind))))

(defn add-kind! [kind]
  (impl/define-kind! kind)
  kind)

(defn known-kinds []
  @impl/*kinds-set)

(defn add-advisor! [advisor]
  (swap! *advisors conj advisor))

(defn set-advisors! [advisors]
  (reset! *advisors advisors))

(defn set-only-advisor! [advisor]
  (set-advisors! [advisor]))
