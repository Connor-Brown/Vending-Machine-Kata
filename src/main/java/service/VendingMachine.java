package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import exceptions.InvalidProductException;
import model.Coin;

public class VendingMachine {

	/**
	 * The amount of nickel in this vending machine
	 */
	private int nickelCount;
	/**
	 * The amount of dimes in this vending machine
	 */
	private int dimeCount;
	/**
	 * The amount of quarters in this vending machine
	 */
	private int quarterCount;
	/**
	 * A Map of all the products this vending machine sells with their prices
	 */
	private Map<String, Double> products;
	/**
	 * A Map of all the products this vending machine sells with the amount
	 * currently in the vending machine
	 */
	private Map<String, Integer> productCount;
	/**
	 * The message that will be displayed on the vending machine. Used to convey
	 * information to the user
	 */
	private String display;
	/**
	 * The amount of money the user has inserted as a list
	 */
	private List<Coin> insertedCoins;
	/**
	 * If true, there is a one time message to display. Used for thank you, sold
	 * out, and Price messages
	 */
	private boolean hasTemporaryDisplay;
	/**
	 * The list of coins currently in the coin return area
	 */
	private List<Coin> coinReturn;
	/**
	 * The amount of each coin the vending machine starts with
	 */
	private int startingCoinCount = 50;
	/**
	 * The amount of each product the vending machine starts with
	 */
	private int startingProductCount = 12;

	/**
	 * Creates a new vending machine. The display starts with the display INSERT
	 * 
	 * COIN All the products and counts are initialized the their starting values
	 * according to startingProductCount and startingCoinCount respectively
	 * 
	 * The list of user inputed coins is set to a new ArrayList
	 * 
	 * The coin return is set to be empty
	 */
	public VendingMachine() {
		addXCoins(Coin.NICKEL, startingCoinCount);
		addXCoins(Coin.DIME, startingCoinCount);
		addXCoins(Coin.QUARTER, startingCoinCount);
		products = initializeProducts();
		display = "INSERT COIN";
		insertedCoins = new ArrayList<>();
		hasTemporaryDisplay = false;
		coinReturn = new ArrayList<>();
	}

	/**
	 * @return The amount of nickels in this machine
	 */
	public int getNickels() {
		return nickelCount;
	}

	/**
	 * @return The amount of dimes in this machine
	 */
	public int getDimes() {
		return dimeCount;
	}

	/**
	 * @return The amount of quarters in this machine
	 */
	public int getQuarters() {
		return quarterCount;
	}

	/**
	 * Simulates a user inputing some type of coin into the vending machine
	 * 
	 * If the coin is a valid coin, we update the amount the vending machine holds,
	 * else we send it to the coin return area
	 * 
	 * @param coin
	 *            The coin the user inserted
	 */
	public void insert(Coin coin) {
		switch (coin) {
		case QUARTER:
			quarterCount++;
			insertedCoins.add(coin);
			break;
		case NICKEL:
			nickelCount++;
			insertedCoins.add(coin);
			break;
		case DIME:
			dimeCount++;
			insertedCoins.add(coin);
			break;
		default:
			coinReturn.add(coin);
		}
		hasTemporaryDisplay = false;
	}

	/**
	 * @return The map of the products in the vending machine with their prices
	 */
	public Map<String, Double> getProductMap() {
		return products;
	}

	/**
	 * @return The map of the products in the vending machine with their amount
	 *         currently in the machine
	 */
	private Map<String, Double> initializeProducts() {
		products = new HashMap<>();
		productCount = new HashMap<>();
		addXProducts("Cola", startingProductCount);
		addXProducts("Chips", startingProductCount);
		addXProducts("Candy", startingProductCount);
		return products;
	}

	/**
	 * Simulates the vendor adding products to the machine
	 * 
	 * @param product
	 *            The type of product being added
	 * @param amount
	 *            The amount of the product type being added
	 */
	public void addXProducts(String product, int amount) {
		int beforeValue = 0;
		if (productCount.containsKey(product))
			beforeValue += productCount.get(product);
		switch (product) {
		case "Cola":
			products.put("Cola", new Double(1));
			break;
		case "Chips":
			products.put("Chips", new Double(.5));
			break;
		case "Candy":
			products.put("Candy", new Double(.65));
			break;
		}
		productCount.put(product, amount + beforeValue);
	}

