(ns binpack.fit-test
  (:use [midje.sweet])
  (:require [binpack.fit :as f]))

(facts "population generation"
       (fact "return the two sort orders and a default of 8 randoms"
             (count (f/populations (shuffle (range 10)))) => 10
             (first (f/populations (shuffle (range 10)))) => (reverse (range 10))
             (second (f/populations (shuffle (range 10)))) => (range 10)
             (last (f/populations (shuffle (range 10)))) =not=> (range 10)
             (last (f/populations (shuffle (range 10)))) =not=> (reverse (range 10)))
       (fact "gives all six permutations of a three element set"
             (count (into #{} (f/populations '(1 2 3) 27))) => 6)
       (fact "can specify the amount of random ones I want"
             (count (f/populations (shuffle (range 10)) 0)) => 2
             (first (f/populations (shuffle (range 10)) 0)) => (reverse (range 10))
             (second (f/populations (shuffle (range 10)) 0)) => (range 10)))

(facts "measure waste"
       (fact "perfect fit is zero waste"
             (f/waste [[1 2 3] [4 5]] [6 9]) => 0)
       (fact "space available on the right"
             (f/waste [[1 2 3] [4 4]] [6 9]) => 1)
       (fact "negative waste means overflow"
             (f/waste [[1 2 3] [4 9]] [6 9]) => -4))
