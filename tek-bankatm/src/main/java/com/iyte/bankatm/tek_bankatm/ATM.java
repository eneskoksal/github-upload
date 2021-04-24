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

	private MonetaryAmount maxWithdrawPerDayAccount;
	public MonetaryAmount getMaxWithdrawPerDayAccount() {
		return maxWithdrawPerDayAccount;
	}
	public void setMaxWithdrawPerDayAccount(MonetaryAmount maxWithdrawPerDayAccount) {
		this.maxWithdrawPerDayAccount = maxWithdrawPerDayAccount;
	}

	private int limitTimeForOperation;	
	private int cardSerialNumber;	
	private CashDispenser MyCashDispenser;
	public CashDispenser getMyCashDispenser() {
		return MyCashDispenser;
	}
	public void setMyCashDispenser(CashDispenser myCashDispenser) {
		MyCashDispenser = myCashDispenser;
	}


	private CardReader MyCardReader;	
	private Display MyDisplay;
	private OperatorPanel MyOperatorPanel;
	private Card insertedCard;
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
			int maxWithdrawPerTransaction, MonetaryAmount maxWithdrawPerDayAccount) {
		this.MyCashDispenser.setInitialCash(initAmount);
		this.setMinWithdrawPerTransaction(minWithdrawPerTransaction);
		this.setMaxWithdrawPerTransaction(maxWithdrawPerTransaction);
		this.setMaxWithdrawPerDayAccount(maxWithdrawPerDayAccount);
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
		insertedCard = MyCardReader.readCard();
		boolean isCashAvail = checkAvailabilityOfCashInATM();
		if (insertedCard != null && isCashAvail) {
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
		//ATM Func. REQ 3
		else if(!isCashAvail) {
			MyDisplay.display("Not enough money in the ATM");
			this.callStateEJECTING_CARD();
		}
		else{
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
			this.state =  ATMstate.CHOOSE_TRANSACTION;	
		//ATM Func REQ 8
		} else if(response.equals("bad password")) {
			MyDisplay.display("Password is wrong");
			callStateEJECTING_CARD();
			this.state = ATMstate.EJECTING_CARD;
		//ATM Func REQ 10
		} else if(response == "keep the card") {
			callStateRETAINING_CARD();
			this.state = ATMstate.RETAINING_CARD;
		//ATM Func REQ 8	
		} else if(response.equals("bad bank code")) {
			MyDisplay.display("Bank code is wrong");
			callStateEJECTING_CARD();
			this.state = ATMstate.EJECTING_CARD;
		//ATM Func REQ 8	
		} else if(response.equals("bad account")) {
			MyDisplay.display("Some problem with account");
			callStateEJECTING_CARD();
			this.state = ATMstate.EJECTING_CARD;
			
		} else {
			MyDisplay.display("Unhandled exception");
			callStateEJECTING_CARD();
			this.state = ATMstate.EJECTING_CARD;
		}		
	}
	
	public ATMstate getState() {
		return state;
	}

	public void callStateCHOOSE_TRANSACTION() {
		Transaction anyTransaction = new Transaction(this, this.insertedCard);    	    	
    	String Option  =  MyDisplay.readMenuChoice(anyTransaction.getOfferedTransactions());
    	callStatePERFORMING_TRANSACTION(anyTransaction, Option);
	}
	public void callStatePERFORMING_TRANSACTION(Transaction anyTransaction, String Option) {
		if(Option.charAt(0) == 'w'){
    		anyTransaction.setType(TransactionTypes.Withdrawal);
        	anyTransaction.readAmount();
        	Boolean IsVerifed = anyTransaction.verify();
        	if(IsVerifed) {
        		System.out.println("It's ok!");
        		anyTransaction.initiateSequence();
        	}else{
        		System.out.println("It's nok!");        		
        	}
    	}else if(Option.charAt(0) == 't') {
    		anyTransaction.setType(TransactionTypes.Transfer);
    		Account toTransfer = anyTransaction.readAccountNumber();
    		if(toTransfer != null) {
	        	anyTransaction.readAmount();
	        	Boolean IsVerifed = anyTransaction.verify();
	        	if(IsVerifed) {
	        		anyTransaction.initiateTransfer();
	        		System.out.println("Transfer is completed");
	        	}else {

	        		System.out.println("It's nok!");
	        	}
	    		//ATM Func REQ 17
    		}
    		else {
    			System.out.println("Invalid Account!");
    		}    		
    	}else {
    		MyDisplay.display("Wrong input, try again");
    		callStateCHOOSE_TRANSACTION();
    	}    	
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
		MyDisplay.display("You should take your card");
		this.state = ATMstate.EJECTING_CARD;
		callStateIDLE(); //Session completed return to idle
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


	//User triggers this function
	public void userInsertedCard(Card aCard) {
		MyCardReader.insertCard(aCard);
	}
	public boolean checkAvailabilityOfCashInATM() {
		MonetaryAmount current = getCashOnHand();
		System.out.println("Total fund in the ATM "+current);
		if(current.isGreaterThan(Money.of(0, "USD")) ) {
			return true;
		}			
		return false;
	}
	public LocalDateTime checkTime() {		
		return LocalDateTime.now();
	}
	public MonetaryAmount getCashOnHand() {
		return this.MyCashDispenser.getCashOnHand();
	}	
	public OperatorPanel getMyOperatorPanel() {
		return this.MyOperatorPanel;
	}
	public Display getMyDisplay() {
		return this.MyDisplay;
	}	
	public void dispenseCash(MonetaryAmount Amount) {
		MyDisplay.display("Please claim your dispensed money");
		this.MyCashDispenser.dispenseCash(Amount);
	}

}