(ns day-6.part1
  (:require [clojure.string :refer [split trim]]))

(def fish-days (as-> (slurp "res/day6/input.txt") inp
                 (trim inp)
                 (split inp #",")
                 (mapv #(. Integer parseInt %) inp)))

(defn update-fish-days
  [fd]
  (reduce
   (fn [new-days day]
     (let [updated (dec day)]
       (if (neg? updated)
         (conj new-days 6 8)
         (conj new-days updated))))
   []
   fd))

(print (count (nth (iterate update-fish-days fish-days) 80)))
