(defproject org.scicloj/kindly "1-alpha1"
  :description "A small library for defining how different kinds of things should be rendered"
  :url "https://github.com/scicloj/kindly"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :plugins [[lein-tools-deps "0.4.5"]
            [lein-codox "0.10.7"]]
  :middleware [lein-tools-deps.plugin/resolve-dependencies-with-deps-edn]

  :lein-tools-deps/config {:config-files [:install :user :project]})

