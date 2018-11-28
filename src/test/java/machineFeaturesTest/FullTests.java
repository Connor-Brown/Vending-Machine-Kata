package machineFeaturesTest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import exceptions.InvalidProductException;
import model.Coin;
import service.VendingMachine;

public class FullTests {
	
	VendingMachine machine;
	
	@Before
	public void setup() {
		machine = new VendingMachine();
	}
	
	@Test
	public void perfectRunthrough() throws InvalidProductException {
		machine.returnCoins();
		assertEquals(new ArrayList<Coin>(), machine.emptyCoinReturn());
		machine.insert(Coin.QUARTER);
		machine.returnCoins();
		assertEquals(.25, machine.coinListToPrice(machine.emptyCoinReturn()), 0.001);
		assertEquals(0, machine.coinListToPrice(machine.emptyCoinReturn()), 0.001);
		assertEquals("INSERT COIN", machine.getDisplay());
		machine.select("Cola");
		assertEquals("PRICE $1.00", machine.getDisplay());
		machine.select("Cola");
		machine.insert(Coin.QUARTER);
		assertEquals("$0.25", machine.getDisplay());
		machine.insert(Coin.NICKEL);
		assertEquals("$0.30", machine.getDisplay());
		machine.insert(Coin.PENNY);
		assertEquals("$0.30", machine.getDisplay());
		assertEquals(.01, machine.coinListToPrice(machine.emptyCoinReturn()), 0.001);
		machine.insert(Coin.PENNY);
		machine.returnCoins();
		assertEquals(.31, machine.coinListToPrice(machine.emptyCoinReturn()), 0.001);
		machine.insert(Coin.NICKEL);
		machine.insert(Coin.NICKEL);
		machine.insert(Coin.NICKEL);
		machine.insert(Coin.NICKEL);
		machine.insert(Coin.NICKEL);
		machine.insert(Coin.NICKEL);
		machine.insert(Coin.NICKEL);
		machine.insert(Coin.NICKEL);
		machine.insert(Coin.NICKEL); //inserting 5 cents shy of a bag of chips (0.45 out of 0.50)
		assertEquals(null, machine.select("Chips"));
		machine.insert(Coin.NICKEL); //insert the last 5 cents for the bag of chips
		assertEquals("Chips", machine.select("Chips"));
		assertEquals(new ArrayList<Coin>(), machine.emptyCoinReturn());
		machine.returnCoins();
		assertEquals(new ArrayList<Coin>(), machine.emptyCoinReturn());
	}
	
}
