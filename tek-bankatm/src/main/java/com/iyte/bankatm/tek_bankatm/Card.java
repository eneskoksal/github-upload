package com.iyte.bankatm.tek_bankatm;

import java.time.LocalDate;

public class Card {

	private int serialNumber;
	private LocalDate expireDate;
	
	public Card(int serialNumber, LocalDate expireDate) {
		this.serialNumber = serialNumber;
		this.expireDate = expireDate;				
	}

	public int getSerialNumber() {
		return this.serialNumber;
	}
		
	public LocalDate getExpireDate() {
		return this.expireDate;
	}

}