	/**
	 * Simulates a user pressing a button on the vending machine to select a product
	 * 
	 * The product gets dispensed if: The product exists in the machine The vending
	 * machine can make change with the amount of money inputed by the user The
	 * amount of money inputed by the user is greater or equal to the price of the
	 * product
	 * 
	 * @param product
	 *            The name of the product the user is trying to dispense
	 * @return The name of the product if the machine can dispense the product
	 *         according to the specification above, else null
	 * @throws InvalidProductException
	 *             Thrown if the given product is not in the vending machine
	 */
	public String select(String product) throws InvalidProductException {
		if (!products.containsKey(product))
			throw new InvalidProductException("The vending machine does not contain " + product);
		String returner = null;
		double currentInsertedAmount = Double.parseDouble(String.format("%.2f", coinListToPrice(insertedCoins)));
		if (!hasProduct(product)) {
			display = "SOLD OUT";
		} else if (hasEnoughMoneyForProduct(product) && canMakeChangeWithProduct(product)) {
			currentInsertedAmount -= getSelectedProductPrice(product);
			makeChangeForCoinReturn(currentInsertedAmount);
			insertedCoins.clear();
			display = "THANK YOU";
			int amount = productCount.get(product);
			amount--;
			productCount.put(product, amount);
			returner = product;
		} else {
			display = "PRICE $" + String.format("%.2f", getSelectedProductPrice(product));
		}
		hasTemporaryDisplay = true;
		return returner;
	}

	/**
	 * @param product
	 *            The type of product the machine may contain
	 * @return true if the machine contains the given product type, false otherwise
	 */
	private boolean hasProduct(String product) {
		return productCount.get(product) > 0;
	}

	/**
	 * Takes the given amount of cash to make and puts and equivalent valued list of
	 * coins in the coin return area
	 * 
	 * @param amountToMake
	 *            The amount of money to output to the coin return area
	 * @return Returns the amount of money the machine can't make into change
	 */
	private double makeChangeForCoinReturn(Double amountToMake) {
		double changeToMake = Double.parseDouble(String.format("%.2f", amountToMake));
		changeToMake = makeChangeWithQuarters(changeToMake);
		changeToMake = makeChangeWithDimes(changeToMake);
		changeToMake = makeChangeWithNickels(changeToMake);
		// Not returning pennies as they can't be inserted
		return changeToMake;
	}

	/**
	 * Used in makeChangeForCoinReturn(Double amountToMake) to chain together the
	 * process of adding the different coins to the coin return area
	 * 
	 * This adds an appropriate amount of Nickels to the coin return area
	 * 
	 * @param changeToMake
	 * @return
	 */
	private double makeChangeWithNickels(double changeToMake) {
		double sum = changeToMake;
		while (sum - coinToPrice(Coin.NICKEL) >= 0) {
			sum -= coinToPrice(Coin.NICKEL);
			coinReturn.add(Coin.NICKEL);
		}
		return Double.parseDouble(String.format("%.2f", sum));
	}

	/**
	 * Used in makeChangeForCoinReturn(Double amountToMake) to chain together the
	 * process of adding the different coins to the coin return area
	 * 
	 * This adds an appropriate amount of Dimes to the coin return area
	 * 
	 * @param changeToMake
	 * @return
	 */
	private double makeChangeWithDimes(double changeToMake) {
		double sum = changeToMake;
		while (sum - coinToPrice(Coin.DIME) >= 0) {
			sum -= coinToPrice(Coin.DIME);
			coinReturn.add(Coin.DIME);
		}
		return Double.parseDouble(String.format("%.2f", sum));
	}

	/**
	 * Used in makeChangeForCoinReturn(Double amountToMake) to chain together the
	 * process of adding the different coins to the coin return area
	 * 
	 * This adds an appropriate amount of Quarters to the coin return area
	 * 
	 * @param changeToMake
	 * @return
	 */
	private double makeChangeWithQuarters(double changeToMake) {
		double sum = changeToMake;
		while (sum - coinToPrice(Coin.QUARTER) >= 0) {
			sum -= coinToPrice(Coin.QUARTER);
			coinReturn.add(Coin.QUARTER);
		}
		return Double.parseDouble(String.format("%.2f", sum));
	}

	/**
	 * NOTE: this method does not error check. Use only if you know the given
	 * product is in the machine
	 * 
	 * @param product
	 *            The type of product to check
	 * @return True if the user inputed an amount of money at least the price of the
	 *         given item
	 */
	private boolean hasEnoughMoneyForProduct(String product) {
		return coinListToPrice(insertedCoins) >= getSelectedProductPrice(product);
	}

	/**
	 * @param product
	 *            The type of product to check
	 * @return The price of the given object if it exists in the machine, else null
	 */
	private Double getSelectedProductPrice(String product) {
		return products.get(product);
	}

	/**
	 * @return The message the display is currently showing
	 */
	public String getDisplay() {
		if (hasTemporaryDisplay) {
			String tempDisplay = display;
			hasTemporaryDisplay = false;
			setNormalDisplay();
			return tempDisplay;
		}
		setNormalDisplay();
		return display;
	}

	/**
	 * Sets the display variable to be the standard messages based on the current
	 * state of the machine
	 */
	private void setNormalDisplay() {
		if (!canMakeChangeForAllItems()) {
			display = "EXACT CHANGE ONLY";
		} else if (coinListToPrice(insertedCoins) == 0)
			display = "INSERT COIN";
		else
			display = "$" + String.format("%.2f", coinListToPrice(insertedCoins));
	}

