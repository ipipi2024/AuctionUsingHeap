public class DoublyLinkedList {
    Node head; // store the pointer of head
    Node tail; // store the pointer of tail
    int size;
   
    // DoublyLinkedList constructor
    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
    
    // Node class
    public static class Node {
        // instance variables
        int key; // store data
        String value;
        Node next; // stores pointer to next node
        Node prev; // stores pointer to previous node
        
        // Node constructor
        Node(int key, String value, Node next, Node prev) {
            this.key = key;
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
    
    // Method to add node at head
    public void addAtHead(int key, String value) {
        // Create a new Node
        Node newNode = new Node(key, value, null, null);
        
        if (head == null) {
            // If list is empty, point both head and tail to new node
            tail = newNode;
        } else {
            // Point next of newNode to head
            newNode.next = head;
            // Point prev of current head to newNode
            head.prev = newNode;
        }
        
        // Point head to newNode
        head = newNode;
        size++;
    }
    
    // Method to add at tail
    public void addAtTail(int key, String value) {
        // Create a new Node
        Node newNode = new Node(key, value, null, null);
        
        if (head == null) {
            // If list is empty, point both head and tail to new node
            head = newNode;
        } else {
            // Point next of tail to newNode
            tail.next = newNode;
            // Point prev of newNode to tail
            newNode.prev = tail;
        }
        
        // Point tail to newNode
        tail = newNode;
        size++;
    }
    
    // Method to remove first element
    public void removeFirst() {
        // Check if list is empty
        if (size == 0) {
            System.out.println("Cannot remove. Linked List is Empty");
            return;
        }
        
        // Handle single node case
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            // Move head to next node
            head = head.next;
            // Set prev of new head to null
            head.prev = null;
        }
        
        // Decrement size
        size--;
    }
    
    // Method to remove last element
    public void removeLast() {
        // Check if list is empty
        if (size == 0) {
            System.out.println("Cannot remove. Linked List is Empty");
            return;
        }
        
        // Handle single node case
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            // Move tail to previous node
            tail = tail.prev;
            // Set next of new tail to null
            tail.next = null;
        }
        
        // Decrement size
        size--;
    }
    
    // Method to remove node with specific key
    public void removeAtKey(int key) {
        // Check if list is empty
        if (size == 0) {
            System.out.println("Cannot remove. Linked List is Empty");
            return;
        }
        
        // Start from the head
        Node current = head;
        
        // Traverse until we find the node with the key
        while (current != null && current.key != key) {
            current = current.next;
        }
        
        // If key not found
        if (current == null) {
            System.out.println("Key not found in the list");
            return;
        }
        
        // If it's the head node
        if (current == head) {
            removeFirst();
            return;
        }
        
        // If it's the tail node
        if (current == tail) {
            removeLast();
            return;
        }
        
        // It's a middle node
        // Connect previous node to next node
        current.prev.next = current.next;
        // Connect next node to previous node
        current.next.prev = current.prev;
        
        // Decrement size
        size--;
    }
    
    // Method to display the list
    public void display() {
        Node current = head;
        if (size == 0) {
            System.out.println("List is empty");
            return;
        }
        
        System.out.print("List (size=" + size + "): ");
        while (current != null) {
            System.out.print("[" + current.key + ":" + current.value + "] <-> ");
            current = current.next;
        }
        System.out.println("null");
    }
}