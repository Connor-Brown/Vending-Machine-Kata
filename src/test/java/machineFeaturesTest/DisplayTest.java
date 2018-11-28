package machineFeaturesTest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import exceptions.InvalidProductException;
import exceptions.NotAcceptedCoinException;
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
			throws InvalidProductException, NotAcceptedCoinException {
		machine.insert(Coin.NICKEL);
		machine.select("Cola");
		assertEquals("PRICE $1.00", machine.getDisplay());
		assertEquals("$0.05", machine.getDisplay()); // after display gets checked again, display changes to INSERT COIN
														// or current amount inserted
	}

	@Test
	public void checkDisplayAfterInsertingNickelShouldReturnFiveCentsInMachine() throws NotAcceptedCoinException {
		machine.insert(Coin.NICKEL);
		assertEquals("$0.05", machine.getDisplay());
	}

}
