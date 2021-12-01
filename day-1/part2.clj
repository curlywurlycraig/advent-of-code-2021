(ns main
  (:require [clojure.core :refer [partition]]
            [clojure.string :refer [split-lines]]))

(defn parse-int
  "Given a string, returns an integer."
  [s]
  (. Integer parseInt s))
  

(defn -main
  []
  (print (let [counts (->> (slurp "./input.txt")
                           (split-lines)
                           (map parse-int)
                           (partition 3 1)
                           (map (partial apply +)))]
           (count
            (filter (partial apply <)
                    (partition 2 1 counts))))))

  (-main)
