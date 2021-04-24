package com.iyte.bankatm.tek_bankatm;

import java.util.ArrayList;
import java.util.Scanner;

public class Display {
	
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
		Scanner MyKeyboard = new Scanner(System.in);
		this.display(prompt);
		String password = MyKeyboard.nextLine();
		MyKeyboard.close();
		return password;		
	}

	/**
	 * 
	 * @param prompt
	 * @param menu
	 */
	public String readMenuChoice(ArrayList<String> menu) {
		Scanner MyKeyboard = new Scanner(System.in);
		this.display("Please choose your action");
		for(int index = 0; index < menu.size(); index++) {
			this.display(menu.get(index));
		}
		String getInput = MyKeyboard.nextLine();
		MyKeyboard.close();
		return getInput;
	}
	
	public int typedAccountNumber() {
		Scanner MyKeyboard = new Scanner(System.in);
		this.display("Enter the Account Number");		
		int getInt = Integer.parseInt(MyKeyboard.nextLine());
		MyKeyboard.close();
		return getInt;
		
	}

	/**
	 * 
	 * @param prompt
	 */
	public double readAmount() {
		Scanner MyKeyboard = new Scanner(System.in);
		this.display("Enter the amount");		
		String inputAmount = MyKeyboard.nextLine();
		MyKeyboard.close();
		return Double.parseDouble(inputAmount);
	}

}