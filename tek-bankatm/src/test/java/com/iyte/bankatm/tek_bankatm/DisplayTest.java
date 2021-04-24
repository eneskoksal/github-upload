package com.iyte.bankatm.tek_bankatm;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DisplayTest {
	
	private Display testDisplay;
	//Simulate user input, helper function
	private void provideInput(String data) {
		InputStream in = new ByteArrayInputStream(data.getBytes());
	    System.setIn(in);
	}
	//Create new object before tests
    @Before
    public void setUp() {
    	testDisplay = new Display();
    }
    //Restore system input after tests
    @After
    public void restoreSystemInput() {
        System.setIn(System.in);        
    }
    
	@Test
	public void readPINTest() {
		//Simulate user pin
		String testPIN = "121";
		provideInput(testPIN);
	    String password = testDisplay.readPIN("Type your password");	    
	    assertEquals(testPIN, password);
	}	
	@Test
	public void readMenuChoice() {
		//Create menu
		ArrayList<String> menu = new ArrayList<String>();
		menu.add("Test_Menu 1");
		menu.add("Test_Menu 2");
		//Simulate user choice
		String testChoice = "w";
		provideInput(testChoice);
		String Choice = testDisplay.readMenuChoice(menu);
		assertEquals(testChoice, Choice);
	}
	@Test
	public void typedAccountNumberTest() {
		//Simulate user input
		String testInput = "123456";
		provideInput(testInput);
		int typedNum = testDisplay.typedAccountNumber();
		assertEquals(Integer.parseInt(testInput), typedNum);
	}
	@Test
	public void readAmountTest() {
		//Simulate user input
		String testInput = "123.456789";
		provideInput(testInput);
		double typedNum = testDisplay.readAmount();
		assertEquals(Double.parseDouble(testInput), typedNum, 0.0001); //Epsilon = 0.0001 has to be defined
	}
	
}
