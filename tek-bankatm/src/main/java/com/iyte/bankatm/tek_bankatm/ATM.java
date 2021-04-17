package com.iyte.bankatm.tek_bankatm;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.time.LocalDate;
import javax.money.MonetaryAmount;
import org.javamoney.moneta.Money;

public class ATM {
	
	private Bank MyBank;
	public Bank getMyBank() {
		return MyBank;
	}
	public void setMyBank(Bank myBank) {
		MyBank = myBank;
	}

	private int minWithdrawPerTransaction;
	private int maxWithdrawPerTransaction;
	public int getMinWithdrawPerTransaction() {
		return minWithdrawPerTransaction;
	}
	public void setMinWithdrawPerTransaction(int minWithdrawPerTransaction) {
		this.minWithdrawPerTransaction = minWithdrawPerTransaction;
	}

	public int getMaxWithdrawPerTransaction() {
		return maxWithdrawPerTransaction;
	}
	public void setMaxWithdrawPerTransaction(int maxWithdrawPerTransaction) {
		this.maxWithdrawPerTransaction = maxWithdrawPerTransaction;
	}

	private int maxWithdrawPerDayAccount;
	private int limitTimeForOperation;	
	private int cardSerialNumber;	
	private CashDispenser MyCashDispenser;
	private CardReader MyCardReader;	
	private Display MyDisplay;
	private OperatorPanel MyOperatorPanel;
	private ATMstate state;
	
	//After ATM is created set state to IDLE
	public ATM(Bank aBank) {
		this.MyBank = aBank;
		this.MyCashDispenser = new CashDispenser(new Log());
		this.MyCardReader = new CardReader(this);		
		this.MyDisplay = new Display();
		this.MyOperatorPanel = new OperatorPanel(this);
		this.callStateOFF();		
	}
	//ATM Func. REQ 1
	public void setInitialParameters(MonetaryAmount initAmount, int minWithdrawPerTransaction, 
			int maxWithdrawPerTransaction, int maxWithdrawPerDayAccount) {
		this.MyCashDispenser.setInitialCash(initAmount);
		this.minWithdrawPerTransaction = minWithdrawPerTransaction;
		this.maxWithdrawPerTransaction = maxWithdrawPerTransaction;
		this.maxWithdrawPerDayAccount = maxWithdrawPerDayAccount;
		new Log().logSend("ATM is initialized");
	}	

	//***************State functions started**********************
	public void callStateOFF() {
		new Log().logSend("Turned off");
	}
	//ATM Func. REQ 2
	public void callStateIDLE() {		
		MyDisplay.display("Initial screen, waiting for a card to be inserted");
	}
	
	public void callStateREADING_CARD() {
		new Log().logSend("Reading a card");
		Card insertedCard = MyCardReader.readCard();
		if (insertedCard != null) {
			//ATM Func REQ 4
			LocalDate today = LocalDate.now();
			if(insertedCard.getExpireDate().compareTo(today) < 0) {
				MyDisplay.display("Card date is expired");
				this.callStateEJECTING_CARD();
			}
			else {
				//ATM Func. REQ 5
				this.cardSerialNumber = insertedCard.getSerialNumber();				
				//ATM Func. REQ 6
				new Log().logSend("Card serial number : " + cardSerialNumber);
				this.callStateWAITING_PASSWORD();
			}
		}
		else {
			//ATM Func REQ 4
			MyDisplay.display("The information on the card can't be read");
			this.callStateEJECTING_CARD();
		}
	}
	
	public void callStateWAITING_PASSWORD() {
		new Log().logSend("Card is read successfully");
		//ATM Func REQ 7
		String password = MyDisplay.readPIN("Please type your password");		
		String response = this.verify(password, cardSerialNumber);
		
		//ATM Func REQ 9
		if(response.equals("account ok")) {			
			callStateCHOOSE_TRANSACTION();			
		//ATM Func REQ 8
		} else if(response.equals("bad password")) {
			MyDisplay.display("Password is wrong");
			callStateEJECTING_CARD();
		//ATM Func REQ 10
		} else if(response == "keep the card") {
			callStateRETAINING_CARD();
		//ATM Func REQ 8	
		} else if(response.equals("bad bank code")) {
			MyDisplay.display("Bank code is wrong");
			callStateEJECTING_CARD();
		//ATM Func REQ 8	
		} else if(response.equals("bad account")) {
			MyDisplay.display("Some problem with account");
			callStateEJECTING_CARD();
			
		} else {
			MyDisplay.display("Unhandled exception");
			callStateEJECTING_CARD();
		}
		
		
	}
	
	public void callStateCHOOSE_TRANSACTION() {
		System.out.println("Transaction");
		//MyDisplay.display("Reading a card:\nOnly withdrawal is offered.\n");
		this.state = ATMstate.CHOOSE_TRANSACTION;
	}
	public void callStatePERFORMING_TRANSACTION() {
		System.out.println("Reading a card");
	}
	public void callStatePRINTING_RECEIPT() {
		System.out.println("Printing Receipt");
	}
	public void callStateEJECTING_CARD() {
		MyDisplay.display("Ejecting the card");
		MyCardReader.ejectCard();
		callStateIDLE(); //Session completed return to idle
		MyDisplay.display("You should take your card");
	}
	//ATM Func REQ 10
	public void callStateRETAINING_CARD() {
		MyCardReader.retainCard();
		MyDisplay.display("Card is retained please call the bank");
	}
	
	//***************State functions ended**********************
	
	//ATM Func REQ 7, authorization by bank
	public String verify(String password, int cardSerialNumber) {
		return MyBank.verifyRequest(password, cardSerialNumber);
	}

	public void readAccountNum(int accountNum) {
		// TODO - implement ATM.readAccountNum
		throw new UnsupportedOperationException();
	}
	//User triggers this function
	public void userInsertedCard(Card aCard) {
		MyCardReader.insertCard(aCard);
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
	public OperatorPanel getMyOperatorPanel() {
		return this.MyOperatorPanel;
	}
	
	public ATMstate getState() {
		return this.state;
	}
	public void dispenseCash(MonetaryAmount Amount) {
		this.MyCashDispenser.dispenseCash(Amount);
	}

}