package com.iyte.bankatm.tek_bankatm;

import java.time.LocalDateTime;
import java.time.LocalDate;
import javax.money.MonetaryAmount;
import org.javamoney.moneta.Money;

public class ATM {

	private int minWithdrawPerTransaction;
	private int maxWithdrawPerTransaction;
	private int maxWithdrawPerDayAccount;
	private int limitTimeForOperation;	
	private Card anyCard;
	private FSM currState;
	private FSM nextState;
	private CashDispenser MyCashDispenser;
	private CardReader MyCardReader;
	
	//After ATM is created set state to IDLE
	public ATM() {
		this.MyCashDispenser = new CashDispenser(new Log());
		this.MyCardReader = new CardReader(this);
		this.setState(FSM.OFF);
		this.currState = FSM.OFF;
		this.nextState = FSM.OFF;
	}
	//ATM Func. REQ 1
	public void setInitialParameters(MonetaryAmount initAmount, int minWithdrawPerTransaction, 
			int maxWithdrawPerTransaction, int maxWithdrawPerDayAccount) {
		this.MyCashDispenser.setInitialCash(initAmount);
		this.minWithdrawPerTransaction = minWithdrawPerTransaction;
		this.maxWithdrawPerTransaction = maxWithdrawPerTransaction;
		this.maxWithdrawPerDayAccount = maxWithdrawPerDayAccount;
	}	
	//Set current state of ATM
	private void setState(FSM state) {
		switch(state) {
			case OFF:
				System.out.println("Turned off");
				break;
			case IDLE:
				System.out.println("ATM is initialized, waiting for a card to be inserted");
				break;
			case READING_CARD:
				System.out.println("Reading a card");
				break;
			case CARD_READ_SUCCESS:
				System.out.println("Card is read successfully");
				anyCard = MyCardReader.readCard();
				LocalDate today = LocalDate.now();
				if(anyCard.getExpireDate().compareTo(today) < 0) {
					System.out.println("Card date is expired");
					this.nextState = FSM.EJECTING_CARD;
				}
				break;
			case WAITING_PASSWORD:
				
				break;
			case CHOOSE_TRANSACTION:
				
				break;
			case PERFORMING_TRANSACTION:
				
				break;
			case PRINTING_RECEIPT:
				
				break;
			case EJECTING_CARD:
				System.out.println("Ejecting the card");
				this.nextState = FSM.IDLE;				
				break;
			
		}
	}
	public void setNextState(FSM nextState) {
		this.nextState = nextState;
	}
	//Finite state machine to be called in main
	public void FSM() {
		if(this.currState != this.nextState)
			this.setState(this.nextState);
	}
	
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