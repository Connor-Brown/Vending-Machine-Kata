package machineFeaturesTest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import exceptions.InvalidProductException;
import model.Coin;
import service.VendingMachine;

public class ProductSelectionTest {
	
	VendingMachine machine;
	
	@Before
	public void setup() {
		machine = new VendingMachine();
	}
	
	@Test
	public void testShowProductsShouldReturnMapContainingColaChipsAndCandyWithValues() {
		Map<String, Double> products = machine.getProductMap();
		assertEquals(3, products.size());
		assertEquals(new Double(1), products.get("Cola"));
		assertEquals(new Double(.5), products.get("Chips"));
		assertEquals(new Double(.65), products.get("Candy"));
	}
	
	@Test
	public void testPressColaButtonWithNoMoneyInsertedShouldReturnNull() throws InvalidProductException {
		assertEquals(null, machine.select("Cola"));
	}
	
	@Test
	public void testPressChipsButtonWithNoMoneyInsertedShouldReturnNull() throws InvalidProductException {
		assertEquals(null, machine.select("Chips"));
	}
	
	@Test
	public void testPressCandyButtonWithNoMoneyInsertedShouldReturnNull() throws InvalidProductException {
		assertEquals(null, machine.select("Candy"));
	}
	
	@Test
	public void testPressColaButtonWithNotEnoughMoneyInsertedShouldReturnNull() throws InvalidProductException {
		assertEquals(null, machine.select("Cola"));
	}
	
	@Test
	public void testPressChipsButtonWithNotEnoughMoneyInsertedShouldReturnNull() throws InvalidProductException {
		assertEquals(null, machine.select("Chips"));
	}
	
	@Test
	public void testPressCandyButtonWithNotEnoughMoneyInsertedShouldReturnNull() throws InvalidProductException {
		assertEquals(null, machine.select("Candy"));
	}
	
	@Test
	public void testPressColaButtonWithExactleyEnoughMoneyInsertedShouldReturnCola() throws InvalidProductException {
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		assertEquals("Cola", machine.select("Cola"));
		assertEquals(0, machine.coinListToPrice(machine.emptyCoinReturn()), 0.001);
	}
	
	@Test
	public void testPressChipsButtonWithExactleyEnoughMoneyInsertedShouldReturnChips() throws InvalidProductException {
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		assertEquals("Chips", machine.select("Chips"));
		assertEquals(0, machine.coinListToPrice(machine.emptyCoinReturn()), 0.001);
	}
	
