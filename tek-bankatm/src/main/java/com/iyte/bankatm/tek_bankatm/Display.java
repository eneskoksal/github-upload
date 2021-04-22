package com.iyte.bankatm.tek_bankatm;

import java.util.ArrayList;
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
	public String readMenuChoice(ArrayList<String> menu) {
		this.display("Please choose your action");
		for(int index = 0; index < menu.size(); index++) {
			this.display(menu.get(index));
		}
		return MyKeyboard.nextLine();
	}
	
	public int typedAccountNumber() {
		this.display("Enter the Account Number");		
		return Integer.parseInt(MyKeyboard.nextLine());
	}

	/**
	 * 
	 * @param prompt
	 */
	public double readAmount() {
		this.display("Enter the amount");		
		String inputAmount = MyKeyboard.nextLine();
		return Double.parseDouble(inputAmount);
	}

}