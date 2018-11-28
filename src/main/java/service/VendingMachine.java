package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exceptions.InvalidProductException;
import model.Coin;

public class VendingMachine {
	
	private int nickelCount;
	private int dimeCount;
	private int quarterCount;
	private Map<String, Double> products;
	private String display;
	private double currentInsertedAmount;
	private boolean hasTemporaryDisplay;
	private List<Coin> coinReturn;

	public VendingMachine() {
		nickelCount = 0;
		dimeCount = 0;
		quarterCount = 0;
		products = initializeProductMap();
		display = "INSERT COIN";
		currentInsertedAmount = 0;
		hasTemporaryDisplay = false;
		coinReturn = new ArrayList<>();
	}
	
	public int getNickels() {
		return nickelCount;
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
		display = "$"+String.format("%.2f", currentInsertedAmount);
	}

	public int getDimes() {
		return dimeCount;
	}

	public int getQuarters() {
		return quarterCount;
	}

	public Map<String, Double> getProductMap() {
		return products;
	}

	private Map<String, Double> initializeProductMap() {
		products = new HashMap<>();
		products.put("Cola", new Double(1));
		products.put("Chips", new Double(.5));
		products.put("Candy", new Double(.65));
		return products;
	}

	public String select(String product) throws InvalidProductException {
		if(!products.containsKey(product))
			throw new InvalidProductException("The vending machine does not contain "+product);
		String returner = null;
		currentInsertedAmount = Double.parseDouble(String.format("%.2f", currentInsertedAmount));
		if(hasEnoughMoneyForProduct(product)) {
			currentInsertedAmount -= getSelectedProductPrice(product);
			currentInsertedAmount = makeChangeForCoinReturn(currentInsertedAmount);
			display = "THANK YOU";
			returner = product;
		} else {
			display = "PRICE $"+String.format("%.2f", getSelectedProductPrice(product));
		}
		hasTemporaryDisplay = true;
		return returner;
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
		if(currentInsertedAmount == 0)
			display = "INSERT COIN";
		else
			display = "$"+String.format("%.2f", currentInsertedAmount);
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
	
}
