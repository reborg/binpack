(ns knapsack.core)

(defn resize [coll n]
  "Grow coll up to n items using nil fillers"
  (concat coll (take (- n (count coll)) (repeat nil))))

(defn resize-all [& colls]
  "Transform the list of collection colls so that
  all collections are of the same size using a nil-filler."
  (map #(resize % (apply max (map count colls))) colls))

(defn interleave-all [& colls]
  "Like interleave but include leftovers"
  (apply interleave (apply resize-all colls)))

(defn pack [sacks items]
  "Packs items into sacks minimizing wasted space"
  (let [assign (partial partition-all (count sacks))]
    (->> (sort #(compare %2 %1) items) 
         assign 
         (apply interleave-all) 
         assign 
         (map (partial remove nil?)))))
