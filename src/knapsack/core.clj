(ns knapsack.core)

(defn resize [items n]
  "Grow items up to n items using nil fillers"
  (concat items (take (- n (count items)) (repeat nil))))

(defn exceeded? [containers sizes]
  "True if any of the containers contain more than
  the allowed size."
  (not (every? true? (map #(<= %1 %2) (map (partial reduce +) containers) sizes))))

(defn index-of [item coll]
  "Retrieve the index of the first occurence of item in coll"
  (let [res (count (take-while #(not= item %) coll))]
    (if (= res (count coll)) -1 res)))

(defn add-first [item containers sizes]
  "Attempt to allocate the item to the first available container."
  (let [avail (map #(<= (reduce + item %1) %2) containers sizes)
        i (index-of true avail)]
    (if (neg? i)
      containers
      (assoc (vec containers) i (conj (nth containers i) item)))))

(defn free-alloc [items containers sizes]
  "Allocate items into containers without constraints. Sizes
  are ignored."
  (let [resized (resize items (count containers))]
    (map (partial remove nil?) (map #(conj %2 %1) resized containers))))

(defn size-alloc [items containers sizes]
  "Allocate items into containers until size of the container is reached.
  Will attempt allocating items in other containers if room available."
  (let [free-allocation (free-alloc items containers sizes)]
    (if (exceeded? free-allocation sizes)
      (loop [[item & others] items cs containers]
        (if (nil? item)
          cs
          (recur others (add-first item cs sizes))))
      free-allocation)))

(defn pack [items sizes f]
  "Pack items into (count sizes) containers giving priority to the first items. 
  Invoke strategy f to allocate new items into containers."
  (loop [xs items cs (repeat (count sizes) (list))]
    (if (= 0 (count xs))
      cs
      (let [[x1 x2] (split-at (count cs) xs)]
        (recur x2 (f x1 cs sizes))))))
