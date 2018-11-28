package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import exceptions.InvalidProductException;
import model.Coin;

public class VendingMachine {
	
	private int nickelCount;
	private int dimeCount;
	private int quarterCount;
	private Map<String, Double> products;
	private Map<String, Integer> productCount;
	private String display;
	private double currentInsertedAmount;
	private boolean hasTemporaryDisplay;
	private List<Coin> coinReturn;

	/**
	 * Machine is created with 50 nickels, dimes and quarters inside of it
	 */
	public VendingMachine() {
		addXCoins(Coin.NICKEL, 50);
		addXCoins(Coin.DIME, 50);
		addXCoins(Coin.QUARTER, 50);
		products = initializeProducts();
		display = "INSERT COIN";
		currentInsertedAmount = 0;
		hasTemporaryDisplay = false;
		coinReturn = new ArrayList<>();
	}
	
	public int getNickels() {
		return nickelCount;
	}
	
	public int getDimes() {
		return dimeCount;
	}

	public int getQuarters() {
		return quarterCount;
	}

	public void insert(Coin coin) {
		switch(coin) {
		case QUARTER:
			quarterCount++;
			currentInsertedAmount += .25;
			break;
		case NICKEL:
			nickelCount++;
			currentInsertedAmount += .05;
			break;
		case DIME:
			dimeCount++;
			currentInsertedAmount += .10;
			break;
		default:
			coinReturn.add(coin);
			System.out.println("Please insert a Nickel, Dime, or Quarter");
		}
		hasTemporaryDisplay = false;
	}

	public Map<String, Double> getProductMap() {
		return products;
	}

	private Map<String, Double> initializeProducts() {
		products = new HashMap<>();
		productCount = new HashMap<>();
		addXProducts("Cola", 12);
		addXProducts("Chips", 12);
		addXProducts("Candy", 12);
		return products;
	}

