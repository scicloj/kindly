(defproject org.scicloj/kindly "3-alpha2-SNAPSHOT"
  :description "A small library for defining the kind of ways values should be displayed."
  :url "https://github.com/scicloj/kindly"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :plugins [[lein-tools-deps "0.4.5"]]
  :middleware [lein-tools-deps.plugin/resolve-dependencies-with-deps-edn]
  :lein-tools-deps/config {:config-files [:install :user :project]})
