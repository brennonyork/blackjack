(ns blackjack.game
  "Namespace to execute the core loop and functionality of an individual
   blackjack round"
  (:require [blackjack.deck :as deck]
            [blackjack.display :as display])
  (:gen-class))

(defn- deal
  "Given a deck will ensure a correct dealing of cards. First to the player,
   then a 'face down' card to the dealer, again to the player, and finally a
   'face up' care to the dealer. The player's hand, dealer's hand, and deck
   are returned."
  [deck]
  (loop [player-hand []
          dealer-hand []
          deck deck]
     (if (and (= (.length player-hand) 2)
              (= (.length dealer-hand) 2))
       [player-hand dealer-hand deck]
       (let [player-hand (conj player-hand (first deck))
             deck (rest deck)
             dealer-hand (conj dealer-hand (first deck))
             deck (rest deck)]
         (recur player-hand dealer-hand deck)))))

(defn setup
  "Sets the game by either shuffling a new deck or using a provided one and
   returning the player's hand, the dealer's hand, and the rest of the
   available deck."
  ([]
   (deal (deck/shuffled-deck)))
  ([deck]
   (deal deck)))

(defn new-deck?
  "Checks the current round to determine if a new deck is needed. If so a new
   deck is generated and each player's hand is drawn from that, otherwise the
   original deck will be drawn from. Returned is a vector containing the
   player's hand, dealer's hand, and the rest of the deck."
  [deck curr-round]
  (if (= (mod curr-round 6) 0)
    (setup)        ; Once every 6 rounds we use a new deck
    (setup deck))) ; else use the old deck
