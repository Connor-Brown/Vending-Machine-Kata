package start;

import java.util.Iterator;
import java.util.Scanner;

import exceptions.InvalidProductException;
import model.Coin;
import service.VendingMachine;

public class StartApp {

	private static Scanner scanner = new Scanner(System.in);
	static VendingMachine machine;

	public static void main(String[] args) {
		machine = new VendingMachine();
		while (true) { // use the command 'quit' to exit the process
			printDisplay();
			String input = getUserInput();
			while (input == null) {
				System.out.println("I don't recognize that command.");
				input = getUserInput();
			}
			// if(input == null) //check if user input doesn't match any command
			processInput(input);
		}
	}

	private static void printDisplay() {
		System.out.println("Display:   " + machine.getDisplay());
	}

	private static void processInput(String input) {
		// Input is all lower case when it reaches here
		switch (input) {
		case "quit":
			System.exit(0);
		case "help":
			help();
			break;
		case "display":
			//do nothing as the display will be printed anyway in the main while loop
			break;
		case "empty coin return area":
			emptyCoinReturnArea();
			break;
		case "return coins":
			returnCoins();
			break;
		case "clear coins":
			clearCoins();
			break;
		case "clear products":
			clearProducts();
			break;
		case "get products":
			getProducts();
			break;
		case "get coins":
			getCoins();
			break;
		case "refresh coins":
			refreshCoins();
			break;
		case "refresh products":
			refreshProducts();
			break;
		default:
			doSpecialCommands(input);
		}
	}

	private static void help() {
		// TODO list all commands
		System.out.println("ALL COMMANDS HERE");
	}

	private static void refreshProducts() {
		machine.clearProductInventory();
		machine.addXProducts("Cola", 12);
		machine.addXProducts("Chips", 12);
		machine.addXProducts("Candy", 12);
		getProducts();
	}

	private static void refreshCoins() {
		machine.clearCoinInventory();
		machine.addXCoins(Coin.NICKEL, 50);
		machine.addXCoins(Coin.DIME, 50);
		machine.addXCoins(Coin.QUARTER, 50);
		getCoins();
	}

	private static void getCoins() {
		System.out.println("Coins in machine: \n" + "     Nickels - " + machine.getNickels() + "\n" + "     Dimes - "
				+ machine.getDimes() + "\n" + "     Quarters - " + machine.getQuarters());
	}

	private static void getProducts() {
		System.out.println("Products in machine + price + amount in machine: ");
		Iterator<String> iter = machine.getProductMap().keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			System.out.println("     " + key + " - " + machine.getProductMap().get(key) + " - "
					+ machine.getProductCount().get(key));
		}
	}

	private static void clearProducts() {
		machine.clearProductInventory();
		getProducts();
	}

	private static void clearCoins() {
		machine.clearCoinInventory();
		getCoins();
	}

	private static void returnCoins() {
		machine.returnCoins();
		System.out.println("All coins inserted by the user are now in the coin return area");
	}

	private static void emptyCoinReturnArea() {
		System.out.println(
				"Received the following coins from the coin return area:  \n     " + machine.emptyCoinReturn());
	}

	private static void doSpecialCommands(String input) {
		String item = input.substring(7, input.length());
		if (input.contains("insert")) {
			// do insert coin
			switch (item) {
			case "quarter":
				machine.insert(Coin.QUARTER);
				break;
			case "dime":
				machine.insert(Coin.DIME);
				break;
			case "nickel":
				machine.insert(Coin.NICKEL);
				break;
			case "penny":
				machine.insert(Coin.PENNY);
				break;
			}
		} else {
			// do select product
			try {
				String product = item.charAt(0)+"";
				product = product.toUpperCase();
				product += item.substring(1);
				System.out.println(product);
				System.out.println("Selecting: " + machine.select(product));
			} catch (InvalidProductException e) {
				e.printStackTrace();
			}
		}
	}

	private static String getUserInput() {
		System.out.print(">");
		String input = scanner.nextLine();
		if (input.equalsIgnoreCase("empty coin return area") || input.equalsIgnoreCase("display")
				|| input.equalsIgnoreCase("return coins") || input.equalsIgnoreCase("clear coins")
				|| input.equalsIgnoreCase("clear products") || input.equalsIgnoreCase("get products")
				|| input.equalsIgnoreCase("quit") || input.equalsIgnoreCase("get coins")
				|| input.equalsIgnoreCase("help") || input.equalsIgnoreCase("refresh coins")
				|| input.equalsIgnoreCase("refresh products"))
			return input.toLowerCase();
		else if (input.matches("[I|i][N|n][S|s][E|e][R|r][T|t] [A-Za-z]+")) {
			Scanner scan = new Scanner(input);
			scan.next();
			String coin = scan.next();
			scan.close();
			if (validCoin(coin))
				return "insert " + coin.toLowerCase();
			return null;
		} else if (input.matches("[S|s][E|e][L|l][E|e][C|c][T|t] [A-Za-z]+")) {
			Scanner scan = new Scanner(input);
			scan.next();
			String product = scan.next();
			scan.close();
			if (validproduct(product))
				return "select " + product.toLowerCase();
			return null;
		} else
			return null;
	}

	private static boolean validproduct(String product) {
		switch (product.toLowerCase()) {
		case "cola":
			return true;
		case "chips":
			return true;
		case "candy":
			return true;
		}
		return false;
	}

	private static boolean validCoin(String coin) {
		switch (coin.toLowerCase()) {
		case "quarter":
			return true;
		case "dime":
			return true;
		case "nickel":
			return true;
		case "penny":
			return true;
		}
		return false;
	}

}
