(ns day-12.part2
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

(defn in?
  [coll v]
  (not (nil? (first (filter #(= v %) coll)))))

(defn small-cave?
  [cave]
  (and (not= "start" cave)
       (not= "end" cave)
       (lower-case? cave)))

(defn reasonable-route?
  "A route is reasonable if small caves are only visited once,
  except one, which may be visited twice.

  Or, the number of unique small caves is at most 1 cave smaller
  than the non-unique amount."
  [r]
  (let [small-only (filter small-cave? r)
        terminal-count (count (filter (partial in? ["start" "end"]) r))]
    (and (< terminal-count 3)
         (< (- (count small-only) (count (distinct small-only)))
            2))))

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
                                 (filter reasonable-route?
                                         (map (partial conj r) (get edges l)))))
                           complete)))))

(println (count routes))
