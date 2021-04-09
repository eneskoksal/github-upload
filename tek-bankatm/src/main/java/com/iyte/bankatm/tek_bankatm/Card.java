package com.iyte.bankatm.tek_bankatm;

import java.time.LocalDate;

public class Card {

	private int number;
	private LocalDate expireDate;

	/**
	 * 
	 * @param number
	 */
	public Card(int number, LocalDate expireDate) {
		this.number = number;
		this.expireDate = expireDate;				
	}

	public int getNumber() {
		return this.number;
	}
	
	public LocalDate getExpireDate() {
		return this.expireDate;
	}

}