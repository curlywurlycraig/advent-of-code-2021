(ns day-4.part2
  (:require [clojure.string :refer [split split-lines trim]]
            [clojure.set :refer [subset?]]))

;; Parsing boards and input numbers

(defn parse-int
  [s]
  (Integer/parseInt s))

(def input-str (slurp "res/day4/input.txt"))

(def in-numbers-str (first (split-lines input-str)))
(def in-numbers (as-> in-numbers-str s
                  (split s #",")
                  (map parse-int s)))

(def boards-str (rest (split input-str #"\n\n")))

(defn board-from-str
  [bs]
  (->> bs
       split-lines
       (map trim)
       (map #(split % #"\s+"))
       (map #(map parse-int %))))

(def boards (map board-from-str boards-str))

;; Execution

(defn column
  "Returns column n from board."
  [board n]
  (map #(nth % n) board))

(defn wins?
  "Given some board and set of numbers, return true if the board is a winner.

  A board wins if it has either a row or a column that is a subset of the given numbers."
  [board numbers]
  (let [all-cols (map
                  (partial column board)
                  (range 5))
        all-lines (concat board all-cols)
        number-set (set numbers)
        all-sets (map set all-lines)]
    (some #(subset? % number-set)
          all-sets)))

(defn find-winners
  "Return the winners for a given set of boards and announced numbers."
  [boards numbers]
  (filter #(wins? % numbers) boards))

(defn count-score
  "Given a board and set of numbers, count the score for that board."
  [board numbers]
  (let [all-nums (apply concat board)]
    (* (last numbers)
       (apply + (filter
                 #(not (.contains numbers %))
                 all-nums)))))

(defn play
  "One by one, check for winners for the given numbers, adding a new number if there
  are no winners."
  [boards numbers]
   (loop [winning []
          remaining boards
          n 1]
     (if (empty? remaining)
       (count-score (last winning) (take (dec n) numbers))
       (let [new-winners (find-winners remaining (take n numbers))]
         (recur new-winners
                (filter #(not (.contains new-winners %)) remaining)
                (inc n))))))

