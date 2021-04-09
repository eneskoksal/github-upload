package com.iyte.bankatm.tek_bankatm;

public class CardReader {
	
	private ATM atm;
	private Card anyCard;
	
	public CardReader(ATM atm) {
		this.atm = atm;
	}

	//This func shall be called by user
	public void insertCard(Card MyCard) {
		this.atm.callStateREADING_CARD();		
		if (MyCard != null) {
			this.anyCard = MyCard;
			this.atm.callStateWAITING_PASSWORD();
		}
		else {
			this.ejectCard();
		}
	}
	
	public Card readCard() {		
		return this.anyCard;
	}	

	public void ejectCard() {
		this.atm.callStateEJECTING_CARD();
	}
	//Card read successfully
	public void retainCard() {
		System.out.println("Card is retained, call the bank");
	}

}