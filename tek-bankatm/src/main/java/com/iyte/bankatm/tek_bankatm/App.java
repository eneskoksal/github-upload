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
        ATM anyATM = new ATM();
        OperatorPanel anyOperatorPanel = new OperatorPanel(anyATM);
        anyOperatorPanel.initializeATM(Money.of(1234567.98, "USD"), 20, 2000, 3500);
        MonetaryAmount initFund = anyOperatorPanel.getInitialCash();
        CurrencyUnit currency = initFund.getCurrency();
        NumberValue numberValue = initFund.getNumber();        
        System.out.println("Amount of money in atm: " + numberValue + " " + currency);
        anyATM.userInsertedCard(new Card(33333, LocalDate.of(2020, 1, 8)));        
        
    }
}
