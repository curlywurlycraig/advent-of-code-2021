(ns main
  (:require [clojure.core :refer [partition]]
            [clojure.string :refer [split-lines]]))

(defn parse-int
  "Given a string, returns an integer."
  [s]
  (. Integer parseInt s))
  

(defn -main
  []
  (print (let [file-raw (slurp "./input.txt")
               file-vals (map parse-int (split-lines file-raw))
               triples (partition 3 1 file-vals)
               counts (map (partial apply +) triples)]
           (count
            (filter (partial apply <)
                    (partition 2 1 counts))))))

(-main)
