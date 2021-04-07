package com.iyte.bankatm.tek_bankatm;

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
        anyOperatorPanel.initializeATM(Money.of(1234567.89, "USD"), 20, 2000, 3500);
        
        while(true) {
        	anyATM.nextState();
        }
    }
}
