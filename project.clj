(defproject blackjack "2.0.0"
  :description "Simple command-line blackjack game"
  :dependencies [[org.clojure/clojure "1.5.1"]]
  :plugins [[lein-marginalia "0.7.1"]]
  :aot :all
  :main blackjack.core
  :profiles {:uberjar {:aot :all}}
  :aliases {"docs" "marg"})