	public void addXProducts(String product, int amount) {
		int beforeValue = 0;
		if(productCount.containsKey(product))
			beforeValue +=productCount.get(product);
		switch(product) {
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
		productCount.put(product, amount+beforeValue);
	}

	public String select(String product) throws InvalidProductException {
		if(!products.containsKey(product))
			throw new InvalidProductException("The vending machine does not contain "+product);
		String returner = null;
		currentInsertedAmount = Double.parseDouble(String.format("%.2f", currentInsertedAmount));
		if(!hasProduct(product)) {
			display = "SOLD OUT";
		}
		else if(hasEnoughMoneyForProduct(product) && canMakeChangeWithProduct(product)) {
			currentInsertedAmount -= getSelectedProductPrice(product);
			currentInsertedAmount = makeChangeForCoinReturn(currentInsertedAmount);
			display = "THANK YOU";
			int amount = productCount.get(product);
			amount--;
			productCount.put(product, amount);
			returner = product;
		} else {
			display = "PRICE $"+String.format("%.2f", getSelectedProductPrice(product));
		}
		hasTemporaryDisplay = true;
		return returner;
	}

	private boolean hasProduct(String product) {
		return productCount.get(product) > 0;
	}

	private double makeChangeForCoinReturn(Double amountToMake) {
		double changeToMake = Double.parseDouble(String.format("%.2f", amountToMake));
		changeToMake = makeChangeWithQuarters(changeToMake);
		changeToMake = makeChangeWithDimes(changeToMake);
		changeToMake = makeChangeWithNickels(changeToMake);
		//Not returning pennies as they can't be inserted
		return changeToMake;
	}

	private double makeChangeWithNickels(double changeToMake) {
		double sum = changeToMake;
		while(sum - coinToPrice(Coin.NICKEL) >= 0) {
			sum -= coinToPrice(Coin.NICKEL);
			coinReturn.add(Coin.NICKEL);
		}
		return Double.parseDouble(String.format("%.2f", sum));
	}

	private double makeChangeWithDimes(double changeToMake) {
		double sum = changeToMake;
		while(sum - coinToPrice(Coin.DIME) >= 0) {
			sum -= coinToPrice(Coin.DIME);
			coinReturn.add(Coin.DIME);
		}
		return Double.parseDouble(String.format("%.2f", sum));
	}

	private double makeChangeWithQuarters(double changeToMake) {
		double sum = changeToMake;
		while(sum - coinToPrice(Coin.QUARTER) >= 0) {
			sum -= coinToPrice(Coin.QUARTER);
			coinReturn.add(Coin.QUARTER);
		}
		return Double.parseDouble(String.format("%.2f", sum));
	}

	private boolean hasEnoughMoneyForProduct(String product) {
		return currentInsertedAmount >= getSelectedProductPrice(product);
	}
	
	private Double getSelectedProductPrice(String product) {
		return products.get(product);
	}

	public String getDisplay() {
		if(hasTemporaryDisplay) {
			String tempDisplay = display;
			hasTemporaryDisplay = false;
			setNormalDisplay();
			return tempDisplay;
		}
		setNormalDisplay();
		return display;
	}

	private void setNormalDisplay() {
		if(!canMakeChangeForAllItems()) {
			display = "EXACT CHANGE ONLY";
		}
		else if(currentInsertedAmount == 0)
			display = "INSERT COIN";
		else
			display = "$"+String.format("%.2f", currentInsertedAmount);
	}

	private boolean canMakeChangeForAllItems() {
		Iterator<String> iter = products.keySet().iterator();
		while(iter.hasNext()) {
			String productName = iter.next();
			if(!canMakeChangeWithProduct(productName))
				return false;
		}
		return true;
	}

	private boolean canMakeChangeWithProduct(String productName) {
		double value = Double.parseDouble(String.format("%.2f", products.get(productName)));
		value = canMakeChangeWithQuarters(value);
		value = canMakeChangeWithDimes(value);
		value = canMakeChangeWithNickels(value);
		return  Double.parseDouble(String.format("%.2f", value)) == 0;
	}

	private double canMakeChangeWithNickels(double value) {
		int temp = nickelCount;
		while(temp > 0 && (value - coinToPrice(Coin.NICKEL) >= 0)) {
			value -= coinToPrice(Coin.NICKEL);
			temp--;
		}
		return value;
	}

	private double canMakeChangeWithDimes(double value) {
		int temp = dimeCount;
		while(temp > 0 && (value - coinToPrice(Coin.DIME) >= 0)) {
			value -= coinToPrice(Coin.DIME);
			temp--;
		}
		return value;
	}

	private double canMakeChangeWithQuarters(double value) {
		int temp = quarterCount;
		while(temp > 0 && (value - coinToPrice(Coin.QUARTER) >= 0)) {
			value -= coinToPrice(Coin.QUARTER);
			temp--;
		}
		return value;
	}

	public double coinListToPrice(List<Coin> coins) {
		double sum = 0;
		for(Coin c : coins) {
			sum += coinToPrice(c);
		}
		return sum;
	}

	private double coinToPrice(Coin c) {
		switch(c) {
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

	public List<Coin> emptyCoinReturn() {
		List<Coin> temp = new ArrayList<>();
		for(Coin c : coinReturn)
			temp.add(c);
		coinReturn.clear();
		return temp;
	}

	public void returnCoins() {
		currentInsertedAmount = makeChangeForCoinReturn(currentInsertedAmount);
	}

	/**
	 * Empties to coins in the machine (except for any coins in a current transaction)
	 */
	public void clearCoinInventory() {
		nickelCount = 0;
		dimeCount = 0;
		quarterCount = 0;
		
		//currently converts all inserted amount to nickels as there is currently not a memory for exactly what coins are inserted
		double temp = currentInsertedAmount; 
		double nickelAmount = coinToPrice(Coin.NICKEL);
		while(temp - nickelAmount >= 0) {
			nickelCount += nickelAmount;
			temp -= nickelAmount;
		}
	}

	/**
	 * Used for refilling the machine
	 * @param coin The type of coin to add
	 * @param amount The amount of coins to add of the given type
	 */
	public void addXCoins(Coin coin, int amount) {
		switch(coin) {
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

	public void clearProductInventory() {
		Iterator<String> iter = productCount.keySet().iterator();
		while(iter.hasNext()) {
			String key = iter.next();
			productCount.put(key, 0);
		}
	}
	
}
