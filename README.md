# knapsack

A Clojure solution to the [knapsack problem](http://en.wikipedia.org/wiki/Knapsack_problem)

## Approach

* Bigger items will be placed first in the hope that smaller items will add flexibility to fill the final gaps (treating the final part of the problem as a non-0/1 knapsack if there are enough small items).
* The overall sum of values for the items could be less of the sum of the capacities of the knapsacks, when that happens all items could potentially fit given there is at least a combination of them that fits in the kanpsack
