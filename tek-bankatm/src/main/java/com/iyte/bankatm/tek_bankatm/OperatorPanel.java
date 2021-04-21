package com.iyte.bankatm.tek_bankatm;

import javax.money.MonetaryAmount;

public class OperatorPanel {

	private ATM atm;
	/**
	 * 
	 * @param atm
	 */
	public OperatorPanel(ATM atm) {
		this.atm = atm;		
	}

	public MonetaryAmount getInitialCash() {
		return this.atm.getCashOnHand();		
	}
	//ATM Func. REQ 1
	public void initializeATM(MonetaryAmount initAmount, int minWithdrawPerTransaction, 
			int maxWithdrawPerTransaction, MonetaryAmount maxWithdrawPerDayAccount) {		
		this.atm.setInitialParameters(initAmount, minWithdrawPerTransaction, 
				maxWithdrawPerTransaction, maxWithdrawPerDayAccount);
		this.atm.callStateIDLE();		
	}

}