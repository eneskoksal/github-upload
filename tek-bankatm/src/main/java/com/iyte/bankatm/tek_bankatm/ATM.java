package com.iyte.bankatm.tek_bankatm;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.time.LocalDate;
import javax.money.MonetaryAmount;
import org.javamoney.moneta.Money;

public class ATM {

	private int minWithdrawPerTransaction;
	private int maxWithdrawPerTransaction;
	private int maxWithdrawPerDayAccount;
	private int limitTimeForOperation;	
	private Card anyCard;
	private CashDispenser MyCashDispenser;
	private CardReader MyCardReader;
	private Scanner MyKeyboard;	
	
	//After ATM is created set state to IDLE
	public ATM() {
		this.MyCashDispenser = new CashDispenser(new Log());
		this.MyCardReader = new CardReader(this);
		this.MyKeyboard = new Scanner(System.in);
		this.callStateOFF();
	}
	//ATM Func. REQ 1
	public void setInitialParameters(MonetaryAmount initAmount, int minWithdrawPerTransaction, 
			int maxWithdrawPerTransaction, int maxWithdrawPerDayAccount) {
		this.MyCashDispenser.setInitialCash(initAmount);
		this.minWithdrawPerTransaction = minWithdrawPerTransaction;
		this.maxWithdrawPerTransaction = maxWithdrawPerTransaction;
		this.maxWithdrawPerDayAccount = maxWithdrawPerDayAccount;
	}	

	//State functions
	public void callStateOFF() {
		System.out.println("Turned off");
	}
	public void callStateIDLE() {
		System.out.println("ATM is initialized, waiting for a card to be inserted");
	}
	public void callStateREADING_CARD() {
		System.out.println("Reading a card");
	}
	public void callStateWAITING_PASSWORD() {
		System.out.println("Card is read successfully");
		anyCard = MyCardReader.readCard();
		LocalDate today = LocalDate.now();
		if(anyCard.getExpireDate().compareTo(today) < 0) {
			System.out.println("Card date is expired");
			this.callStateEJECTING_CARD();
		}
		else {
			System.out.println("Please type your password");
			String password = MyKeyboard.nextLine();
			String response = this.verify(password);
			if(response == "OK") {
				
			} else if(response == "bad password") {
				
			} else if(response == "bad bank code") {
				
			} else if(response == "bad account") {
				
			}
		}
	}
	public void callStateCHOOSE_TRANSACTION() {
		System.out.println("Reading a card");
	}
	public void callStatePERFORMING_TRANSACTION() {
		System.out.println("Reading a card");
	}
	public void callStatePRINTING_RECEIPT() {
		System.out.println("Reading a card");
	}
	public void callStateEJECTING_CARD() {
		System.out.println("Ejecting the card");		
	}
	
	public String verify(String password) {
		//Implement verify function
		return "OK";
	}

	/**
	 * 
	 * @param accountNum
	 */
	public void readAccountNum(int accountNum) {
		// TODO - implement ATM.readAccountNum
		throw new UnsupportedOperationException();
	}

	public Message checkAvailabilityOfCashInATM(MonetaryAmount xmoney) {		
		if(MyCashDispenser.checkCashOnHand(xmoney)) {
			//?
		}			
		return new Message();
	}
	public MonetaryAmount getCashOnHand() {
		return this.MyCashDispenser.getCashOnHand();
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