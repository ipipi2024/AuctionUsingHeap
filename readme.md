This program implements a bidding system using a max-heap data structure. The time complexity of the key operations is as follows:

insert() - O(log n):
When adding a new bid, we may need to restore the heap property by moving the element upward in the tree. Since a binary heap has a height of log(n), where n is the number of elements, the maximum number of comparisons and swaps required is proportional to log(n).

removeMax() - O(log n):
When removing the highest bid (at the root), we replace it with the last element and then must restore the heap property by moving this element downward. Similar to insertion, this operation is bounded by the height of the tree, resulting in a log(n) time complexity.

getMax() - O(1):
Retrieving the highest bid is a constant-time operation because the maximum element is always stored at the root (index 0) of the max-heap, allowing immediate access regardless of heap size.

These efficient time complexities make the heap an ideal data structure for managing bids where quick access to the highest bid and reasonably fast updates are required.