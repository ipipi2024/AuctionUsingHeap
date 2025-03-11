/**
 * Author: Ipule Pipi (Modified based on your original code)
 * Email: ipipi2022@my.fit.edu
 * Course: CSE2010-Algorithms and Data Structures
 * Due Date: 13th March 2025
 * Section: E1
 * 
 * HW4Extra - Auction Using Priority Queue with Bid Update Support
 * 
 * This program extends the HW4 implementation to allow customers to update their bids.
 * 
 * Time Complexity Analysis for UpdateBid:
 * 
 * (a) The HashMap data structure enables finding a customer's bid in O(1) average time 
 *     instead of O(N) because it maps names directly to their positions in the heap.
 *     Without this mapping, we would need to scan the entire heap (O(N)) to find a specific
 *     customer's bid. With the HashMap, the UpdateBid operation time complexity is O(log N)
 *     in worst case, which is needed for reheapifying after an update.
 * 
 * (b) There are different cases for UpdateBid:
 *     1. When the new price is higher than the original: We need to call swapUp to restore
 *        the max-heap property, which takes O(log N) time.
 *     2. When the new price is lower than the original: We need to call swapDown to restore
 *        the max-heap property, which also takes O(log N) time.
 *     3. When the price remains the same but quantity changes: No reheapifying is needed,
 *        as it doesn't affect the heap ordering (O(1) time).
 * 
 * (c) EnterBid can become slower than O(log N) if we need to check if a customer already
 *     exists before inserting a new bid. In the worst case, this might require a full scan
 *     of the existing customer names if hash collisions occur, making it O(N).
 */

 import java.io.File;
 import java.io.FileNotFoundException;
 import java.util.Scanner;
 
 public class HW4Extra {
     private static Heap bidHeap = new Heap();
     private static double minimumAcceptablePrice = 0.0; // Default value
 
     public static void main(String[] args) {
         if (args.length != 1) {
             System.out.println("Usage: java HW4Extra <input_file>");
             return;
         }
 
         String inputFilename = args[0];
 
         try {
             Scanner scanner = new Scanner(new File(inputFilename));
 
             while (scanner.hasNextLine()) {
                 String line = scanner.nextLine();
                 processCommand(line);
             }
 
             scanner.close();
         } catch (FileNotFoundException e) {
             System.out.println("Error: File not found: " + inputFilename);
         }
     }
 
     private static void processCommand(String command) {
         String[] parts = command.split(" ");
         String action = parts[0];
 
         switch (action) {
             case "EnterBid":
                 handleEnterBid(parts);
                 break;
             case "UpdateMinimumAcceptablePrice":
                 handleUpdateMinimumPrice(parts);
                 break;
             case "SellOneItem":
                 handleSellOneItem(parts);
                 break;
             case "DisplayHighestBid":
                 handleDisplayHighestBid(parts);
                 break;
             case "UpdateBid":
                 handleUpdateBid(parts);
                 break;
             default:
                 System.out.println("Unknown command: " + action);
         }
     }
 
     private static void handleEnterBid(String[] parts) {
         // Format: EnterBid time name price quantity
         String timeStr = parts[1];
         String name = parts[2];
         double price = Double.parseDouble(parts[3]);
         int quantity = Integer.parseInt(parts[4]);
 
         // Convert time string to integer (HHMM format)
         int time = Integer.parseInt(timeStr);
 
         // Create new bid and insert into heap
         Heap.Bid bid = new Heap.Bid(name, price, time, quantity);
         bidHeap.insert(bid);
 
         // Echo the command to output
         System.out.println("EnterBid " + timeStr + " " + name + " " + price + " " + quantity);
     }
 
     private static void handleUpdateMinimumPrice(String[] parts) {
         // Format: UpdateMinimumAcceptablePrice time price
         String timeStr = parts[1];
         double newPrice = Double.parseDouble(parts[2]);
 
         minimumAcceptablePrice = newPrice;
 
         // Echo the command to output
         System.out.println("UpdateMinimumAcceptablePrice " + timeStr + " " + newPrice);
     }
 
     private static void handleSellOneItem(String[] parts) {
         // Format: SellOneItem time
         String timeStr = parts[1];
     
         if (bidHeap.isEmpty()) {
             // No bids available
             System.out.println("SellOneItem " + timeStr + " NoBids");
             return;
         }
     
         // Get highest bid without removing it
         Heap.Bid highestBid = bidHeap.getMax();
     
         if (highestBid.price < minimumAcceptablePrice) {
             // Highest bidding price is too low
             System.out.println("SellOneItem " + timeStr + " HighestBiddingPriceIsTooLow");
             return;
         }
     
         // Store bid information for output
         String bidderName = highestBid.name;
         double bidPrice = highestBid.price;
     
         // If this is the last item from this bid, remove it
         if (highestBid.quantity == 1) {
             bidHeap.removeMax();
             //also remove the entry in hashmap
             bidHeap.map.removeEntry(bidderName);
         } else {
             // Otherwise, just decrement the quantity in place
             highestBid.quantity--;
             // No need to reheapify since price and timestamp (the priority keys) remain unchanged
         }
     
         // Output the sale information
         System.out.println("SellOneItem " + timeStr + " " + bidderName + " " + bidPrice);
     }
 
     private static void handleDisplayHighestBid(String[] parts) {
         // Format: DisplayHighestBid time
         String timeStr = parts[1];
 
         if (bidHeap.isEmpty()) {
             // No bids available
             System.out.println("DisplayHighestBid " + timeStr + " NoBids");
             return;
         }
 
         // Get highest bid
         Heap.Bid highestBid = bidHeap.getMax();
 
         // Output highest bid information
         // Format the time to preserve leading zeros
         String bidTimeStr = String.format("%04d", highestBid.time);
         System.out.println("DisplayHighestBid " + timeStr + " " +
                 highestBid.name + " " +
                 bidTimeStr + " " +
                 highestBid.price + " " +
                 highestBid.quantity);
     }
 
     private static void handleUpdateBid(String[] parts) {
         // Format: UpdateBid time name price quantity
         String timeStr = parts[1];
         String name = parts[2];
         double newPrice = Double.parseDouble(parts[3]);
         int newQuantity = Integer.parseInt(parts[4]);
         int newTime = Integer.parseInt(timeStr);
 
         // Echo the command to output only if customer exists
         Integer heapIndex = bidHeap.map.getKey(name);
 
         if (heapIndex == null) {
             // Customer not found - print with error message
             System.out.println("UpdateBid " + timeStr + " " + name + " " + newPrice + " " + newQuantity + " customerNotFound");
             return;
         }
 
         // Echo the command to output if customer exists
         System.out.println("UpdateBid " + timeStr + " " + name + " " + newPrice + " " + newQuantity);
         
         // Get the customer's bid
         Heap.Bid currentBid = bidHeap.heap[heapIndex];
         double oldPrice = currentBid.price;
         int oldTime = currentBid.time;
 
         // Update the price, quantity, and time
         currentBid.price = newPrice;
         currentBid.quantity = newQuantity;
         currentBid.time = newTime;
 
         // Reheapify based on price and time changes
         if (newPrice > oldPrice) {
             // If price increased, possible upward movement
             bidHeap.swapUp(heapIndex);
         } else if (newPrice < oldPrice) {
             // If price decreased, possible downward movement
             bidHeap.swapDown(heapIndex);
         } else if (newTime < oldTime) {
             // If price is the same but time is earlier, possible upward movement
             bidHeap.swapUp(heapIndex);
         } else if (newTime > oldTime) {
             // If price is the same but time is later, possible downward movement
             bidHeap.swapDown(heapIndex);
         }
         // If both price and time remain the same, heap property is maintained
     }
 }