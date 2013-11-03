(ns blackjack.score
  "Maintains the scoring algorithm for blackjack"
  (:gen-class))

(defn eval-hand
  "Returns the current score for the dealer hand provided.

   The algorithm works by first mapping kings, queens, and jacks into a '10'
   value followed by sorting the cards so all numeric values are present
   first. These are then summed up with the ace values last. Once the first
   ace is seen it is determined if it is the last card in the deck or not. If
   not we can assure the value of that said ace to be '1'. This loop continues
   until the last ace is seen in which the program determines whether adding
   '11' would bust the dealer or not."
  [hand]
  {:post [(= (type %) java.lang.Long)]}
  (let [;; Map all kings, queens, and jacks into a value of 10
        numeric-hand (map (fn [card]
                            (if (some #(= (nth card 0) %) ['k 'j 'q])
                              [10 :of (nth card 2)]
                              card)) hand)
        ;; Sort so all ace cards are the last in the list
        ace-sorted-hand (sort-by first
                                 (fn [x y]
                                   (compare (str x) (str y))) numeric-hand)]
    (loop [hand ace-sorted-hand
           score 0]
      (if (empty? hand)
        score
        (let [card-val (nth (first hand) 0)]
          (cond
           ;; if we've found an ace at the end of the list and we have more ace's
           (and (= card-val 'a)
                (not (empty? (rest hand)))) (recur (rest hand) (+ 1 score))
           ;; else if we have an ace with no other cards after
           (= card-val 'a) (recur (rest hand) (if (< (+ score 11) 22)
                                                (+ score 11)
                                                (+ score 1)))
           :else
           (recur (rest hand) (+ (nth (first hand) 0) score))))))))
