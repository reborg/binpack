(ns binpack.core
  (:require [binpack.fit :as fit]))

(defn resize [items n]
  "Grow items up to n items using nil fillers"
  (concat items (take (- n (count items)) (repeat nil))))

(defn exceeded? [containers sizes]
  "True if any of the containers contain more than
  the allowed size, false if they are below nominal size."
  (not (every? true? (map #(<= %1 %2) (map (partial reduce +) containers) sizes))))

(defn index-of [item coll]
  "Retrieve the index of the first occurence of item in coll.
  It uses equality to understand when a matching item was found."
  (let [res (count (take-while #(not= item %) coll))]
    (if (= res (count coll)) -1 res)))

(defn add-first [item containers sizes]
  "Attempt to allocate the item to the first available container.
  Does nothing and returns containers if no match was found."
  (let [avail (map #(<= (reduce + item %1) %2) containers sizes)
        i (index-of true avail)]
    (if (neg? i)
      containers
      (assoc (vec containers) i (conj (nth containers i) item)))))

(defn free-alloc [items containers sizes]
  "Allocation strategy function that sends items into containers without constraints. 
  Sizes argument is present but ignored."
  (let [resized (resize items (count containers))]
    (map (partial remove nil?) (map #(conj %2 %1) resized containers))))

(defn size-alloc [items containers sizes]
  "Allocation strategy function that allocates items into containers respecting their size.
  It attempts free allocation first to see if all items fit at once. If that fails, it attempts
  to allocate to the first container that accepts the item."
  (let [free-allocation (free-alloc items containers sizes)]
    (if (exceeded? free-allocation sizes)
      (loop [[item & others] items cs containers]
        (if (nil? item)
          cs
          (recur others (add-first item cs sizes))))
      free-allocation)))

(defn pack 
  "Pack items into (count sizes) containers giving priority to the first items first.
  Require an allocation strategy function f taking one item, 
  the current containers status and their sizes that is used to allocate 
  a new item into containers. Use size strategy by default."
  ([items sizes]
   (pack items sizes size-alloc))
  ([items sizes f]
   (loop [xs items cs (repeat (count sizes) (list))]
     (if (= 0 (count xs))
       cs
       (let [[x1 x2] (split-at (count cs) xs)]
         (recur x2 (f x1 cs sizes)))))))

(defn pack-attempts 
  ([items sizes]
   "Return multiple packing attempts of different combinations of items in the same
   containers. n Defaults to 10 attempts."
   (map #(pack % sizes) (fit/populations items)))
  ([items sizes n]
   (map #(pack % sizes) (fit/populations items (- n 2)))))

(defn best-fit 
  "Return the best allocation of items in containers that minimize wasted space.
  Runs n iterations before returning the best result (n defaults to 10).
  Filters out allocations with missing items."
  ([items sizes]
   (best-fit items sizes 10))
  ([items sizes n]
   (let [fully-packed (filter #(= (count items) (reduce + (map count %))) (pack-attempts items sizes n))
         fit-table (map #(-> [(fit/waste % sizes) %]) fully-packed)]
     (last (first (sort-by first fit-table))))))
