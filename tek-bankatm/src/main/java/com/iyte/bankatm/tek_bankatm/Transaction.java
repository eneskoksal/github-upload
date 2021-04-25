package com.iyte.bankatm.tek_bankatm;
import java.util.ArrayList;

import org.javamoney.moneta.Money;



public class Transaction {

	protected ATM atm;	
	protected Card card;
	protected int pin;
	
	private ArrayList<String> OfferedTransactions;	
	private double Amount; 
	
	private int ToAccountNumber;

	private Account FromAccount;
	private Account ToAccount;
	private TransactionTypes Type;
	
	public Account getFromAccount() {
		return FromAccount;
	}

	public void setFromAccount(Account fromAccount) {
		FromAccount = fromAccount;
	}

	public Account getToAccount() {
		return ToAccount;
	}

	public void setToAccount(Account toAccount) {
		ToAccount = toAccount;
	}


	public void setType(TransactionTypes type) {
		Type = type;
	}
	
	public ArrayList<String> getOfferedTransactions() {
		return this.OfferedTransactions;
	}
	
	public void setOfferedTransaction(String Transaction) {
		this.OfferedTransactions.add(Transaction);
	}

	public Transaction(ATM atm, Card Card) {
		this.OfferedTransactions = new ArrayList<String>();
		this.setOfferedTransaction("Withdrawal");
    	this.setOfferedTransaction("Transfer");
		this.atm = atm;
		this.card = Card;
	}
	

	public void readAmount() {
		this.Amount = atm.getMyDisplay().readAmount();
	}
	
	public double getAmount() {
		return Amount;
	}

	public void setAmount(double amount) {
		Amount = amount;
	}

	public Account readAccountNumber() {		
		this.ToAccountNumber =  atm.getMyDisplay().typedAccountNumber();
		Account ToAccount = this.atm.getMyBank().verifyAccountNumber(this.ToAccountNumber);
		this.setToAccount(ToAccount);
		return ToAccount;
	}
	
	public boolean verify() {
		System.err.println(atm.getMaxWithdrawPerTransaction());
		System.err.println(this.Amount);
		if(atm.getMaxWithdrawPerTransaction() >= this.Amount &&
				atm.getCashOnHand().isGreaterThanOrEqualTo(Money.of(this.Amount, "USD")))
			return true;
		//ATM Func REQ 11
		else if(atm.getMaxWithdrawPerTransaction() < this.Amount) {
			atm.getMyDisplay().display("Too much to withdraw, max allowed: " + atm.getMaxWithdrawPerTransaction());
			return false;
		}
		else{
			atm.getMyDisplay().display("Not enough money in the ATM ");
			return false;
		}
	}

	public void initiateSequence() {		
		//ATM Func REQ 12

		String response = this.atm.getMyBank().verifyTransaction(this.card.getSerialNumber(), Money.of(this.Amount, "USD")); 
		
		//ATM Func REQ 13
		if(response == "transaction succeeded") {
			//ATM Func REQ 14
			this.atm.callStatePRINTING_RECEIPT();
			this.atm.callStateEJECTING_CARD();
			this.atm.dispenseCash(Money.of(this.Amount,"USD"));
			
			new Log().logSend(String.format("Card with %d serial code dispensed %f %s", 
										this.card.getSerialNumber(), this.Amount, "USD"));			
			//ATM Func REQ 15  "Response sent to bank for money dispensed."
			this.atm.getMyBank().updateAccount(this.card.getSerialNumber(), Money.of(Amount, "USD"));			
		}else {
			this.atm.callStateEJECTING_CARD();
			//ATM Func REQ 16
		}		
	}
	
	public void initiateTransfer() {
		this.atm.getMyBank().startTransfer(this.Amount, this.getFromAccount(), this.getToAccount());
	
	}
}