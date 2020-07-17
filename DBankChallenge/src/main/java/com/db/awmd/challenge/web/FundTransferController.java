package com.db.awmd.challenge.web;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.FundTransfer;
import com.db.awmd.challenge.exception.NoAccountFoundException;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;
import com.db.awmd.challenge.exception.NotEnoughBalanceException;
import com.db.awmd.challenge.exception.SameAccountFundTransferException;
import com.db.awmd.challenge.service.AccountsService;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1.0/accounts")
@Slf4j
public class FundTransferController {

	private final AccountsService accountsService;

	@Autowired
	public FundTransferController(AccountsService accountsService) {
		this.accountsService = accountsService;
	}

	@PostMapping(path = "/fundtransfer", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> doTransfer(@RequestBody @Valid FundTransfer transfer) {
		log.info("Fund transfer ...initiated...from Account Service..", transfer);

		try {
			this.accountsService.doTransfer(transfer);
		} catch (NoAccountFoundException ane) {
			return new ResponseEntity<>(ane.getMessage(), HttpStatus.NOT_FOUND);
		} catch (SameAccountFundTransferException ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

}
