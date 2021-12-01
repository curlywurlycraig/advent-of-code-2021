(print (count (filter (partial apply <) (clojure.core/partition 2 1 (map #(. Integer parseInt %) (clojure.string/split-lines (slurp "./input.txt")))))))
