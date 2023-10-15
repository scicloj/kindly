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

(defn kind-fn [[kind attrs]]
  (let [kind-kw (keyword "kind" (name kind))]
    (str "(defn " kind \newline
         "  \""
         (str/join \newline
                   (for [[k v] attrs]
                     (str (name k) ": " (escape v))))
         "\"" \newline
         "  ([] " kind-kw ")" \newline
         "  ([value] (attach-kind-to-value value " kind-kw ")))" \newline)))

(defn kind-fns [all-kinds]
  (str/join (str \newline \newline)
            (for [[category kinds] all-kinds]
              (str ";; ## " category \newline \newline
                   (str/join \newline
                             (map kind-fn kinds))))))

(defn kind-ns [all-kinds]
  (str "(ns scicloj.kindly.v4.kind
  \"Kinds for visualization\"
  (:require [scicloj.kindly.v4.api :refer [attach-kind-to-value]]))

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

(defn attach-kind-to-value
  \"Prefer using the functions in the kind namespace instead\"
  [value kind]
  (if (instance? clojure.lang.IObj value)
    (vary-meta value assoc :kindly/kind kind)
    (attach-kind-to-value [value] kind)))

(defn consider
  \"Prefer using the functions in the kind namespace instead\"
  [value kind]
  (cond (keyword? kind) (attach-kind-to-value value kind)
        (fn? kind) (consider value (kind))))

" (known-kinds all-kinds)))

(defn -main [& args]
  (let [all-kinds (read-kinds)]
    (->> (kind-ns all-kinds)
         (spit (io/file "src" "scicloj" "kindly" "v4" "kind.cljc")))
    (->> (api-ns all-kinds)
         (spit (io/file "src" "scicloj" "kindly" "v4" "api.cljc")))))

(comment
  (-main))
