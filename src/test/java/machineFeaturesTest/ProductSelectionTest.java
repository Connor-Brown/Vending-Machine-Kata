package machineFeaturesTest;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import exceptions.InvalidProductException;
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
	public void testPressColaButtonShouldReturnSelectedProductAsCola() throws InvalidProductException {
		machine.select("Cola");
		assertEquals("Cola", machine.getSelectedProductName());
		assertEquals(new Double(1), machine.getSelectedProductPrice());
	}
	
	@Test
	public void testPressChipsButtonShouldReturnSelectedProductAsChips() throws InvalidProductException {
		machine.select("Chips");
		assertEquals("Chips", machine.getSelectedProductName());
		assertEquals(new Double(.5), machine.getSelectedProductPrice());
	}
	
	@Test
	public void testPressCandyButtonShouldReturnSelectedProductAsCandy() throws InvalidProductException {
		machine.select("Candy");
		assertEquals("Candy", machine.getSelectedProductName());
		assertEquals(new Double(.65), machine.getSelectedProductPrice());
	}
	
	@Test(expected = InvalidProductException.class)
	public void testPressInvalidButtonShouldThrowInvalidProductException() throws InvalidProductException {
		machine.select("Bacon");
	}
	
	@Test
	public void testGetSelectedProductWithoutPressingButtonShouldReturnNull() {
		assertEquals(null, machine.getSelectedProductName());
		assertEquals(null, machine.getSelectedProductPrice());
	}
	
	//TODO check selected product is null after purchase
	
}
