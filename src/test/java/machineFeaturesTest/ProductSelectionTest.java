package machineFeaturesTest;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import exceptions.InvalidProductException;
import exceptions.NotAcceptedCoinException;
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
	public void testPressColaButtonWithExactleyEnoughMoneyInsertedShouldReturnCola() throws InvalidProductException, NotAcceptedCoinException {
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		assertEquals("Cola", machine.select("Cola"));
	}
	
	@Test
	public void testPressChipsButtonWithExactleyEnoughMoneyInsertedShouldReturnChips() throws InvalidProductException, NotAcceptedCoinException {
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		assertEquals("Chips", machine.select("Chips"));
	}
	
	@Test
	public void testPressCandyButtonWithExactleyEnoughMoneyInsertedShouldReturnCandy() throws InvalidProductException, NotAcceptedCoinException {
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.DIME);
		machine.insert(Coin.NICKEL);
		assertEquals("Candy", machine.select("Candy"));
	}
	
	@Test(expected = InvalidProductException.class)
	public void testPressInvalidButtonShouldThrowInvalidProductException() throws InvalidProductException {
		machine.select("Bacon"); //Unfortunately, bacon isn't in the machine...
	}
	
	//TODO check selected product is null after purchase
	
}
