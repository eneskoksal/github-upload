package com.iyte.bankatm.tek_bankatm;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.NumberValue;
import java.time.LocalDate;
import java.util.Scanner;

import org.javamoney.moneta.Money;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
    	Bank EnesBank = new Bank();
        ATM theATM = EnesBank.getMyATM();
        OperatorPanel theOP = theATM.getMyOperatorPanel();
        EnesBank.createNewAccount(1234, "123", 0, 33333, LocalDate.of(2022, 1, 8));
        EnesBank.createNewAccount(4321, "122", 0, 33333, LocalDate.of(2022, 1, 8));
        theOP.initializeATM(Money.of(1234567.98, "USD"), 20, 2000, 3500);
        MonetaryAmount initFund = theOP.getInitialCash();
        System.out.println("Amount of money in atm: " + initFund);        
        CurrencyUnit currency = initFund.getCurrency();
        NumberValue numberValue = initFund.getNumber();        
        System.out.println("Amount of money in atm: " + numberValue + " " + currency);
        Card theCard = new Card(33333, LocalDate.of(2022, 1, 8));
        theATM.userInsertedCard(theCard);    
        if(theATM.getState() == ATMstate.CHOOSE_TRANSACTION) {
        	Transaction anyTransaction = new Transaction(theATM, theCard);
        	anyTransaction.setOfferedTransaction("Withdrawal");
        	anyTransaction.setOfferedTransaction("Transfer");
        	anyTransaction.ListOfferedTransaction();
        	Scanner ChooseTransaction = new Scanner(System.in);
        	if(ChooseTransaction.nextLine() == "w"){
	        	anyTransaction.readAmount();
	        	Boolean IsVerifed = anyTransaction.verify();
	        	if(IsVerifed) {
	        		System.out.println("It's ok!");
	        		anyTransaction.initiateSequence();
	        	}else{
	        		System.out.println("It's nok!");
	        		
	        	}
        	}else {
        		Account toTransefer = anyTransaction.readAccountNumber();
        		if(toTransefer != null) {
    	        	anyTransaction.readAmount();;
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
        		
        		
        	}
        }
    }
}
