(ns day-2.part1
  (:require [clojure.string :refer [split-lines split]]))

(defn parse-int
  "Given a string, returns an integer."
  [s]
  (. Integer parseInt s))

(def init-pos {:x 0
               :a 0
               :d 0})

(defn update-from-command
  "Given a position and a command, return the new position."
  [pos [cmd v]]
  (let [curr-x (:x pos)
        curr-d (:d pos)]
    (case cmd
      "forward" (assoc-in pos [:x] (+ curr-x v))
      "down" (assoc-in pos [:d] (+ curr-d v))
      "up" (assoc-in pos [:d] (- curr-d v)))))

(defn position-product
  [{x :x d :d}]
  (* x d))

(defn main
  [_]
  (let [commands (->> (slurp "res/day2/input.txt")
                      (split-lines)
                      (map #(split % #" "))
                      (map (fn [[cmd v]] [cmd (parse-int v)])))]
        (print (position-product (reduce update-from-command init-pos commands)))))
