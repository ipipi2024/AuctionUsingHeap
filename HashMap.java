public class HashMap {
    // store entry list to avoid collision
    DoublyLinkedList[] valueToKeyMap;
    
    HashMap(int size) {
        valueToKeyMap = new DoublyLinkedList[size];
    }
    
    public int hashString(String str) {
        if (str == null) {
            System.out.println("Must be a valid string!");
            return -1;
        }
        // Convert to lowercase for case-insensitive hashing
        str = str.toLowerCase();
        // A simple hash function for strings
        int hash = 0;
        for (char c : str.toCharArray()) {
            hash = (hash * 31 + c) % valueToKeyMap.length;
        }
        return hash;
    }
    
    // public getter method to get hashed String
    public int getHashString(String value) {
        return hashString(value);
    }
    
    public void addEntry(int key, String value) {
        int valueHash = hashString(value);
        // If no list exists at this hash position, create a new one
        if (valueToKeyMap[valueHash] == null) {
            DoublyLinkedList newList = new DoublyLinkedList();
            valueToKeyMap[valueHash] = newList;
            newList.addAtTail(key, value);
            return;
        }
        // Check if the value already exists in the list
        DoublyLinkedList.Node current = valueToKeyMap[valueHash].head;
        while (current != null) {
            if (value.equals(current.value)) {
                // Value already exists, update its key instead of adding a duplicate
                current.key = key;
                return;
            }
            current = current.next;
        }
        // Value doesn't exist, add it to the end
        valueToKeyMap[valueHash].addAtTail(key, value);
    }
    
    public void updateKey(int key1, int key2, String name1, String name2) {
        updateNodeKey(name1, key2);
        updateNodeKey(name2, key1);
    }
    
    private void updateNodeKey(String name, int newKey) {
        int hashedValue = hashString(name);
        
        // Check if a list exists at this hash position
        if (valueToKeyMap[hashedValue] == null) {
            System.out.println("Value not found: " + name);
            return;
        }
        
        DoublyLinkedList.Node current = valueToKeyMap[hashedValue].head;
        while (current != null) {
            if (name.equals(current.value)) {
                current.key = newKey;
                return; // Assuming each name appears at most once in the list
            }
            current = current.next;
        }
        
        // If we get here, the value wasn't found
        System.out.println("Value not found: " + name);
    }
    
    // Method to remove entry
    public void removeEntry(String value) {
        int hashedValue = hashString(value);
        
        // Check if a list exists at this hash position
        if (valueToKeyMap[hashedValue] == null) {
            System.out.println("Value not found: " + value);
            return;
        }
        
        DoublyLinkedList list = valueToKeyMap[hashedValue];
        DoublyLinkedList.Node current = list.head;
        
        while (current != null) {
            if (value.equals(current.value)) {
                // Found the node, remove it using its key
                list.removeAtKey(current.key);
                
                // Check if the list is now empty
                if (list.size == 0) {
                    valueToKeyMap[hashedValue] = null;
                }
                
                return; // Assuming each value appears at most once
            }
            current = current.next;
        }
        
        // If we get here, the value wasn't found
        System.out.println("Value not found: " + value);
    }
    
    // Method to get key for a given value
    public Integer getKey(String value) {
        int hashedValue = hashString(value);
        
        // Check if a list exists at this hash position
        if (valueToKeyMap[hashedValue] == null) {
            return null; // Value not found
        }
        
        DoublyLinkedList.Node current = valueToKeyMap[hashedValue].head;
        while (current != null) {
            if (value.equals(current.value)) {
                return current.key;
            }
            current = current.next;
        }
        
        return null; // Value not found
    }
    
    // Method to check if a value exists in the map
    public boolean containsValue(String value) {
        int hashedValue = hashString(value);
        
        // Check if a list exists at this hash position
        if (valueToKeyMap[hashedValue] == null) {
            return false;
        }
        
        DoublyLinkedList.Node current = valueToKeyMap[hashedValue].head;
        while (current != null) {
            if (value.equals(current.value)) {
                return true;
            }
            current = current.next;
        }
        
        return false;
    }
    
    // Method to display the contents of the HashMap
    public void display() {
        System.out.println("HashMap Contents:");
        for (int i = 0; i < valueToKeyMap.length; i++) {
            if (valueToKeyMap[i] != null) {
                System.out.print("Bucket " + i + ": ");
                DoublyLinkedList.Node current = valueToKeyMap[i].head;
                while (current != null) {
                    System.out.print("[" + current.key + ":" + current.value + "] -> ");
                    current = current.next;
                }
                System.out.println("null");
            }
        }
    }
}