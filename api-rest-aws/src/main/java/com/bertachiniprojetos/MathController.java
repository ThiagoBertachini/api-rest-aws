package com.bertachiniprojetos;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.bertachiniprojetos.exceptions.UnsuportedMathOperationException;
import com.bertachiniprojetos.services.ServiceCalculator;
import com.bertachiniprojetos.utils.NumberValidator;

@RestController
public class MathController {
	
	@Autowired
	private ServiceCalculator serviceCalculator;

	@GetMapping(value = "/sum/{numberOne}/{numberTwo}")
	public Double sum(@PathVariable(value = "numberOne") String numberOne,
						@PathVariable(value = "numberTwo") String numberTwo) throws Exception {
		if(!NumberValidator.isNumeric(numberOne, numberTwo)) {
			throw new UnsuportedMathOperationException("Set numeric values");
		}
		return serviceCalculator.sum(NumberValidator.strToDouble(numberOne), 
				NumberValidator.strToDouble(numberTwo));
	}
	
	@GetMapping(value = "/sub/{numberOne}/{numberTwo}")
	public Double sub(@PathVariable(value = "numberOne") String numberOne,
						@PathVariable(value = "numberTwo") String numberTwo) throws Exception {
		if(!NumberValidator.isNumeric(numberOne, numberTwo)) {
			throw new UnsuportedMathOperationException("Set numeric values");
		}
		return serviceCalculator.sub(NumberValidator.strToDouble(numberOne), 
				NumberValidator.strToDouble(numberTwo));
	}
	
	@GetMapping(value = "/div/{numberOne}/{numberTwo}")
	public Double div(@PathVariable(value = "numberOne") String numberOne,
						@PathVariable(value = "numberTwo") String numberTwo) throws Exception {
		if(!NumberValidator.isNumeric(numberOne, numberTwo)) {
			throw new UnsuportedMathOperationException("Set numeric values");
		}
		return serviceCalculator.div(NumberValidator.strToDouble(numberOne), 
				NumberValidator.strToDouble(numberTwo));
	}
	
	@GetMapping(value = "/multi/{numberOne}/{numberTwo}")
	public Double multi(@PathVariable(value = "numberOne") String numberOne,
						@PathVariable(value = "numberTwo") String numberTwo) throws Exception {
		if(!NumberValidator.isNumeric(numberOne, numberTwo)) {
			throw new UnsuportedMathOperationException("Set numeric values");
		}
		return serviceCalculator.multi(NumberValidator.strToDouble(numberOne), 
				NumberValidator.strToDouble(numberTwo));
	}
	
	@GetMapping(value = "/avg/{numberOne}/{numberTwo}")
	public Double average(@PathVariable(value = "numberOne") String numberOne,
						@PathVariable(value = "numberTwo") String numberTwo) throws Exception {
		if(!NumberValidator.isNumeric(numberOne, numberTwo)) {
			throw new UnsuportedMathOperationException("Set numeric values");
		}
		return serviceCalculator.avarage(NumberValidator.strToDouble(numberOne), 
				NumberValidator.strToDouble(numberTwo));
	}
	
	@GetMapping(value = "/sqrRoot/{number}")
	public Double squareRoot(@PathVariable(value = "number") String number) throws Exception {
		if(!NumberValidator.isNumeric(number)) {
			throw new UnsuportedMathOperationException("Set numeric values");
		}
		return serviceCalculator.squareRoot(NumberValidator.strToDouble(number));
	}
}
