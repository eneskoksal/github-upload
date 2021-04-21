package com.iyte.bankatm.tek_bankatm;
import java.util.ArrayList;
import java.util.Scanner;
import javax.money.MonetaryAmount;

import org.javamoney.moneta.Money;



public class Transaction {

	protected ATM atm;
	protected Session session;
	protected Card card;
	protected int pin;
	
	private ArrayList<String> OfferedTransactions;
	private Scanner MyKeyboard;
	private double Amount; //will be replaced
	private Account Account;
	public Account getAccount() {
		return Account;
	}

	public void setAccount(Account account) {
		Account = account;
	}


	private int ToAccountNumber;
	private TransactionTypes Type;
	public TransactionTypes getType() {
		return Type;
	}

	public void setType(TransactionTypes type) {
		Type = type;
	}

	public Transaction(ATM atm, Card Card) {
		this.OfferedTransactions = new ArrayList<String>();
		this.MyKeyboard = new Scanner(System.in);
		this.atm = atm;
		this.card = Card;
	}
	
	public ArrayList getOfferedTransactions() {
		return this.OfferedTransactions;
	}
	
	public void setOfferedTransaction(String Transaction) {
		this.OfferedTransactions.add(Transaction);
	}
	
	public void ListOfferedTransaction() {
		for(int index = 0; index < this.OfferedTransactions.size(); index++) {
			System.out.println(this.OfferedTransactions.get(index));
		}		
	}
	public void readAmount() {
		System.out.println("Enter the amount");
		this.Amount =  Double.parseDouble(MyKeyboard.nextLine());
	}
	
	public Account readAccountNumber() {
		System.out.println("Enter the Account Number");
		this.ToAccountNumber =  Integer.parseInt(MyKeyboard.nextLine());
		return this.atm.getMyBank().verifyAccountNumber(this.ToAccountNumber);
	}
	public boolean verify() {
		if(atm.getMaxWithdrawPerTransaction()  > this.Amount)
			return true;
		//ATM Func REQ 11
		System.out.println("--- " + atm.getMaxWithdrawPerTransaction());
		return false;
		
	}

	public boolean initiateSequence() {
		if(atm.getMaxWithdrawPerTransaction() < this.Amount) {
			System.out.println("Too much to withdraw");
			return false;
		}
		//ATM Func REQ 12

		String response = this.atm.getMyBank().verifyTransaction(this.Account, Money.of(this.Amount, "USD")); 
		
		//ATM Func REQ 13
		if(response == "transaction succeeded") {
			this.atm.callStatePRINTING_RECEIPT();
			this.atm.dispenseCash(Money.of(this.Amount,"USD"));
			this.atm.callStateEJECTING_CARD();
			//ATM Func REQ 14

			new Log().logSend(String.format("Card with %d serial code dispensed %f %s", this.card.getSerialNumber(), this.Amount, "USD"));
			//ATM Func REQ 15  "Response sent to bank for money dispensed." ???
			

			// 
		}else {
			System.err.println(response);
			this.atm.callStateEJECTING_CARD();
			//ATM Func REQ 16
		}
		return true;
		
	}

	
	public void initiateTransfer() {
		this.atm.getMyBank().startTransfer(this.Amount);
		new Log().logSend(String.format("Transfer from %d to %d with Amount %f %s", this.Account.getAccount_number(), this.ToAccountNumber, this.Amount, "USD"));
		
		
	}
}