package com.my.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="quotes")
public class QuoteEntity {
	@Id
	private String Quote;
	private String status;

	public String getQuote() {
		return Quote;
	}

	public void setQuote(String quote) {
		Quote = quote;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
