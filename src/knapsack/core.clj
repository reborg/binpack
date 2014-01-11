(ns knapsack.core)

(defn resize [coll n]
  "Grow coll up to n items using nil fillers"
  (concat coll (take (- n (count coll)) (repeat nil))))

(defn unconstrained-packing [n items]
  "Packs items into n containers, bigger items first, without
  limits to the size of packed items in each container."
  (loop [xs (reverse (sort items)) containers (repeat n (list))]
    (if (= 0 (count xs))
      (->> containers (map (partial remove nil?)))
      (let [[allocable others] (split-at (count containers) xs)]
        (recur others (map #(conj %2 %1) (resize allocable (count containers)) containers))))))
