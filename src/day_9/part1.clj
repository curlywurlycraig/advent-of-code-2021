(ns day-9.part1
  (:require [clojure.string :refer [split-lines split]]))

(def heightmap (->> (slurp "res/day9/input.txt")
                    split-lines
                    (map (fn [line]
                           (map #(Integer/parseInt %) (split line #""))))))

(defn get-in-hm
  [hm x y]
  (nth (nth hm y nil) x nil))

(defn all-adj
  [hm x y]
  (remove nil?
          (list
           (get-in-hm hm x (dec y))
           (get-in-hm hm x (inc y))
           (get-in-hm hm (dec x) y)
           (get-in-hm hm (inc x) y)
           (get-in-hm hm (inc x) (inc y))
           (get-in-hm hm (inc x) (dec y))
           (get-in-hm hm (dec x) (inc y))
           (get-in-hm hm (dec x) (dec y)))))

(defn lower-than-all?
  [x ns]
  (every? #(< x %) ns))

(defn find-lowpoints
  [hm]
  (flatten (map-indexed
            (fn [y r]
              (keep-indexed
               (fn [x v]
                 (if (every? #(< v %) (all-adj heightmap x y))
                   v
                   nil))
               r))
            heightmap)))

(print (apply + (map inc (find-lowpoints heightmap))))
