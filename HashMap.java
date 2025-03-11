public class HashMap {
    //store entry list to avoid collusion
    SinglyLinkedList [] valueToKeyMap;

    HashMap (int size) {
        valueToKeyMap = new SinglyLinkedList[size];
    }


    private int hashString(String str) {
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

    //public getter method to get hashed String
    public int getHasString(String value) {
        return hashString(value);
    }

    //method to add Entry
    public void addEntry(int key, String value) {
        int valueHash = hashString(value);

        if (valueToKeyMap[valueHash] == null) {
            //if no entry exist, add it
            //create a new singly linked list
            SinglyLinkedList newList = new SinglyLinkedList();
            valueToKeyMap[valueHash] = newList;
            newList.addAtTail(key, value);
        }else{
            //add at the end 
           valueToKeyMap[valueHash].addAtTail(key, value);
        }
    }

}