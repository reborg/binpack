# BinPack

binpack is a small Clojure library to solve the [bin packing problem](http://en.wikipedia.org/wiki/Bin_packing_problem). It can be used from your code provided you can translate items sizes and containers sizes into positive integers and invoke binpacking like so (REPL):

    user=> (require '[binpack.core :as pack])
    nil
    user=> (pack/best-fit (range 1 10) [15 15 20])
    [(6 9) (2 5 8) (1 3 4 7)]

## Approach

* Multiple combinations of the initial set of items are ordered ascending, descending (First Fit Decreasing FFD) and a few random additional combinations
* Items are assigned to containers until the first item results in an overflow of one of the container
* Once the first overflow is detected, the item is allocated to the first available container that doesn't generate overflow
* Combinations results are measured in terms of waste generated (empty spaces left) and the one with lower waste that allocates all items wins

## Possible improvements

* The current best-fit strategy attempts a few ordering of the items and select the one with less waste. More sophisticated approaches would involve genetic algorithms to select the best order for input items that minimize waste.
* The provided size-based strategy is very simple, because it assigns the item to the first container that accepts it. A more sophisticated solution would be to allow for an analysis and potential re-arranging of containers based on how many more items are still waiting to be assigned.

## Internals documentation

Tests are pretty comprehensive (binpack was developed test first). You can run them with:

    lein midje

and they give you a detailed overview at what each function is doing. Functions are almost always documented in code.
