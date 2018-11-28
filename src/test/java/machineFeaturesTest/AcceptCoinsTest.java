package machineFeaturesTest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import model.Coin;
import service.VendingMachine;

public class AcceptCoinsTest {
	
	VendingMachine machine;
	
	@Before
	public void setUp() {
		//create machine
		machine = new VendingMachine();
	}
	
	@Test
	public void testInsertSingleNicketShouldShowSingleNickelInMachine() {
		machine.insert(Coin.NICKEL);
		assertEquals(machine.getNickels(), 1);
	}
	
}
