# BinPack

BinPack is a Clojure solution to the [bin packing problem](http://en.wikipedia.org/wiki/Bin_packing_problem)

## Approach

* Items are ordered ascending, descending (First Fit Decreasing FFD) and a few random combinations
* Items are assigned to containers in a round-robin fashion, until the first item results in an overflow of one of the container
* Once the first overflow is detected, the item is allocated to the first available container that doesn't generate overflow
* Combinations results are measured in terms of waste generated (empty spaces) and the one with lower waste that allocates all items wins

## Todo

* Parsing of list of items and their size expressed in some form of positive integer
* Parsing of containers and their sizes
* Mechanism for re-submission of same configuration, measuring amount of waste each iteration and selecting the best fit