	/**
	 * @return True if the machine is able to make change for any item in the
	 *         machine, false otherwise
	 */
	private boolean canMakeChangeForAllItems() {
		Iterator<String> iter = products.keySet().iterator();
		while (iter.hasNext()) {
			String productName = iter.next();
			if (!canMakeChangeWithProduct(productName))
				return false;
		}
		return true;
	}

	/**
	 * Checks and individual product to see if the machine can make change with
	 * given product
	 * 
	 * @param productName
	 *            The name of the product to check
	 * @return True if the machine can currently make change for the given product,
	 *         false otherwise
	 */
	private boolean canMakeChangeWithProduct(String productName) {
		double value = Double.parseDouble(String.format("%.2f", products.get(productName)));
		value = canMakeChangeWithQuarters(value);
		value = canMakeChangeWithDimes(value);
		value = canMakeChangeWithNickels(value);
		return Double.parseDouble(String.format("%.2f", value)) == 0;
	}

	/**
	 * Used in canMakeChangeWithProduct(String productName) to chain together checks
	 * with each valid type of coin
	 * 
	 * @param value
	 *            The current value of the chain
	 * @return The new value of the chain
	 */
	private double canMakeChangeWithNickels(double value) {
		int temp = nickelCount;
		while (temp > 0 && (value - coinToPrice(Coin.NICKEL) >= 0)) {
			value -= coinToPrice(Coin.NICKEL);
			temp--;
		}
		return value;
	}

	/**
	 * Used in canMakeChangeWithProduct(String productName) to chain together checks
	 * with each valid type of coin
	 * 
	 * @param value
	 *            The current value of the chain
	 * @return The new value of the chain
	 */
	private double canMakeChangeWithDimes(double value) {
		int temp = dimeCount;
		while (temp > 0 && (value - coinToPrice(Coin.DIME) >= 0)) {
			value -= coinToPrice(Coin.DIME);
			temp--;
		}
		return value;
	}

	/**
	 * Used in canMakeChangeWithProduct(String productName) to chain together checks
	 * with each valid type of coin
	 * 
	 * @param value
	 *            The current value of the chain
	 * @return The new value of the chain
	 */
	private double canMakeChangeWithQuarters(double value) {
		int temp = quarterCount;
		while (temp > 0 && (value - coinToPrice(Coin.QUARTER) >= 0)) {
			value -= coinToPrice(Coin.QUARTER);
			temp--;
		}
		return value;
	}

	/**
	 * @param coins
	 *            The list of coins to determine the price of
	 * @return The amount the given list of coins is worth
	 */
	public double coinListToPrice(List<Coin> coins) {
		double sum = 0;
		for (Coin c : coins) {
			sum += coinToPrice(c);
		}
		return Double.parseDouble(String.format("%.2f", sum));
	}

	/**
	 * @param coin
	 *            The coin to check the worth of
	 * @return The amount the given coin is worth
	 */
	private double coinToPrice(Coin coin) {
		switch (coin) {
		case QUARTER:
			return 0.25;
		case DIME:
			return 0.10;
		case NICKEL:
			return 0.05;
		case PENNY:
			return 0.01;
		}
		return 0;
	}

	/**
	 * Empties the coin return area
	 * 
	 * @return The coins that were just in the coin return area
	 */
	public List<Coin> emptyCoinReturn() {
		List<Coin> temp = new ArrayList<>();
		for (Coin c : coinReturn)
			temp.add(c);
		coinReturn.clear();
		return temp;
	}

	/**
	 * Simulates the user pressing the return coins button on the vending machine.
	 * Transfers all user inputed coins into the coin return area
	 */
	public void returnCoins() {
		makeChangeForCoinReturn(coinListToPrice(insertedCoins));
		insertedCoins.clear();
	}

	/**
	 * Empties to coins in the machine (except for any coins in a current
	 * transaction)
	 */
	public void clearCoinInventory() {
		nickelCount = 0;
		dimeCount = 0;
		quarterCount = 0;

		// currently converts all inserted amount to nickels as there is currently not a
		// memory for exactly what coins are inserted
		double temp = coinListToPrice(insertedCoins);
		double nickelAmount = coinToPrice(Coin.NICKEL);
		while (temp - nickelAmount >= 0) {
			nickelCount += nickelAmount;
			temp -= nickelAmount;
		}
	}

	/**
	 * Used for refilling the machine
	 * 
	 * @param coin
	 *            The type of coin to add
	 * @param amount
	 *            The amount of coins to add of the given type
	 */
	public void addXCoins(Coin coin, int amount) {
		switch (coin) {
		case NICKEL:
			nickelCount += amount;
			break;
		case DIME:
			dimeCount += amount;
			break;
		case QUARTER:
			quarterCount += amount;
			break;
		default:
			break;
		}
	}

	/**
	 * Simulates the vendor emptying the vending machine of all the products, but
	 * leaving the references to the products.
	 */
	public void clearProductInventory() {
		Iterator<String> iter = productCount.keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			productCount.put(key, 0);
		}
	}

}