package com.iyte.bankatm.tek_bankatm;

import java.util.ArrayList;
import javax.money.MonetaryAmount;

public class DatabaseProxy {
	
	private ArrayList<Account> DB;
	
	public DatabaseProxy() {
		this.DB = new ArrayList<Account>();
	}
	
	public Account selectAccountByCardSerialNo(int cardSerialNumber) {
		for (int i = 0; i < DB.size(); i++) {
			Account current = DB.get(i);
			if(current.getMyCard().getSerialNumber() == cardSerialNumber)
				return current;
	    }
		return null;
	}
	public Account selectAccountByAccountNumber(int accountNumber) {
		for (int i = 0; i < DB.size(); i++) {
			Account current = DB.get(i);
			if(current.getAccount_number() == accountNumber)
				return current;
	    }
		return null;
	}
	public void setWrongPasswordCount(int accountNumber, int value) {
		Account theAccount = selectAccountByAccountNumber(accountNumber);
		theAccount.setWrongPasswordCount(value);
	}
	public int getWrongPasswordCount(int accountNumber) {
		Account theAccount = selectAccountByAccountNumber(accountNumber);
		return theAccount.getWrongPasswordCount();
	}
	public void minusBalance(int accountNumber, double amount) {
		
	}
	public void plusBalance() {
		// TODO - implement DatabaseProxy.plusBalance
		throw new UnsupportedOperationException();
	}

	public void createNewAccount(Account newAccount) {
		DB.add(newAccount);
	}

	public MonetaryAmount checkTheBalance(Account anAccount) {
		return anAccount.getBalance();
	}

}