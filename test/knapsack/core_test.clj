(ns knapsack.core-test
  (:use [midje.sweet])
  (:require [knapsack.core :as k]))

(facts "collection expansion"
       (fact "fill with nils"
             (k/resize [1 2 3] 5) => [1 2 3 nil nil])
       (fact "nothing to resize"
             (k/resize-all [1 2] [4 5]) => [[1 2] [4 5]])
       (fact "fill all with nils upto the biggest size"
             (k/resize-all [4 1 2] [1] [2]) => [[4 1 2] [1 nil nil] [2 nil nil]]))

(facts "interleave all"
       (fact "just the same as interleave"
             (k/interleave-all [1 2] [3 4]) => [1 3 2 4])
       (fact "but returning the rest as well"
             (k/interleave-all [1 2] [3 4 5]) => [1 3 2 4 nil 5])
       (fact "other direction included"
             (k/interleave-all [1 2 8] [3 4]) => [1 3 2 4 8 nil])
       (fact "but even the middle"
             (k/interleave-all [1 2 3] [4 5] [6 7 8]) => [1 4 6 2 5 7 3 nil 8]))

(facts "packing without overflow"
       (fact "bigger items first"
             (k/pack [5 4] [2 2 1 1]) => [[2 1] [2 1]])
       (fact "more interesting example"
             (k/pack [10 9 8] [2 3 1 1 4 2 1]) => [[4 2 1] [3 1] [2 1]]))
