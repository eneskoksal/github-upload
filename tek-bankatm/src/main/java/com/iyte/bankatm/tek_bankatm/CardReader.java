package com.iyte.bankatm.tek_bankatm;

public class CardReader {

	private ATM atm;

	public CardReader(ATM atm) {
		this.atm = atm;
	}
	//This func shall be called by user
	public void insertCard(Card MyCard) {
		Card insertedCard = this.readCard(MyCard);
		if (insertedCard != null) {
			this.atm.getCardInfo(MyCard);
			this.retainCard();
		}
		else {
			this.ejectCard();
		}
	}
	
	public Card readCard(Card MyCard) {		
		return MyCard;
	}	

	public void ejectCard() {
		// TODO - implement CardReader.ejectCard
		throw new UnsupportedOperationException();
	}

	public void retainCard() {
		// TODO - implement CardReader.retainCard
		throw new UnsupportedOperationException();
	}

}