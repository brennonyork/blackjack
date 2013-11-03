(ns blackjack.deck
  "Namespace to handle all deck-related state and functionality"
  (:gen-class))

(defn shuffled-deck
  "Returns a new deck of shuffled cards (randomized) as a vector of vectors
   with each vector in the form of [card suit]. Face cards are noted as a
   symbol representing their first letter (i.e. an Ace would be 'a).

   Ex. A deck of three cards might look like:
       ([9 :of spades] [q :of diamonds] [8 :of spades])"
  []
  {:post [(and (= (type %) clojure.lang.PersistentVector)
               (= (count %) 52))]}
  (shuffle (for [card (conj (range 2 11) 'j 'q 'k 'a)
                 suit ['hearts 'diamonds 'spades 'clubs]]
             [card :of suit])))
