package com.iyte.bankatm.tek_bankatm;

import java.time.LocalDate;

import javax.money.MonetaryAmount;

public class Bank {
	private DatabaseProxy MyDatabaseProxy;
	public DatabaseProxy getMyDatabaseProxy() {
		return MyDatabaseProxy;
	}

	private ATM MyATM;
	
	public Bank() {
		this.MyDatabaseProxy = new DatabaseProxy();
		this.MyATM = new ATM(this);		
	}
	
	public String verifyRequest(String password, int cardSerialNumber) {
		Account theAccount = MyDatabaseProxy.selectAccountByCardSerialNo(cardSerialNumber);
		if(theAccount != null) {
			String expectedPassword = theAccount.getPassword();			
			//Bank Func REQ 5
			if(expectedPassword == null) { //No password is initialized
				return "bad account";
			//Bank Func REQ 3 & 4 & 6
			}else if(expectedPassword.equals(password)) {
				MyDatabaseProxy.setWrongPasswordCount(cardSerialNumber, 0);
				return "account ok";
			}else {
				int wrongAttempt = MyDatabaseProxy.getWrongPasswordCount(cardSerialNumber);				
				if(wrongAttempt >= 3) { //In fourth wrong attempt(index = 3) keep the card
					return "keep the card";
				}else {					
					MyDatabaseProxy.setWrongPasswordCount(cardSerialNumber, ++wrongAttempt);
					return "bad password";
				}				
			}			
		}
		//Bank Func REQ 1 & 2
		//Account which is connected with this card serial number has not found 
		return "bad bank code";
	}
	
	public String verifyTransaction(int cardSerialNumber, MonetaryAmount Amount ) {
		Account findAccount = MyDatabaseProxy.selectAccountByCardSerialNo(cardSerialNumber);
		MonetaryAmount Balance = findAccount.getBalance();		
		if(Balance.isGreaterThanOrEqualTo(Amount) && 
				findAccount.getLeftMaxWithdrawPerDay().isGreaterThanOrEqualTo(Amount))
		{			
			return "transaction succeeded";
		}
		else {
			if(Balance.isLessThan(Amount))
				MyATM.getMyDisplay().display("Balance is not enough: " + Balance);
			if(findAccount.getLeftMaxWithdrawPerDay().isLessThan(Amount))
				MyATM.getMyDisplay().display("Max withdraw limit is exceeded: " + findAccount.getLeftMaxWithdrawPerDay());
			return "transaction failed"; // Bank Func REQ 7
		}
	}
	
	public void updateAccount(int cardSerialNumber, MonetaryAmount Amount) {		
		MyDatabaseProxy.minusBalance(cardSerialNumber, Amount); // Bank Func REQ 8
		MyDatabaseProxy.setLeftMaxWithdrawPerDay(cardSerialNumber, Amount);  // Bank Func REQ 9
		MyATM.getMyDisplay().display("New Balance: " + MyDatabaseProxy.selectAccountByCardSerialNo(cardSerialNumber).getBalance());
	}
	
	public Account createNewAccount(int account_number,String password, int accountType, int serialNumber, LocalDate expireDate){
		Account newAccount = new Account(this.MyATM.getMaxWithdrawPerDayAccount());
		newAccount.setAccount_number(account_number);
		newAccount.setAccountType(accountType);
		newAccount.setMyCard(new Card(serialNumber, expireDate));		
		newAccount.setPassword(password);
		MyDatabaseProxy.createNewAccount(newAccount);
		return newAccount;
	}
	
	public ATM getMyATM() {
		return this.MyATM;
	}

	public Account verifyAccountNumber(int accountNumber) {
		// TODO Auto-generated method stub
		return this.MyDatabaseProxy.selectAccountByAccountNumber(accountNumber);
	}

	public void startTransfer(double Amount) {
		// TODO Auto-generated method stub
		
	}
}