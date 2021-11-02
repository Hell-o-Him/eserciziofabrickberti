package com.fabrick.exercise;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.fabrick.exercise.model.Account;
import com.fabrick.exercise.model.TransferResponse;


@RestController
public class RestFabrickController {
	
	private final static String ACCOUNTID = "" + 14537780;
	
	@GetMapping(value = "/balance")
	public ResponseEntity<String> getBalance(Model model, @RequestParam(value = "accountId", required = true, defaultValue = ACCOUNTID) Long id,
			@RequestHeader HttpHeaders headers) {
		String url = "https://sandbox.fabrick.com/api/gbs/banking/v4.0/accounts/" + id + "/balance";
		RestTemplate rt = new RestTemplate();
		model.addAllAttributes(headers);
		model.addAttribute("accountId", id);
		Account account = rt.getForObject(url, Account.class, headers);
		return new ResponseEntity<String>(Float.toString(account.getBalance()), HttpStatus.OK);
	}
	
	@PostMapping(value = "/transfer")
	public ResponseEntity<String> doTransfer(Model model, @RequestHeader HttpHeaders headers, 
			@RequestParam(value = "accountId", required = true, defaultValue = ACCOUNTID) Long id,
			@RequestParam(value = "receiverName", required = true) String receiverName,
			@RequestParam(value = "description", required = true) String description,
			@RequestParam(value = "currency", required = true) String currency,
			@RequestParam(value = "amount", required = true) String amount,
			@RequestParam(value = "executionDate", required = true) Date executionDate) {
		String url = "/api/gbs/banking/v4.0/accounts/" + id + "/payments/money-transfers";
		RestTemplate rt = new RestTemplate();
		model.addAllAttributes(headers);
		model.addAttribute("accountId", id);
		model.addAttribute("receiverName", receiverName);
		model.addAttribute("description", description);
		model.addAttribute("currency", currency);
		model.addAttribute("amount", amount);
		model.addAttribute("executionDate", executionDate);
		return new ResponseEntity<String>(rt.getForObject(url, Object.class, headers).toString(), HttpStatus.OK);
	}
	
	@GetMapping(value ="/movements")
	public List<Object> getAccounts(Model model, @RequestHeader HttpHeaders headers, 
			@RequestParam(value = "accountId", required = true, defaultValue = ACCOUNTID) Long id,
			@RequestParam(value = "fromAccountingDate", required = true) Date fromAccountingDate,
			@RequestParam(value = "toAccountingDate", required = true) Date toAccountingDate) {
		String url = "https://sandbox.fabrick.com/api/gbs/banking/v4.0/accounts/" + id + "/transactions [?<uriQuery>]";
		RestTemplate rt = new RestTemplate();
		model.addAttribute("accountId", id);
		model.addAttribute("fromAccountingDate", fromAccountingDate);
		model.addAttribute("toAccountingDate", toAccountingDate);
		Object[] transactions = rt.getForObject(url, Object[].class);
		return Arrays.asList(transactions);
	}

}
