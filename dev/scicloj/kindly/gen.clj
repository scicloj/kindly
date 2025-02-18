(ns scicloj.kindly.gen
  "The Kindly specification is encapsulated in kinds.edn
  This code generates the namespaces of the Kindly library from kinds.edn
  so that it may be more conveniently consumed by users"
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.string :as str]))

(defn read-kinds []
  (-> (io/resource "kinds.edn")
      (slurp)
      (edn/read-string)))

(defn escape [s]
  (str/replace s "\"" "\\\""))

(defn docstring-attr [k v]
  (if (= k :raw-docstring)
    v
    (str (name k) ": "
      (case k
        :example (str "\n```clj\n" (escape v) "\n```\n")
        :examples (str "\n```clj\n" (str/join "\n" (map escape v)) "\n```\n") 
        (escape v)))))

(defn kind-fn [[kind attrs]]
  (let [kind-kw (keyword "kind" (name kind))]
    (str "(defn " kind \newline
         "  \""
         (str/join \newline
                   (for [[k v] attrs]
                     (docstring-attr k v)))
         "\"" \newline
         "  ([] " kind-kw ")" \newline
         "  ([value] (scicloj.kindly.v4.kind/" kind " value nil))" \newline
         "  ([value options] (kindly/attach-meta-to-value value {:kindly/kind " kind-kw " :kindly/options options})))"
         \newline)))

(defn kind-fns [all-kinds]
  (str/join (str \newline \newline)
            (for [[category kinds] all-kinds]
              (str ";; ## " category \newline \newline
                   (str/join \newline
                             (map kind-fn kinds))))))

(defn excludes [all-kinds]
  (let [cc (find-ns 'clojure.core)]
    (vec (for [[category kinds] all-kinds
               [kind] kinds
               :when (ns-resolve cc kind)]
           kind))))

(defn kind-ns [all-kinds]
  (str "(ns scicloj.kindly.v4.kind
  \"Kinds for visualization\"
  (:require [scicloj.kindly.v4.api :as kindly])
  (:refer-clojure :exclude " (excludes all-kinds) "))

" (kind-fns all-kinds) \newline))

(defn known-kinds [all-kinds]
  (str "(def known-kinds
  \"A set of common visualization requests\"
  #{
" (str/join \newline
            (for [[category kinds] all-kinds]
              (str ";; " category \newline
                   (str/join \newline
                             (for [[kind] kinds
                                   :let [kind-kw (keyword "kind" (name kind))]]
                               (str "    " kind-kw))))))
       "})"
       \newline))

(defn api-ns [all-kinds]
  (str "(ns scicloj.kindly.v4.api
  \"See the kind namespace\")

(defn attach-meta-to-value
  [value m]
  (if (instance? clojure.lang.IObj value)
    (vary-meta value merge m)
    (attach-meta-to-value [value] m)))

(defn attach-kind-to-value
  [value kind]
  (attach-meta-to-value value {:kindly/kind kind}))

(defn deep-merge
  \"Recursively merges maps only.\"
  [& xs]
  (->> xs
       (remove nil?)
       (reduce (fn m [a b]
                 (if (and (map? a) (map? b))
                   (merge-with m a b)
                   b))
               {})))

(defn get-options
  []
  (-> (meta *ns*) :kindly/options))

(defn hide-code
  \"Annotate whether the code of this value should be hidden\"
  ([value]
    (hide-code value true))
  ([value bool]
   ;; Will change when Clay is updated
   (attach-meta-to-value value {:kindly/hide-code bool})))

(defn set-options!
  \"Replaces *options* with options\"
  [options]
  (hide-code
    (attach-kind-to-value
      (alter-meta! *ns* merge {:kindly/options options})
      :kind/hidden)))

(defn merge-options!
  \"Mutates *options* with the deep merge of options\"
  [options]
  (hide-code
    (attach-kind-to-value
      (alter-meta! *ns* deep-merge {:kindly/options options})
      :kind/hidden)))

(defn consider
  \"Add metadata to a given value.
A values which cannot have metadata
(i.e., is not an instance of `IObj`)
is wrapped in a vector first\"
  [value m]
  (cond (keyword? m) (attach-kind-to-value value m)
        (fn? m) (consider value (m))
        (map? m) (attach-meta-to-value value m)))

(defn check
  \"Add a generated test using `:kind/test-last`\"
  [& args]
  (consider args :kind/test-last))

" (known-kinds all-kinds)))

(defn -main [& args]
  (let [all-kinds (read-kinds)]
    (->> (kind-ns all-kinds)
         (spit (io/file "src" "scicloj" "kindly" "v4" "kind.cljc")))
    (->> (api-ns all-kinds)
         (spit (io/file "src" "scicloj" "kindly" "v4" "api.cljc")))))

(comment
  (-main))
