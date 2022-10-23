(ns scicloj.kindly.v3.defaults
  (:require [scicloj.kindly.v3.api :as kindly]
            [scicloj.kindly.v3.impl :as impl]
            [scicloj.kindly.v3.kindness :as kindness]))

(defn form->kind [form]
  (when-let [m (some-> form meta)]
    (or (some->> m
                 :tag
                 resolve
                 deref
                 namespace
                 (= "kind"))
        (some->> m
                 keys
                 (filter #(-> %
                              namespace
                              (= "kind")))
                 first))))

(defn value->kind-by-logic [value]
  (cond ;; (and (vector? value)
    ;;      (-> value first keyword?))
    ;; :kind/hiccup
    ;;
    (var? value)
    :kind/var
    ;;
    :else
    nil))

(defn value->kind [value]
  (or (-> value
          meta
          :kindly/kind)
      (-> value
          kindness/kind)
      (-> value
          value->kind-by-logic)))

(defn kind
  ([value form]
   (or (-> form
           form->kind)
       (-> value
           value->kind))))


(defn check-predicate-kinds [value predicate-kinds]
  (->> predicate-kinds
       (map (fn [[predicate k]]
              (when (predicate value)
                k)))
       (filter identity)
       first))

(def default-predicate-kinds
  [[(fn [v]
      (-> v type pr-str (= "tech.v3.dataset.impl.dataset.Dataset")))
    :kind/dataset]])

(defn create-advice
  ([]
   (create-advice {:predicate-kinds default-predicate-kinds}))
  ([{:keys [predicate-kinds]}]
   (fn [{:as context :keys [value form]}]
     (if (:kind context)
       context
       (assoc context
              :kind (or (kind value form)
                        (check-predicate-kinds value
                                               predicate-kinds)))))))

(extend-protocol kindness/Kindness
  java.awt.image.BufferedImage
  (kind [image]
    :kind/buffered-image))

(defn setup! []
  (kindly/set-only-advice! (create-advice)))
