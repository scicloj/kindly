(ns scicloj.kindly.v4.context)

(defn eval-in-ns [ns form]
  (if ns
    (binding [*ns* ns]
      (eval form))
    (eval form)))

(defn complete-value [{:keys [ns form]
                       :as context}]
  (if (contains? context :value)
    context
    (if (contains? context :form)
      (assoc context
             :value (eval-in-ns ns form))
      (throw (ex-info "context missing both form and value"
                      {:context context})))))

(defn form-meta-kind [form]
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

(defn value-meta-kind [value]
  (-> value
      meta
      :kindly/kind))

(defn complete-meta-kind [{:keys [form value]
                           :as context}]
  (assoc context
         :meta-kind (or (form-meta-kind form)
                        (value-meta-kind value))))

(defn complete [context]
  (-> context
      complete-value
      complete-meta-kind))
