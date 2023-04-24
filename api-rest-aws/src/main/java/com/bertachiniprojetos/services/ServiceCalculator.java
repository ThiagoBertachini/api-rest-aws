package com.bertachiniprojetos.services;

import org.springframework.stereotype.Service;

@Service
public class ServiceCalculator {
	
	public Double sum(Double numberOne, Double numberTwo) {
		return numberOne + numberTwo;
	}
	
	public Double sub(Double numberOne, Double numberTwo) {
		return numberOne - numberTwo;
	}
	
	public Double div(Double numberOne, Double numberTwo) {
		return numberOne / numberTwo;
	}
	
	public Double multi(Double numberOne, Double numberTwo) {
		return numberOne * numberTwo;
	}
	
	public Double avarage(Double numberOne, Double numberTwo) {
		return numberOne + numberTwo / 2;
	}
	
	public Double squareRoot(Double number) {
		return Math.sqrt(number);
	}
	
	
	

}
