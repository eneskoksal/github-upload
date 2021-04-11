package com.iyte.bankatm.tek_bankatm;

import javax.money.MonetaryAmount;

import org.javamoney.moneta.Money;

public class Account {

	private int account_number;
	private String password;
	private MonetaryAmount balance;
	private int accountType;
	private Card myCard;
	private int wrongPasswordCount;
	
	public Account() {
		this.wrongPasswordCount = 0;
		this.balance = Money.of(0, "USD");
	}
	
	public int getAccount_number() {
		return account_number;
	}
	public void setAccount_number(int account_number) {
		this.account_number = account_number;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public MonetaryAmount getBalance() {
		return balance;
	}
	public void setBalance(MonetaryAmount balance) {
		this.balance = balance;
	}
	public int getAccountType() {
		return accountType;
	}
	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}
	public Card getMyCard() {
		return myCard;
	}
	public void setMyCard(Card myCard) {
		this.myCard = myCard;
	}

	public int getWrongPasswordCount() {
		return wrongPasswordCount;
	}

	public void setWrongPasswordCount(int wrongPasswordCount) {
		this.wrongPasswordCount = wrongPasswordCount;
	}

}