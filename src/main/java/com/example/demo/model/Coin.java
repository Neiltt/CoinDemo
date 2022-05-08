package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "coins")
public class Coin {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "code")
	private String code;

	@Column(name = "symbol")
	private String symbol;

	@Column(name = "rate")
	private String rate;

	@Column(name = "description")
	private String description;
	
	@Column(name = "rate_float")
	private String rate_float;

	public Coin() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Coin(String code, String symbol, String rate, String description, String rate_float) {
		super();
		this.code = code;
		this.symbol = symbol;
		this.rate = rate;
		this.description = description;
		this.rate_float = rate_float;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRate_float() {
		return rate_float;
	}

	public void setRate_float(String rate_float) {
		this.rate_float = rate_float;
	}

	@Override
	public String toString() {
		return "Coin [id=" + id + ", code=" + code + ", symbol=" + symbol + ", rate=" + rate + ", description="
				+ description + ", rate_float=" + rate_float + "]";
	}

}
