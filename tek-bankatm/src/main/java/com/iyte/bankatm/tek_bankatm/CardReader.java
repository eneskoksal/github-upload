package com.iyte.bankatm.tek_bankatm;

public class CardReader {
	
	private ATM atm;
	private Card anyCard;
	
	public CardReader(ATM atm) {
		this.atm = atm;
	}

	//This func shall be called by user
	public void insertCard(Card MyCard) {
		this.atm.setNextState(FSM.READING_CARD);		
		if (MyCard != null) {
			this.anyCard = MyCard;
			this.atm.setNextState(FSM.CARD_READ_SUCCESS);
		}
		else {
			this.ejectCard();
		}
	}
	
	public Card readCard() {		
		return this.anyCard;
	}	

	public void ejectCard() {
		this.atm.setNextState(FSM.EJECTING_CARD);
	}
	//Card read successfully
	public void retainCard() {
		this.atm.setNextState(FSM.WAITING_PASSWORD);
	}

}