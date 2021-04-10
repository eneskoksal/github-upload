package com.iyte.bankatm.tek_bankatm;

public class CardReader {
	
	private ATM atm;
	private Card anyCard;
	
	public CardReader(ATM atm) {
		this.atm = atm;
	}

	//This func shall be called by user
	public void insertCard(Card MyCard) {
		this.anyCard = MyCard;
		this.atm.callStateREADING_CARD();	//inform atm computer that a card is read		
	}
	
	public Card readCard() {		
		return this.anyCard;
	}	

	public void ejectCard() {
		//Card is ejected
	}
	//Card read successfully
	public void retainCard() {
		//Card is retained, call the bank"
	}

}