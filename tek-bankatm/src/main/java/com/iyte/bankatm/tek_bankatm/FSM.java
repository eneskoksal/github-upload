package com.iyte.bankatm.tek_bankatm;

public class FSM
{
	public enum ATMstate {
		OFF,
		IDLE,
		READING_CARD,
		WAITING_PASSWORD,	
		CHOOSE_TRANSACTION,
		PERFORMING_TRANSACTION,
		PRINTING_RECEIPT,
		EJECTING_CARD		
	}
	public enum Events {
		TURN_ON,
		TURN_OFF,
		CARD_INSERTED,
		CARD_NOT_VALID,
		CARD_READ_SUCCESS,
		CANCEL_PRESSED,
		PASSWORD_CORRECT,
		TRANSACTION_CHOOSEN,
		SESSION_COMPLETED_W_PRINT,
		SESSION_COMPLETED,
		SESSION_DISMISSED	
	}
	public ATMstate[/*State*/][/*Event*/] transitionTable = {
		{ATMstate.IDLE,		null,null,null,null,null},
		{},
		{},
		{},
		{},
		{},
		{},
		{}
		
	};
}