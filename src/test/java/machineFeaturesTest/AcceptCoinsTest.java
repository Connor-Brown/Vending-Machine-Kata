package machineFeaturesTest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import exceptions.NotAcceptedCoinException;
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
	public void testInsertSingleNickelShouldReturn1() throws NotAcceptedCoinException {
		machine.insert(Coin.NICKEL);
		assertEquals(1, machine.getNickels());
	}
	
	@Test
	public void testInsert5NickelsShouldReturn5() throws NotAcceptedCoinException {
		machine.insert(Coin.NICKEL);
		machine.insert(Coin.NICKEL);
		machine.insert(Coin.NICKEL);
		machine.insert(Coin.NICKEL);
		machine.insert(Coin.NICKEL);
		assertEquals(5, machine.getNickels());
	}
	
	@Test
	public void testInsertSingleDimeShouldReturn1() throws NotAcceptedCoinException {
		machine.insert(Coin.DIME);
		assertEquals(1, machine.getDimes());
	}
	
	@Test
	public void testInsert6DimesShouldReturn6() throws NotAcceptedCoinException {
		machine.insert(Coin.DIME);
		machine.insert(Coin.DIME);
		machine.insert(Coin.DIME);
		machine.insert(Coin.DIME);
		machine.insert(Coin.DIME);
		machine.insert(Coin.DIME);
		assertEquals(6, machine.getDimes());
	}
	
	@Test
	public void testInsertSingleQuarterShouldReturn1() throws NotAcceptedCoinException {
		machine.insert(Coin.QUARTER);
		assertEquals(1, machine.getQuarters());
	}
	
	@Test
	public void testInsert4QuartersShouldReturn4() throws NotAcceptedCoinException {
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		assertEquals(4, machine.getQuarters());
	}
	
	@Test
	public void testMultiCoinInsert2Nickels1Dime3QuartersShouldReturn2Nickels1Dime3Quarters() throws NotAcceptedCoinException {
		machine.insert(Coin.NICKEL);
		machine.insert(Coin.NICKEL);
		machine.insert(Coin.DIME);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		assertEquals(2, machine.getNickels());
		assertEquals(1, machine.getDimes());
		assertEquals(3, machine.getQuarters());
	}
	
	@Test(expected = NotAcceptedCoinException.class)
	public void testInsertPennyShouldReturnNotAcceptedCoinException() throws NotAcceptedCoinException {
		machine.insert(Coin.PENNY);
	}
	
}