	@Test
	public void testPressCandyButtonWithExactleyEnoughMoneyInsertedShouldReturnCandy() throws InvalidProductException {
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.DIME);
		machine.insert(Coin.NICKEL);
		assertEquals("Candy", machine.select("Candy"));
		assertEquals(0, machine.coinListToPrice(machine.emptyCoinReturn()), 0.001);
	}
	
	@Test(expected = InvalidProductException.class)
	public void testPressInvalidButtonShouldThrowInvalidProductException() throws InvalidProductException {
		machine.select("Bacon"); //Unfortunately, bacon isn't in the machine...
	}
	
	@Test
	public void testPressColaButtonWithMoreThanEnoughMoneyInsertedShouldReturnColaAndExtraMoney() throws InvalidProductException {
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.DIME);
		machine.insert(Coin.NICKEL);
		assertEquals("Cola", machine.select("Cola"));
		assertEquals(.40, machine.coinListToPrice(machine.emptyCoinReturn()), .001);
	}
	
	@Test
	public void testPressChipsButtonWithMoreThanEnoughMoneyInsertedShouldReturnChipsAndExtraMoney() throws InvalidProductException {
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.DIME);
		machine.insert(Coin.NICKEL);
		assertEquals("Chips", machine.select("Chips"));
		assertEquals(.15, machine.coinListToPrice(machine.emptyCoinReturn()), .001);
	}
	
	@Test
	public void testPressCandyButtonWithMoreThanEnoughMoneyInsertedShouldReturnCandyAndExtraMoney() throws InvalidProductException {
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.DIME);
		machine.insert(Coin.DIME);
		machine.insert(Coin.DIME);
		machine.insert(Coin.NICKEL);
		machine.insert(Coin.NICKEL);
		assertEquals("Candy", machine.select("Candy"));
		assertEquals(.50, machine.coinListToPrice(machine.emptyCoinReturn()), .001);
	}
	
	@Test
	public void testPressReturnCoinsButtonWithNoInputtedCoinsShouldReturnEmptyList() {
		machine.returnCoins();
		assertEquals(new ArrayList<Coin>(), machine.emptyCoinReturn());
	}
	
	@Test
	public void testPressReturnCoinsButtonWith40CentsInsertedShouldReturnListOfCoins() {
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.DIME);
		machine.insert(Coin.NICKEL);
		machine.returnCoins();
		List<Coin> list = new ArrayList<>();
		list.add(Coin.QUARTER);
		list.add(Coin.DIME);
		list.add(Coin.NICKEL);
		assertEquals(list, machine.emptyCoinReturn());
	}
	
	@Test
	public void testPressReturnCoinsButtonWith40CentsInsertedShouldReturn40Cents() {
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.DIME);
		machine.insert(Coin.NICKEL);
		machine.returnCoins();
		assertEquals(.40, machine.coinListToPrice(machine.emptyCoinReturn()), .001);
	}
	
	@Test
	public void testNeedExactCoinAmountForColaGivenExactCoinAmountShouldReturnCola() throws InvalidProductException {
		machine.clearCoinInventory();
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		assertEquals("Cola", machine.select("Cola"));
	}
	
	@Test
	public void testNeedExactCoinAmountForChipsGivenExactCoinAmountShouldReturnChips() throws InvalidProductException {
		machine.clearCoinInventory();
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		assertEquals("Chips", machine.select("Chips"));
	}
	
	@Test
	public void testNeedExactCoinAmountForCandyGivenExactCoinAmountShouldReturnCandy() throws InvalidProductException {
		machine.clearCoinInventory();
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.DIME);
		machine.insert(Coin.NICKEL);
		assertEquals("Candy", machine.select("Candy"));
	}
	
	@Test
	public void testNeedExactCoinAmountGivenForColaGivenExtraAmountShouldReturnNull() throws InvalidProductException {
		machine.clearCoinInventory();
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.DIME);
		machine.insert(Coin.DIME);
		machine.insert(Coin.DIME);
		assertEquals(null, machine.select("Cola"));
	}
	@Test
	public void testNeedExactCoinAmountGivenForColaGivenExtraAmountShouldReturnColaWithCoinsReturned() throws InvalidProductException {
		machine.clearCoinInventory();
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.DIME);
		machine.insert(Coin.DIME);
		machine.insert(Coin.DIME);
		machine.insert(Coin.QUARTER);
		assertEquals("Cola", machine.select("Cola"));
		assertEquals(.3, machine.coinListToPrice(machine.emptyCoinReturn()), 0.001);
	}
	
	@Test
	public void testGivenMoreThanEnoughCoinButCanStillMakeChangeShouldReturnColaWithCoinsReturned() throws InvalidProductException {
		machine.clearCoinInventory();
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		assertEquals("Cola", machine.select("Cola"));
		List<Coin> fauxCoinList = new ArrayList<>();
		fauxCoinList.add(Coin.QUARTER);
		assertEquals(fauxCoinList, machine.emptyCoinReturn());
	}
	
	@Test
	public void testOtherGivenMoreThanEnoughCoinButCanStillMakeChangeShouldReturnColaWithCoinsReturned() throws InvalidProductException {
		machine.clearCoinInventory();
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.DIME);
		assertEquals("Cola", machine.select("Cola"));
		List<Coin> fauxCoinList = new ArrayList<>();
		fauxCoinList.add(Coin.DIME);
		assertEquals(fauxCoinList, machine.emptyCoinReturn());
	}
	
	@Test
	public void testSoldOutColaShouldReturnSoldOut() throws InvalidProductException {
		machine.clearProductInventory();
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		assertEquals(null, machine.select("Cola"));
		machine.returnCoins();
		assertEquals(1, machine.coinListToPrice(machine.emptyCoinReturn()), 0.001);
	}
	
	@Test
	public void testGrabLastItemAndTryToGrabAgainShouldReturnChipsAndNull() throws InvalidProductException {
		machine.clearProductInventory();
		machine.addXProducts("Chips", 1);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		assertEquals("Chips", machine.select("Chips"));
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		assertEquals(null, machine.select("Chips"));
	}
	
}
