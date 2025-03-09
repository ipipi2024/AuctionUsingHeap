public class Heap {
    Bid[] heap = new Bid[100];
    int numberOfBids = 0;

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
        // check if its not full
        if (numberOfBids == heap.length) {
            System.out.println("Maximum Bid capacity is reached, please wait and try again");
            return;
        }

        heap[numberOfBids] = bid;
        swapUp(numberOfBids);
        numberOfBids++;

        // Fix max hip property by swapping

    }

    public void swapUp(int index) {
        // check if reached the root
        if (index == 0) {
            return;
        }

        int parentIndex = (index - 1) / 2;
        if (heap[parentIndex].price < heap[index].price) {
            swap(parentIndex, index);
            swapUp(parentIndex);
        } else if (heap[parentIndex].price == heap[index].price &&
                heap[parentIndex].time > heap[index].time) {
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

        // clear last element
        heap[numberOfBids - 1] = null;

        // decrement size
        numberOfBids--;

        // swap down method to heapify
        swapDown(0);

        return max;

    }

    // method to swap down down
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
}