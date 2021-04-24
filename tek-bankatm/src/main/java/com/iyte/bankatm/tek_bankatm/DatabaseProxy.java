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
	public void setWrongPasswordCount(int cardSerialNumber, int value) {
		Account theAccount = selectAccountByCardSerialNo(cardSerialNumber);
		theAccount.setWrongPasswordCount(value);
	}
	public int getWrongPasswordCount(int cardSerialNumber) {
		Account theAccount = selectAccountByCardSerialNo(cardSerialNumber);
		return theAccount.getWrongPasswordCount();
	}
	public void minusBalance(int cardSerialNumber, MonetaryAmount amount) {
		Account findAccount = selectAccountByCardSerialNo(cardSerialNumber);
		MonetaryAmount newBalance = findAccount.getBalance().subtract(amount);
		findAccount.setBalance(newBalance);		
	}
	public void plusBalance(int cardSerialNumber, MonetaryAmount amount ) {
		Account findAccount = selectAccountByCardSerialNo(cardSerialNumber);
		MonetaryAmount newBalance = findAccount.getBalance().add(amount);
		findAccount.setBalance(newBalance);
	}
	public void setLeftMaxWithdrawPerDay(int cardSerialNumber, MonetaryAmount amount ) {
		Account findAccount = selectAccountByCardSerialNo(cardSerialNumber);
		MonetaryAmount newLimit = findAccount.getLeftMaxWithdrawPerDay().subtract(amount);
		findAccount.setLeftMaxWithdrawPerDay(newLimit);
	}
	public void createNewAccount(Account newAccount) {
		DB.add(newAccount);
	}

}