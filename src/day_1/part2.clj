(ns day-1.part2
  (:require [clojure.core :refer [partition]]
            [clojure.string :refer [split-lines]]))

(defn parse-int
  "Given a string, returns an integer."
  [s]
  (. Integer parseInt s))
  

(defn main
  [args]
  (print (let [counts (->> (slurp "res/day1/input.txt")
                           (split-lines)
                           (map parse-int)
                           (partition 3 1)
                           (map (partial apply +)))]
           (count
            (filter (partial apply <)
                    (partition 2 1 counts))))))

