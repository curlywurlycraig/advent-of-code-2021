(ns day-10.part1
  (:require [clojure.string :refer [split split-lines]]))


(def matching {"}" "{"
               ")" "("
               ">" "<"
               "]" "["})

(defn opening?
  [c]
  (some #(= c %) ["{" "(" "<" "["]))

(def lines (->> (slurp "res/day10/input.txt")
                split-lines
                (map #(apply list (split % #"")))))

(def score {")" 3
            "]" 57
            "}" 1197
            ">" 25137})

(defn find-error
  [line]
  (reduce
   (fn [st c]
     (if (opening? c)
       (conj st c)
       (if (= (matching c) (peek st))
         (pop st)
         (reduced c))))
   '()
   line))

(print "\n\n" (->> lines
                   (map find-error)
                   (remove list?)
                   (map score)
                   (apply +)))
