package machineFeaturesTest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

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
		machine.clearCoinInventory();
	}
	
	@Test
	public void testInsertSingleNickelShouldReturn1()  {
		machine.insert(Coin.NICKEL);
		assertEquals(1, machine.getNickels());
	}
	
	@Test
	public void testInsert5NickelsShouldReturn5()  {
		machine.insert(Coin.NICKEL);
		machine.insert(Coin.NICKEL);
		machine.insert(Coin.NICKEL);
		machine.insert(Coin.NICKEL);
		machine.insert(Coin.NICKEL);
		assertEquals(5, machine.getNickels());
	}
	
	@Test
	public void testInsertSingleDimeShouldReturn1()  {
		machine.insert(Coin.DIME);
		assertEquals(1, machine.getDimes());
	}
	
	@Test
	public void testInsert6DimesShouldReturn6()  {
		machine.insert(Coin.DIME);
		machine.insert(Coin.DIME);
		machine.insert(Coin.DIME);
		machine.insert(Coin.DIME);
		machine.insert(Coin.DIME);
		machine.insert(Coin.DIME);
		assertEquals(6, machine.getDimes());
	}
	
	@Test
	public void testInsertSingleQuarterShouldReturn1()  {
		machine.insert(Coin.QUARTER);
		assertEquals(1, machine.getQuarters());
	}
	
	@Test
	public void testInsert4QuartersShouldReturn4()  {
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		machine.insert(Coin.QUARTER);
		assertEquals(4, machine.getQuarters());
	}
	
	@Test
	public void testMultiCoinInsert2Nickels1Dime3QuartersShouldReturn2Nickels1Dime3Quarters()  {
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
	
	@Test
	public void testInsertPennyShouldReturnPennyInCoinReturn()  {
		machine.insert(Coin.PENNY);
		List<Coin> fauxCoinReturn = new ArrayList<>();
		fauxCoinReturn.add(Coin.PENNY);
		assertEquals(fauxCoinReturn, machine.emptyCoinReturn());
	}
	
	@Test
	public void testInsertMultiplePenniesShouldReturnAllPenniesInCoinReturn() {
		machine.insert(Coin.PENNY);
		machine.insert(Coin.PENNY);
		machine.insert(Coin.PENNY);
		machine.insert(Coin.PENNY);
		machine.insert(Coin.PENNY);
		List<Coin> fauxCoinReturn = new ArrayList<>();
		fauxCoinReturn.add(Coin.PENNY);
		fauxCoinReturn.add(Coin.PENNY);
		fauxCoinReturn.add(Coin.PENNY);
		fauxCoinReturn.add(Coin.PENNY);
		fauxCoinReturn.add(Coin.PENNY);
		assertEquals(fauxCoinReturn, machine.emptyCoinReturn());
	}
	
}
