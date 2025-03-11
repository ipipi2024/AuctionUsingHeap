public class Heap {
    Bid[] heap = new Bid[100];
    int numberOfBids = 0;

    //hashmap to access bid by using character as key
    //this hash map stores references or pointers of at most 100 DoublyLinkedList which contains the bids
    HashMap map = new HashMap(100);

    public static class Bid {
        String name;
        double price;
        int quantity;
        int time;

        Bid(String name, double price, int time, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
            this.time = time;
        }
    }

    public int size() {
        return numberOfBids;
    }

    public boolean isEmpty() {
        return numberOfBids == 0;
    }

    public boolean isFull() {
        return numberOfBids == heap.length;
    }

    // method to add bid to heap
    public void insert(Bid bid) {
        // Check if the bidder already exists
        Integer existingIndex = map.getKey(bid.name);
        if (existingIndex != null) {
            // If bidder exists, treat this as an update
            Bid existingBid = heap[existingIndex];
            double oldPrice = existingBid.price;
            int oldTime = existingBid.time;
            
            // Update existing bid values including time
            existingBid.price = bid.price;
            existingBid.quantity = bid.quantity;
            existingBid.time = bid.time;
            
            // Reheapify as needed based on price and time changes
            if (bid.price > oldPrice) {
                swapUp(existingIndex);
            } else if (bid.price < oldPrice) {
                swapDown(existingIndex);
            } else if (bid.time < oldTime) {
                // If price is same but time is earlier, may need to move up
                swapUp(existingIndex);
            } else if (bid.time > oldTime) {
                // If price is same but time is later, may need to move down
                swapDown(existingIndex);
            }
            return;
        }
        
        // check if its not full
        if (numberOfBids == heap.length) {
            System.out.println("Maximum Bid capacity is reached, please wait and try again");
            return;
        }
        
        // Add new bid
        heap[numberOfBids] = bid;
        //insert the bid into hash map
        map.addEntry(numberOfBids, heap[numberOfBids].name);
        swapUp(numberOfBids);
        numberOfBids++;
    }

    public void swapUp(int index) {
        // check if reached the root
        if (index == 0) {
            return;
        }

        int parentIndex = (index - 1) / 2;
        if (heap[parentIndex].price < heap[index].price) {
            //update key which stores the index to bid
            map.updateKey(parentIndex, index, heap[parentIndex].name, heap[index].name);
            swap(parentIndex, index);
            swapUp(parentIndex);
        } else if (heap[parentIndex].price == heap[index].price &&
                heap[parentIndex].time > heap[index].time) {
            map.updateKey(parentIndex, index, heap[parentIndex].name, heap[index].name);
            swap(parentIndex, index);
            swapUp(parentIndex);
        }
    }

    // method to remove max
    public Bid removeMax() {
        if (isEmpty()) {
            System.out.println("Heap is empty, cannot remove!");
            return null;
        }

        Bid max = heap[0];
        // If there's only one element, just remove it
        if (this.size() == 1) {
            heap[0] = null;
            numberOfBids--;
            return max;
        }
        
        // replace root with the last element or bid
        heap[0] = heap[numberOfBids - 1];
        
        // Update the mapping for the moved bid
        map.addEntry(0, heap[0].name);

        // clear last element
        heap[numberOfBids - 1] = null;

        // decrement size
        numberOfBids--;

        // swap down method to heapify
        swapDown(0);

        return max;
    }

    // method to swap down
    public void swapDown(int index) {
        int largest = index;
        int leftChildIndex = 2 * index + 1;
        int rightChildIndex = 2 * index + 2;
        int heapSize = this.size();

        // Check if left child exists and has higher priority
        if (leftChildIndex < heapSize) {
            if (heap[leftChildIndex].price > heap[largest].price) {
                largest = leftChildIndex;
            } else if (heap[leftChildIndex].price == heap[largest].price
                    && heap[leftChildIndex].time < heap[largest].time) {
                largest = leftChildIndex;
            }
        }

        // Check if right child exists and has higher priority
        if (rightChildIndex < heapSize) {
            if (heap[rightChildIndex].price > heap[largest].price) {
                largest = rightChildIndex;
            } else if (heap[rightChildIndex].price == heap[largest].price
                    && heap[rightChildIndex].time < heap[largest].time) {
                largest = rightChildIndex;
            }
        }

        // If largest is not the current index, swap and continue down
        if (largest != index) {
            map.updateKey(index, largest, heap[index].name, heap[largest].name);
            swap(index, largest);
            swapDown(largest);
        }
    }

    //method to get max without removing
    public Bid getMax() {
        if (this.size() == 0) {
            System.out.println("No bids available");
            return null;
        }
        return heap[0];
    }

    /**
     * Helper method to swap two elements in the heap
     */
    private void swap(int i, int j) {
        Bid temp = heap[j];
        heap[j] = heap[i];
        heap[i] = temp;
    }
    
    /**
     * Method to find index of a bid by customer name
     * Returns -1 if not found
     */
    public int findBidIndex(String name) {
        Integer index = map.getKey(name);
        return index != null ? index : -1;
    }
}