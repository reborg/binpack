(ns knapsack.core-test
  (:use [midje.sweet])
  (:require [knapsack.core :as k]))

(facts "unconstrained packing of items in containers"
       (fact "even distribution"
             (k/unconstrained-packing 2 [2 2 1 1]) => [[1 2] [1 2]])
       (fact "single container"
             (k/unconstrained-packing 1 (range 10)) => [(range 10)])
       (fact "asymmetric packing"
             (k/unconstrained-packing 3 [2 3 1 1 4 2 1]) => [[1 2 4] [1 3] [1 2]])
       (fact "asymmetric packing"
             (k/unconstrained-packing 3 (range 10)) => [[0 3 6 9] [2 5 8] [1 4 7]]))
