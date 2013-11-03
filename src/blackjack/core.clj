(ns blackjack.core
  "Main namespace for the blackjack game; provides initial point of execution."
  (:require [blackjack.game :as game]
            [blackjack.display :as display]
            [blackjack.dealer :as dealer]
            [blackjack.score :as score])
  (:gen-class))

(defn -main
  "Run a single instance of the Blackjack game. This will execute the main game
   loop and continually run until the user enters the 'q key to quit. A
   breakdown of the execution flow is as follows:

   1. Print the overall 'welcome' banner to the screen
   2. Print the rules and controls to the screen
   3. Initialize the main game loop
      1. Display the console (dealer's hand, statistics, and player's hand)
      2. Wait for user input
      3. Determine what action to take based on the provided input
      4. Check game mechanics to determine win, loss, push, or a continued round
   4. Once the user quits the game, print the final statistics"
  []
  (println display/banner)
  (println display/rules)
  (loop [[player-hand dealer-hand deck] (game/setup)
         curr-round 1
         [wins losses pushes] [0 0 0]]
    (let [user-action (display/main-prompt curr-round player-hand dealer-hand wins losses pushes)]
      (condp = user-action
        ;; Handle a 'quit' command
        "q" (do
              (display/overall-stats wins losses pushes (dec curr-round))
              (System/exit 0))
        ;; Handle a 'hit' command
        "h" (let [player-hand (conj player-hand (first deck))
                  deck (rest deck)
                  player-score (score/eval-hand player-hand)]
              (if (> player-score 21) ; check for bust
                (do
                  (display/results player-score (last player-hand) :bust)
                  (recur (game/new-deck? deck curr-round) (inc curr-round) [wins (inc losses) pushes]))
                (recur [player-hand dealer-hand deck] curr-round [wins losses pushes])))
        ;; Handle a 'stay' command
        "s" (let [[dealer-score dealer-hand deck] (dealer/run-hand dealer-hand deck)
                  player-score (score/eval-hand player-hand)
                  results (partial display/results player-score dealer-score dealer-hand)]
              (recur (game/new-deck? deck curr-round) (inc curr-round)
                     (cond
                      (and (< dealer-score 22)
                           (< dealer-score player-score)) (do (results :win) [(inc wins) losses pushes])
                      (and (< dealer-score 22)
                           (> dealer-score player-score)) (do (results :lose) [wins (inc losses) pushes])
                      (and (< dealer-score 22)
                           (= dealer-score player-score)) (do (results :push) [wins losses (inc pushes)])
                      :else (do (results :dealer-bust) [(inc wins) losses pushes]))))
        ; else
        (do
          (println "\nWARN: Valid keys are 'h (hit), 's (stay), and 'q (quit).\n")
          (recur [player-hand dealer-hand deck] curr-round [wins losses pushes]))))))
