(ns day-1.part1
  (:require [clojure.core :refer [partition]]
            [clojure.string :refer [split-lines]]))

(defn parse-int
  "Given a string, returns an integer."
  [s]
  (. Integer parseInt s))
  

(defn main
  [args]
  (print (let [vals (->> (slurp "res/day1/input.txt")
                         (split-lines)
                         (map parse-int))]
           (count
            (filter (partial apply <)
                    (partition 2 1 vals))))))

