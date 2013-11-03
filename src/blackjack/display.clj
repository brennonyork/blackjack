(ns blackjack.display
  "Maintains all display and pretty-print functionality for Blackjack"
  (:require [blackjack.dealer :as dealer])
  (:gen-class))

;; Main banner or splash screen upon entry
(def banner "Welcome to Blackjack!\n")

;; Outlines the controls for the game
(def rules
"Keyboard actions:
\t'h : hit
\t's : stay
\t'q : quit\n")

;; Banner to display after a deck needs to be 'reshuffled'
(def shuffle-banner ":info:> Using a newly shuffled deck.\n")

;; Prompt definition for the dealer
(def dealer-prompt ":deal:>")

;; Prompt definition for the statistics output
(def statistics-prompt ":stat:>")

;; Prompt definition for the player
(def player-prompt ":play:>")

(defn- stat-percent
  "Calculates a percentage (e.g. wins, losses, etc.) and returns the value as
   a string-formatted float."
  [stat-count num-rounds]
  (format "%.2f" (if (> num-rounds 0)
                   (* (float (/ stat-count num-rounds)) 100)
                   (float 0))))

(defn- format-outcome
  "Formats the key provided as 'outcome' into a capitalized, left-justified
   string."
  [outcome]
  (clojure.string/upper-case (format "%-4s" (name outcome))))

(defn main-prompt
  "Reads user input with a console that presents the current round of play.
   Returns the user input and the current round."
  [round player-hand dealer-hand wins losses pushes]
  (let [stat-round (dec round)]
    (do
      (if (= (mod round 6) 0)
        (println shuffle-banner))
      (if (= (.length dealer-hand) 2)
        (println dealer-prompt (dealer/display-hand dealer-hand))
        (println dealer-prompt dealer-hand))
      (println statistics-prompt   [:wins (stat-percent wins stat-round) "%"
                                    :losses (stat-percent losses stat-round) "%"
                                    :pushes (stat-percent pushes stat-round) "%"])
      (println player-prompt player-hand)
      (print ":" (format "%02d" round) ":> ")
      (flush)
      (read-line))))

(defn results
  "Display results from the end of a given round as well as the final hand of
   the dealer."
  ([player-score dealer-score dealer-hand outcome]
   (if (= outcome :dealer-bust)
     (do
       (println ":WIN :> Score:" player-score "vs. Dealer:" dealer-score "(bust)")
       (println "\tw/Dealer hand:" dealer-hand "\n"))
     (do
       (println (str ":" (format-outcome outcome) ":> Score:") player-score "vs. Dealer:" dealer-score)
       (println "\tw/Dealer hand:" dealer-hand "\n"))))
  ([player-score player-card outcome]
   {:pre [(= outcome :bust)]}
   (println (str ":" (format-outcome outcome) ":> Score:") player-score "with final card" player-card "\n")))

(defn overall-stats
  "Prints basic overall statistics after finishing the game."
  [wins losses pushes num-rounds]
  (if (> num-rounds 0)
    (do
      (println "\nFinal Statistics")
      (println ":WIN :> Overall:" wins)
      (println "\tPercent:" (stat-percent wins num-rounds) "%")
      (println ":LOSE:> Overall:" losses)
      (println "\tPercent:" (stat-percent losses num-rounds) "%")
      (println ":PUSH:> Overall:" pushes)
      (println "\tPercent:" (stat-percent pushes num-rounds) "%"))))
