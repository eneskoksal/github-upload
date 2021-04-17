package com.iyte.bankatm.tek_bankatm;

import java.time.LocalDate;
import java.util.Random;
import java.util.Scanner;

import javax.money.MonetaryAmount;

public class Bank {
	private DatabaseProxy MyDatabaseProxy;
	private ATM MyATM;
	private Scanner MyKeyboard;
	
	public Bank() {
		this.MyDatabaseProxy = new DatabaseProxy();
		this.MyATM = new ATM(this);
		this.MyKeyboard = new Scanner(System.in);
	}
	
	public String verifyRequest(String password, int cardSerialNumber) {
		Account theAccount = MyDatabaseProxy.selectAccountByCardSerialNo(cardSerialNumber);
		if(theAccount != null) {
			String expectedPassword = theAccount.getPassword();			
			//Bank Func REQ 5
			if(expectedPassword == null) { //No password is initialized
				return "bad account";
			//Bank Func REQ 3 & 4 & 6
			}else if(expectedPassword.equals(password)) {
				MyDatabaseProxy.setWrongPasswordCount(theAccount.getAccount_number(), 0);
				return "account ok";
			}else {
				int wrongAttempt = MyDatabaseProxy.getWrongPasswordCount(theAccount.getAccount_number());				
				if(wrongAttempt >= 3) { //In fourth wrong attempt(index = 3) keep the card
					return "keep the card";
				}else {					
					MyDatabaseProxy.setWrongPasswordCount(theAccount.getAccount_number(), ++wrongAttempt);
					return "bad password";
				}				
			}			
		}
		//Bank Func REQ 1 & 2
		//Account which is connected with this card serial number has not found 
		return "bad bank code";
	}
	
	public String verifyTransaction() {        
		Random rand = new Random();
		int rand2 = rand.nextInt();
		if(rand2%2 == 0)
			return "transaction succeeded";
		else
			return "transaction not successful";
				
	}
	
	public void createNewAccount(int account_number,String password, int accountType, int serialNumber, LocalDate expireDate){
		Account newAccount = new Account();
		newAccount.setAccount_number(account_number);
		newAccount.setAccountType(accountType);
		newAccount.setMyCard(new Card(serialNumber, expireDate));		
		newAccount.setPassword(password);
		MyDatabaseProxy.createNewAccount(newAccount);
	}
	
	public ATM getMyATM() {
		return this.MyATM;
	}

	public Account verifyAccountNumber(int accountNumber) {
		// TODO Auto-generated method stub
		return this.MyDatabaseProxy.selectAccountByAccountNumber(accountNumber);
	}

	public void startTransfer(double Amount) {
		// TODO Auto-generated method stub
		
	}
}