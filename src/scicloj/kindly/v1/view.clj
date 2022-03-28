(ns scicloj.kindly.v1.view
  (:require [clojure.string :as string]
            [clojure.pprint :as pp]
            [scicloj.kindly.v1.util :as util]))


(defn value->naive-hiccup [value]
  [:p/code {:code (-> value
                      pp/pprint
                      with-out-str)}])

(defn markdowns->hiccup [mds]
  (if-not (sequential? mds)
    (markdowns->hiccup [mds])
    [:p/markdown
     (->> mds
          (map #(-> % print with-out-str))
          (string/join "\n"))]))

(defn safe-value [x]
  (if (instance? java.time.LocalDateTime x)
    (.toInstant ^java.time.LocalDateTime x)
    x))

(def status-style
  {:style {:font-style  "italic"
           :font-family "\"Lucida Console\", Courier, monospace"}})

(defn status-description->hiccup [status-description]
  [:p status-style
   [:small (format "(%s)" status-description)]])

(defn dataset->grid-hiccup [ds]
  (let [ds (if (sequential? ds)
             (util/map-coll->key-vector-map ds)
             ds)
        max-n-rows          100
        string-column-names (->> ds
                                 keys
                                 (map name))
        column-defs         (->> string-column-names
                                 (mapv (fn [k-str]
                                         {:headerName k-str
                                          :field      k-str})))
        columns             (->> ds
                                 vals
                                 (map (partial take max-n-rows)))
        row-data            (apply
                             map
                             (fn [& row-values]
                               (->> row-values
                                    (map safe-value)
                                    (zipmap string-column-names)))
                             columns)]
    [:div {:class "ag-theme-balham"
           :style {:height "150px"}}
     (status-description->hiccup
      (format "showing at most %d rows" max-n-rows))
     [:p/dataset {:columnDefs column-defs
                  :rowData    row-data}]]))

(defn dataset->md-hiccup [mds]
  (let [height (* 46 (- (count (string/split-lines (str mds))) 2))
        height-limit (min height 400)]
    [:div {:class "table table-striped table-hover table-condensed table-responsive"
           :style {:height (str height-limit "px")}}
     (markdowns->hiccup mds)]))

(defn bool->symbol [bool]
  (if bool
    [:big [:big {:style {:color "darkgreen"}}
           "✓"]]
    [:big [:big {:style {:color "darkred"}}
           "❌"]]))

(defn test-boolean->hiccup [bool]
  [:div
   (bool->symbol bool)
   (str "   " bool)])
