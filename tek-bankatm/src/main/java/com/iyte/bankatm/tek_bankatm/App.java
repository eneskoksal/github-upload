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
        MonetaryAmount maxWithdrawPerDayAccount = Money.of(4000, "USD");
        theOP.initializeATM(Money.of(1234567.98, "USD"), 20, 20000, maxWithdrawPerDayAccount);
        Account MainAccount = EnesBank.createNewAccount(1234, "123", 0, 33333, LocalDate.of(2022, 1, 8));
        Account SecondaryAccount = EnesBank.createNewAccount(4321, "122", 0, 33333, LocalDate.of(2022, 1, 8));
        MainAccount.setBalance(Money.of(1500, "USD"));
        MonetaryAmount initFund = theOP.getInitialCash();
        System.out.println("Amount of money in atm: " + initFund);        
        CurrencyUnit currency = initFund.getCurrency();
        NumberValue numberValue = initFund.getNumber();        
        System.out.println("Amount of money in atm: " + numberValue + " " + currency);
        Card theCard = new Card(33333, LocalDate.of(2022, 1, 8));
        theATM.userInsertedCard(theCard);    
        
    }
}
