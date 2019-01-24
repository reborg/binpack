(ns binpack.fit)

(defn populations 
  "Generates a total of n+2 combinations of elements in items,
  ordered ascending, descending and n randomizations."
  ([items]
   (populations items 8))
  ([items n]
   (let [randomized (take n (iterate shuffle items))]
     (merge randomized (sort items) (reverse (sort items))))))

(defn waste [containers sizes]
  "Measure the amount of space remaning in each container
  considering their sizes"
  (reduce + (map #(- %2 (reduce + %1)) containers sizes)))
