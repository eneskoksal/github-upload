package com.iyte.bankatm.tek_bankatm;

import java.time.LocalDateTime;
import javax.money.MonetaryAmount;
import org.javamoney.moneta.Money;

public class ATM {

	private int minWithdrawPerTransaction;
	private int maxWithdrawPerTransaction;
	private int maxWithdrawPerDayAccount;
	private int limitTimeForOperation;	
	private Card anyCard;
	private ATMstate state;
	
	CashDispenser MyCashDispenser = new CashDispenser(new Log());
	CardReader MyCardReader = new CardReader(this);
	
	//After ATM is created set state to IDLE
	public ATM() {
		this.state = ATMstate.IDLE;
	}
	//ATM Func. REQ 1
	public void setInitialParameters(MonetaryAmount initAmount, int minWithdrawPerTransaction, 
			int maxWithdrawPerTransaction, int maxWithdrawPerDayAccount) {
		this.MyCashDispenser.setInitialCash(initAmount);
		this.minWithdrawPerTransaction = minWithdrawPerTransaction;
		this.maxWithdrawPerTransaction = maxWithdrawPerTransaction;
		this.maxWithdrawPerDayAccount = maxWithdrawPerDayAccount;
	}
	//This shall be called by card reader
	public void getCardInfo(Card MyCard) {
		this.anyCard = MyCard;
	}
	//Set current state of ATM
	public void setState(ATMstate state) {
		this.state = state;
	}
	/**
	 * 
	 * @param password
	 */
	public String verify(String password) {
		// TODO - implement ATM.verify
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param accountNum
	 */
	public void readAccountNum(int accountNum) {
		// TODO - implement ATM.readAccountNum
		throw new UnsupportedOperationException();
	}

	public Message checkAvailabilityOfCashInATM() {
		// TODO - implement ATM.checkAvailabilityOfCashInATM
		throw new UnsupportedOperationException();
	}

	public Message verifyInputAmount() {
		// TODO - implement ATM.verifyInputAmount
		throw new UnsupportedOperationException();
	}

	public LocalDateTime checkTime() {
		// TODO - implement ATM.checkTime
		return LocalDateTime.now();
	}

}