package com.bertachiniprojetos.utils;

import java.util.Objects;

public class NumberValidator {
	
	public static boolean isNumeric(String numberOne, String numberTwo) {
		if(Objects.isNull(numberOne) || Objects.isNull(numberTwo)) {
			return false;
		}
		String numOne = numberOne.replaceFirst(",", ".");
		String numTwo = numberTwo.replaceFirst(",", ".");
		return numOne.matches("[-+]?[0-9]*\\.?[0-9]+") 
				&& numTwo.matches("[-+]?[0-9]*\\.?[0-9]+");
	}
	
	public static boolean isNumeric(String number) {
		if(Objects.isNull(number)) {
			return false;
		}
		String num = number.replaceFirst(",", ".");
		return num.matches("[-+]?[0-9]*\\.?[0-9]+");
	}
	
	public static Double strToDouble(String strNumber) {
		if(Objects.isNull(strNumber)) return 0D;
		String number = strNumber.replaceAll(",", ".");
		return Double.parseDouble(number);
	}
	
}
