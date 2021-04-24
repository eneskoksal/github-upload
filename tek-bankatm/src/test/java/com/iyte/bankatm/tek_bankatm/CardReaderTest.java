package com.iyte.bankatm.tek_bankatm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Before;
import org.junit.Test;

public class CardReaderTest {
	private CardReader testCardReader;
	private Card testCard;
	private ATM testATM;
	//Generate random date
	private LocalDate randLocalDate() {
		long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
	    long maxDay = LocalDate.of(2030, 12, 31).toEpochDay();
	    long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
	    LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
	    return randomDate;
	}
	
	@Before
	public void setUp() {
		testATM = new ATM(new Bank());
		testCardReader = new CardReader(testATM);
		int cardSerialNumber = ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
		testCard = new Card(cardSerialNumber, randLocalDate());		
	}
	@Test
	public void insertCardTest_Valid() {		
		testCardReader.insertCard(testCard);
		assertEquals(testCard, testCardReader.readCard());
		//Observe ATM goes from IDLE/OFF to another state
		assertTrue((testATM.getState() != ATMstate.IDLE) && (testATM.getState() != ATMstate.OFF));		
	}
	@Test
	public void insertCardTest_Null() {		
		testCardReader.insertCard(null);
		assertEquals(null, testCardReader.readCard());
		//Observe ATM goes from IDLE/OFF to another state
		assertTrue((testATM.getState() != ATMstate.IDLE) && (testATM.getState() != ATMstate.OFF));		
	}
}
