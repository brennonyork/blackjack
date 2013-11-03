(ns blackjack.dealer
  "A namespace for all dealer actions"
  (:require [blackjack.score :as score])
  (:gen-class))

(defn- soft-17?
  "Predicate to determine if the hand is considered a 'soft 17' or, more
   formally if it contains an Ace with a value of 11 and some additional set
   of cards equaling 6."
  [hand]
  (let [;; Map all kings, queens, and jacks into a value of 10
        numeric-hand (map (fn [card]
                            (if (some #(= (nth card 0) %) ['k 'j 'q])
                              [10 :of (nth card 2)]
                              card)) hand)
        ;; Sort so all ace cards are the last in the list
        ace-sorted-hand (vec (reverse (sort-by first
                                               (fn [x y]
                                                 (compare (str x) (str y))) numeric-hand)))]
    (if (not (= (first (first ace-sorted-hand)) 'a)) ; ensure the first card is an ace
      false
      (if (= (first (nth ace-sorted-hand 1)) 'a) ; ensure no other ace's exist afterwards (sorting grants this)
        false
        (if (= (score/eval-hand ; sum the rest of the cards and determine if it equals 6
                (rest ace-sorted-hand))
               6)
          true
          false)))))

(defn display-hand
  "Since the dealer initially deals his first card down we want to ensure that
   this is correctly displayed to the user until the dealer executes on his
   dealt hand. 'hand' is considered a vector of two cards.
   e.x. [[a :of spades] [2 :of clubs]]"
  [hand]
  {:pre [(and (= (type hand) clojure.lang.PersistentVector)
              (= (count hand) 2))]
   :post [(and (= (type %) clojure.lang.PersistentVector)
               (= (nth % 0) :showing)
               (= (type (nth % 1)) clojure.lang.PersistentVector))]}
  [:showing (nth hand 1)])

(defn run-hand
  "Given the current deck of cards and the dealer's hand, will run the current
   hand until a score of hard 17 (a 17 without an ace) or above is achieved."
  [hand deck]
  {:pre [(and (= (type hand) clojure.lang.PersistentVector)
              (= (count hand) 2))]}
  (loop [hand hand
         deck deck]
    (let [curr-score (score/eval-hand hand)]
      (if (and (not (soft-17? hand)) ; ensure not on a soft-17 situation
               (> curr-score 16))    ; wait until score is greater than 16
        [curr-score hand deck]
        (recur (conj hand (first deck)) (rest deck))))))
