(ns day-10.part2
  (:require [clojure.string :refer [split split-lines]]
            [clojure.set :refer [map-invert]]))

(def matching {"}" "{"
               ")" "("
               ">" "<"
               "]" "["})

(def matching-open (map-invert matching))

(defn opening?
  [c]
  (some #(= c %) ["{" "(" "<" "["]))

(def score {")" 1
            "]" 2
            "}" 3
            ">" 4})

(defn find-error
  "Returns the first character that is an error, or the whole list of incomplete chars."
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

(defn score-line
  "Given a set of remaining incomplete characters, return score."
  [line]
  (reduce (fn [total c]
            (+ (score (matching-open c)) (* 5 total)))
          0
          line))

(def lines (->> (slurp "res/day10/input.txt")
                split-lines
                (map #(apply list (split % #"")))))

(println (->> lines
              (map find-error)
              (remove #(not (list? %)))
              (map score-line)
              sort
              (#(nth % (quot (count %) 2)))))
