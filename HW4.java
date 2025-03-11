/**
 * Author: Ipule Pipi
Email: ipipi2022@my.fit.edu
Course: CSE2010-Algorithms and Data Structures
Due Date: 13th March 2025
Section: E1
 * HW4 - Auction Using Priority Queue  
 * This program uses priority queue to create a auction
 * This priority queue is implemented using a max heap data structure
 * When user makes a bid , the bid is inserted into the priority queue and take O(log n) due to swapping in each layer
 * When seller sells the item to bidders, the bid is removed from priority queue and also takes O(log n) time
 * Accessing the highest bid takes O(1) time complexity since it is already sorted in heap
 * The bid is inserted back into the queue if quantity of item is not met, meaning not zero
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HW4 {
    private static Heap bidHeap = new Heap();
    private static double minimumAcceptablePrice = 0.0; // Default value

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java HW4 <input_file>");
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

        // Get highest bid
        Heap.Bid highestBid = bidHeap.getMax();

        if (highestBid.price < minimumAcceptablePrice) {
            // Highest bidding price is too low
            System.out.println("SellOneItem " + timeStr + " HighestBiddingPriceIsTooLow");
            return;
        }

        // Process the sale
        Heap.Bid soldBid = bidHeap.removeMax();

        // Decrement quantity
        soldBid.quantity--;

        // If quantity is still > 0, reinsert the bid
        if (soldBid.quantity > 0) {
            bidHeap.insert(soldBid);
        }

        // Output the sale information
        System.out.println("SellOneItem " + timeStr + " " + soldBid.name + " " + soldBid.price);
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
}