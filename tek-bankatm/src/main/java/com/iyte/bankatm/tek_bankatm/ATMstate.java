package com.iyte.bankatm.tek_bankatm;

public enum ATMstate {
	OFF,
	IDLE,
	READING_CARD,
	WAITING_PASSWORD,	
	CHOOSE_TRANSACTION,
	PERFORMING_TRANSACTION,
	PRINTING_RECEIPT,
	EJECTING_CARD,		
	RETAINING_CARD, 
	FailedTransfer,		
}