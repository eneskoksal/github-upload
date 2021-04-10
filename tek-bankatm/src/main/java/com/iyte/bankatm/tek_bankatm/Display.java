package com.iyte.bankatm.tek_bankatm;

import java.util.Scanner;

import javax.money.MonetaryAmount;

public class Display {
	
	private Scanner MyKeyboard;

	public Display() {
		this.MyKeyboard = new Scanner(System.in);
	}

	/**
	 * 
	 * @param message
	 */
	public void display(String message) {
		System.out.println("Display: " + message);
	}

	/**
	 * 
	 * @param prompt
	 */
	public String readPIN(String prompt) {
		this.display(prompt);
		String password = MyKeyboard.nextLine();
		return password;
	}

	/**
	 * 
	 * @param prompt
	 * @param menu
	 */
	public int readMenuChoice(String prompt, String[] menu) {
		// TODO - implement Display.readMenuChoice
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param prompt
	 */
	public MonetaryAmount readAmount(String prompt) {
		// TODO - implement Display.readAmount
		throw new UnsupportedOperationException();
	}

}