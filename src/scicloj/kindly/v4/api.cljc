(ns scicloj.kindly.v4.api
  (:require [scicloj.kindly.v4.fn :as fn]
            [scicloj.kindly.v4.advisors :as advisors]
            [scicloj.kindly.v4.context :as context]
            [scicloj.kindly.v4.kind :as kind]))

(def *advisors
  (atom []))

(defn advise
  ([context]
   (advise context @*advisors))
  ([context advisors]
   (-> context
       context/complete
       (#(reduce advisors/update-context
                 %
                 advisors))
       (update :advice vec))))

(defn consider [value kind]
  (cond (keyword? kind) (fn/attach-kind-to-value value kind)
        (fn? kind) (consider value (kind))))

(def known-kinds
  (->> 'scicloj.kindly.v4.kind
       find-ns
       ns-publics
       vals
       (map #(%))
       set))

(defn add-advisor! [advisor]
  (swap! *advisors conj advisor))

(defn set-advisors! [advisors]
  (reset! *advisors advisors))

(defn set-only-advisor! [advisor]
  (set-advisors! [advisor]))

(set-advisors! advisors/default-advisors)
