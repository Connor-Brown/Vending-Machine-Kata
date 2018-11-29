package machineFeaturesTest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import exceptions.InvalidProductException;
import model.Coin;
import service.VendingMachine;

public class DisplayTest {

	VendingMachine machine;

	@Before
	public void setup() {
		machine = new VendingMachine();
	}

	@Test
	public void checkInitialDisplayShouldReturnInsertCoin() {
		assertEquals("INSERT COIN", machine.getDisplay());
	}

	@Test
	public void checkMultipleInitialDisplayChecksShouldReturnInsertCoin() {
		assertEquals("INSERT COIN", machine.getDisplay());
		assertEquals("INSERT COIN", machine.getDisplay());
		assertEquals("INSERT COIN", machine.getDisplay());
		assertEquals("INSERT COIN", machine.getDisplay());
	}

	@Test
	public void checkDisplayAfterSelectingColaWithNotEnoughMoneyInsertedShouldDisplayPriceOfCola()
			throws InvalidProductException {
		machine.insert(Coin.NICKEL);
		machine.select("Cola");
		assertEquals("PRICE $1.00", machine.getDisplay());
		assertEquals("$0.05", machine.getDisplay()); // after display gets checked again, display changes to INSERT COIN
														// or current amount inserted
	}
	
	@Test
	public void checkDisplayAfterSelectingChipsWithNotEnoughMoneyInsertedShouldDisplayPriceOfChips()
			throws InvalidProductException {
		machine.insert(Coin.NICKEL);
		machine.select("Chips");
		assertEquals("PRICE $0.50", machine.getDisplay());
		assertEquals("$0.05", machine.getDisplay()); // after display gets checked again, display changes to INSERT COIN
														// or current amount inserted
	}
	
	@Test
	public void checkDisplayAfterSelectingCandyWithNotEnoughMoneyInsertedShouldDisplayPriceOfCandy()
			throws InvalidProductException {
		machine.insert(Coin.NICKEL);
		machine.select("Candy");
		assertEquals("PRICE $0.65", machine.getDisplay());
		assertEquals("$0.05", machine.getDisplay()); // after display gets checked again, display changes to INSERT COIN
														// or current amount inserted
	}

	@Test
	public void checkDisplayAfterInsertingNickelShouldReturnFiveCentsInMachine()  {
		machine.insert(Coin.NICKEL);
		assertEquals("$0.05", machine.getDisplay());
	}
	
	@Test
	public void checkDisplayAfterInsertingDimeShouldReturn10CentsInMachine()  {
		machine.insert(Coin.DIME);
		assertEquals("$0.10", machine.getDisplay());
	}
	
	@Test
	public void checkDisplayAfterInsertingQuarterShouldReturnTwentyFiveCentsInMachine()  {
		machine.insert(Coin.QUARTER);
		assertEquals("$0.25", machine.getDisplay());
	}
	
	@Test
	public void checkDisplayAfterInserting75CentsShouldReturn75CentsInMachine()  {
		machine.insert(Coin.NICKEL);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.DIME);
		machine.insert(Coin.DIME);
		machine.insert(Coin.QUARTER);
		assertEquals("$0.75", machine.getDisplay());
	}
	
	@Test
	public void checInsertInvalidCoinInUninsertedMachineShouldShowInsertCoin()  {
		machine.insert(Coin.PENNY);
		assertEquals("INSERT COIN", machine.getDisplay());
	}
	
	@Test
	public void checInsertInvalidCoinInMachineWithOtherInsertedCoinsShouldShowOtherCoinsValue() {
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.DIME);
		machine.insert(Coin.NICKEL);
		machine.insert(Coin.PENNY);
		assertEquals("$0.65", machine.getDisplay());
	}
	
	@Test
	public void checkUnableToMakeChangeForAnythingShouldShowExactChange() {
		//This test also works for Cola, since: if the machine couldn't make change for chips, it definitely can't for cola
		machine.clearCoinInventory();
		assertEquals("EXACT CHANGE ONLY", machine.getDisplay());
	}
	
	@Test
	public void checkUnableToMakeChangeForChipsShouldShowExactChange() {
		//if it can't make change for chips, it can't make change for anything
		machine.clearCoinInventory();
		machine.insert(Coin.NICKEL);
		machine.insert(Coin.NICKEL);
		machine.insert(Coin.NICKEL);
		machine.insert(Coin.NICKEL);
		machine.insert(Coin.NICKEL);
		machine.insert(Coin.NICKEL);
		machine.insert(Coin.NICKEL);
		machine.insert(Coin.NICKEL);
		machine.insert(Coin.NICKEL);
		assertEquals("EXACT CHANGE ONLY", machine.getDisplay());
	}
	
	@Test
	public void checkCanMakeChangeWithChipsButNotColaShouldReturnShowExactChange() {
		machine.clearCoinInventory();
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		assertEquals("EXACT CHANGE ONLY", machine.getDisplay());
	}
	
	@Test
	public void checkCanMakeChangeWithChipsAndColaButNotCandyShouldReturnShowExactChange() {
		machine.clearCoinInventory();
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		assertEquals("EXACT CHANGE ONLY", machine.getDisplay());
	}
	
	@Test
	public void checkCanMakeChangeWithEverythingButJustBarelyShouldReturnInsertCoin() {
		machine.clearCoinInventory();
		machine.addXCoins(Coin.QUARTER, 4);
		machine.addXCoins(Coin.DIME, 1);
		machine.addXCoins(Coin.NICKEL, 1);
		assertEquals("INSERT COIN", machine.getDisplay());
	}
	
	@Test
	public void testSoldOutColaShouldReturnSoldOut() throws InvalidProductException {
		machine.clearProductInventory();
		machine.select("Cola");
		assertEquals("SOLD OUT", machine.getDisplay());
	}
	
	@Test
	public void testSoldOutColaRecheckShouldReturnSoldOutAndInsertCoin() throws InvalidProductException {
		machine.clearProductInventory();
		machine.select("Cola");
		assertEquals("SOLD OUT", machine.getDisplay());
		assertEquals("INSERT COIN", machine.getDisplay());
	}

}
