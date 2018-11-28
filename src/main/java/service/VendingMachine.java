package service;

import exceptions.NotAcceptedCoinException;
import model.Coin;

public class VendingMachine {
	
	private int nickelCount;
	private int dimeCount;
	private int quarterCount;

	public VendingMachine() {
		nickelCount = 0;
		dimeCount = 0;
	}
	
	public int getNickels() {
		return nickelCount;
	}

	public void insert(Coin coin) throws NotAcceptedCoinException {
		switch(coin) {
		case QUARTER:
			quarterCount++;
			break;
		case NICKEL:
			nickelCount++;
			break;
		case DIME:
			dimeCount++;
			break;
		case PENNY:
			throw new NotAcceptedCoinException("Please insert a Nickel, Dime, or Quarter");
		}
	}

	public int getDimes() {
		return dimeCount;
	}

	public int getQuarters() {
		return quarterCount;
	}

}
