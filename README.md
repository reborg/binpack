# knapsack

A Clojure solution to the [knapsack problem](http://en.wikipedia.org/wiki/Knapsack_problem)

## Approach

* The overall sum of values for the items could be less of the sum of the capacities of the knapsacks, when that happens all items could potentially fit given there is at least a combination of them that fits in the kanpsack

## Todo

* Parsing of list of items and their size expressed in some form of positive integer
* Parsing of containers and their sizes
* Mechanism for re-submission of same configuration, measuring amount of waste each iteration and selecting best
