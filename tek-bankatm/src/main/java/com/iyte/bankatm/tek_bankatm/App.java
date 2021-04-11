package com.iyte.bankatm.tek_bankatm;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.NumberValue;
import java.time.LocalDate;

import org.javamoney.moneta.Money;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {      
        Bank EnesBank = new Bank();
        ATM theATM = EnesBank.getMyATM();
        OperatorPanel theOP = theATM.getMyOperatorPanel();
        
        EnesBank.createNewAccount(1234, "123", 0, 33333, LocalDate.of(2022, 1, 8));
        
        theOP.initializeATM(Money.of(1234567.98, "USD"), 20, 2000, 3500);
        MonetaryAmount initFund = theOP.getInitialCash();
        System.out.println("Amount of money in atm: " + initFund);        
        theATM.userInsertedCard(new Card(33333, LocalDate.of(2022, 1, 8)));
        
    }
}
