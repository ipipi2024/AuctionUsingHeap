public class SinglyLinkedList {
    Node head; //store the pointer of head
    Node tail; //store the pointer of tail
    int size;

    //singlyLinkedList constructure
    public SinglyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }


    //node class
    public static class Node {
        //instance variable
        int key; //store data
        Node next; //stores pointer to next node
        String value;

        //node constructure
        Node(int key, String value,  Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    //method to add node at head
    public void addAtHead(int key, String value) {
        //create a new Node
        Node newNode = new Node(key, value, null);

        if (head == null) {
            //point tail to new Node
            tail = newNode;
        } else {
            //point next of newNode to head
            newNode.next = head;
        }
        //point head to newNode
        head = newNode;
        size++;
    }

    //method to add at tail
    public void addAtTail(int key, String value) {
        //create a new Node
        Node newNode = new Node(key, value, null);

        if (head == null) {
            //point tail to new Node
            head = newNode;
        } else {
            //point next of tail to newNode
            tail.next = newNode;
        }
        //point head to newNode
        tail = newNode;
        size++;
    }

    //method to remove first element
    public void removeFirst() {
        // check if empty 
        if(size == 0) {
            System.out.println("Cannot remove. Linked List is Empty");
            return;
        }
        
        // handle single node case
        if(size == 1) {
            head = null;
            tail = null;
        } else {
            // just move head to next node
            head = head.next;
        }
        
        // decrement size
        size--;
    }

    //method to remove last element
    public void removeLast() {
        //check if size is empty
        if(size == 0) {
            System.out.println("Cannot remove. Linked List is Empty");
            return;
        }

        if (size == 1) {
            tail = null;
            head = null;
        }else{
            Node currentNode = head;
            while(currentNode.next.next != null) {
                currentNode = currentNode.next;
            }
            
            //current node is now prev node to tail
            //set tail to be the current node since it is 
            //the prev of tail
            tail = currentNode;
            tail.next = null;

        }

        size--;
    }

} 