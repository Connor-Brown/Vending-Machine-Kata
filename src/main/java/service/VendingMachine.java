package service;

import java.util.HashMap;
import java.util.Map;

import exceptions.InvalidProductException;
import exceptions.NotAcceptedCoinException;
import model.Coin;

public class VendingMachine {
	
	private int nickelCount;
	private int dimeCount;
	private int quarterCount;
	private Map<String, Double> products;
	private String selectedProduct;
	private String display;
	private double currentInsertedAmount;
	private boolean hasTemporaryDisplay;

	public VendingMachine() {
		nickelCount = 0;
		dimeCount = 0;
		quarterCount = 0;
		products = initializeProductMap();
		display = "INSERT COIN";
		currentInsertedAmount = 0;
		hasTemporaryDisplay = false;
	}
	
	public int getNickels() {
		return nickelCount;
	}

	public void insert(Coin coin) throws NotAcceptedCoinException {
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
		case PENNY:
			throw new NotAcceptedCoinException("Please insert a Nickel, Dime, or Quarter");
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

	public void select(String product) throws InvalidProductException {
		if(!products.containsKey(product))
			throw new InvalidProductException("The vending machine does not contain "+product);
		selectedProduct = product;
		if(hasEnoughMoneyForProduct()) {
			display = "THANK YOU";
		} else {
			display = "PRICE $"+String.format("%.2f", products.get(selectedProduct));
		}
		hasTemporaryDisplay = true;
	}

	private boolean hasEnoughMoneyForProduct() {
		return currentInsertedAmount >= products.get(selectedProduct);
	}

	public String getSelectedProductName() {
		return selectedProduct;
	}
	
	public Double getSelectedProductPrice() {
		return products.get(selectedProduct);
	}

	public String getDisplay() {
		if(hasTemporaryDisplay) {
			String tempDisplay = display;
			hasTemporaryDisplay = false;
			setNormalDisplay();
			return tempDisplay;
		}
		return display;
	}

	private void setNormalDisplay() {
		if(currentInsertedAmount == 0)
			display = "INSERT COIN";
		else
			display = "$"+String.format("%.2f", currentInsertedAmount);
	}
	
	

}
