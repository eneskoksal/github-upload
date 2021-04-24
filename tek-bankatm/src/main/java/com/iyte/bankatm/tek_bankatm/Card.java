package com.iyte.bankatm.tek_bankatm;

import java.time.LocalDate;

public class Card {

	private int serialNumber;
	private LocalDate expireDate;
	
	public void setExpireDate(LocalDate expireDate) {
		this.expireDate = expireDate;
	}

	public Card(int serialNumber, LocalDate expireDate) {
		this.serialNumber = serialNumber;
		this.expireDate = expireDate;				
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	public int getSerialNumber() {
		return this.serialNumber;
	}
		
	public LocalDate getExpireDate() {
		return this.expireDate;
	}

}