package com.iyte.bankatm.tek_bankatm;

import java.time.LocalDate;
import java.util.Random;
import java.util.Scanner;

import javax.money.MonetaryAmount;

import org.javamoney.moneta.Money;

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
	
	public String verifyTransaction(Account AccountToWithdrawal, MonetaryAmount Amount ) { 
		MonetaryAmount Balance = AccountToWithdrawal.getBalance();		
		if(Balance.isLessThan(Amount) || AccountToWithdrawal.getLeftMaxWithdrawPerDay().isLessThan(Amount) ){
			return "transaction failed"; // Bank Func REQ 7
		}
		AccountToWithdrawal.setBalance(Balance.subtract(Amount)); // Bank Func REQ 8
		AccountToWithdrawal.setLeftMaxWithdrawPerDay(Amount);  // Bank Func REQ 9
		return "transaction succeeded";
	}
	
	public Account createNewAccount(int account_number,String password, int accountType, int serialNumber, LocalDate expireDate){
		Account newAccount = new Account(this.MyATM.getMaxWithdrawPerDayAccount());
		newAccount.setAccount_number(account_number);
		newAccount.setAccountType(accountType);
		newAccount.setMyCard(new Card(serialNumber, expireDate));		
		newAccount.setPassword(password);
		MyDatabaseProxy.createNewAccount(newAccount);
		return newAccount;
	}
	
	public ATM getMyATM() {
		return this.MyATM;
	}

	public Account verifyAccountNumber(int accountNumber) {
		// TODO Auto-generated method stub
		return this.MyDatabaseProxy.selectAccountByAccountNumber(accountNumber);
	}
	public Account getAccountNumberBySerialNumber(int serialNumber) {
		return MyDatabaseProxy.selectAccountByCardSerialNo(serialNumber);
	}

	public void startTransfer(double Amount) {
		// TODO Auto-generated method stub
		
	}
}