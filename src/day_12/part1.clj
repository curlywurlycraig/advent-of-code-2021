(ns day-12.part1
  (:require [clojure.string :refer [split-lines split lower-case]]))

(def edges (->> (slurp "res/day12/input.txt")
                split-lines
                (map #(split % #"-"))
                (reduce (fn [edges [from to]]
                          (-> edges
                              (update from #(conj % to))
                              (update to #(conj % from))))
                        {})))

(defn lower-case?
  [c]
  (= c (lower-case c)))

(defn small-cave-twice?
  [r]
  (not (apply distinct? (filter lower-case? r))))

(def routes
  (loop [rs [["start"]]
         complete '()]
    (let [r (peek rs)
          l (last r)]
      (cond
        (nil? r)    complete
        (= "end" l) (recur (pop rs) (conj complete r))
        :else       (recur (vec (concat
                                 (pop rs)
                                 (remove small-cave-twice?
                                         (map (partial conj r) (get edges l)))))
                           complete)))))

(println (count routes))
