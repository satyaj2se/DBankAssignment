package com.db.awmd.challenge.service;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.FundTransfer;
import com.db.awmd.challenge.service.AccountsService;
import com.db.awmd.challenge.exception.NoAccountFoundException;
import com.db.awmd.challenge.exception.NotEnoughBalanceException;
import com.db.awmd.challenge.exception.SameAccountFundTransferException;
import com.db.awmd.challenge.service.NotificationService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verifyZeroInteractions;

public class FundTransferControllerTest {

	private IMoneyTransfer iMoneyTransfer;

	@Autowired
	private AccountsService accountsService;

	@Autowired
	private NotificationService notificationService;

	@Before
	public void setUp() {
		iMoneyTransfer = new IMoneyTransferImplValidation();
	}

	
	//Valid Account to Account transfer test case execution
	@Test
	public void validTransferBetweenAccounts() throws Exception {
		final Account accountFrom = new Account("Axc007", new BigDecimal("400.00"));
		final Account accountTo = new Account("Axc009");
		final FundTransfer transfer = new FundTransfer("Axc007", "Axc009", new BigDecimal("200.00"));

		iMoneyTransfer.fundTransfer(accountFrom, accountTo, transfer);
	}
	
	
	//Not a Valid or null negative test execution
	@Test
	public void notValidAccountFoundToTransferException() throws Exception {
		final Account accountTo = new Account("ACC_2"); // transfer from Acc 1 to Acc 2
		final FundTransfer transfer = new FundTransfer("From_ACC_1", accountTo.getAccountId(), new BigDecimal("2.00"));

		try {
			iMoneyTransfer.fundTransfer(null, new Account("ACC_2"), transfer);
			fail("Account with From_ACC_1 should not be found");
		} catch (NoAccountFoundException ace) {
			assertThat(ace.getMessage()).isEqualTo("Account From_ACC_1 not found.");
		}
	}

	//If Account is not available test case execution..
	@Test
	public void testNoExistingAccountFound() throws Exception {

		/*
		 * Trying to send money from ACC_008 to ACC_00X1 the case is if ACC_00X1 is not
		 * available for the transfer.
		 */
		final Account accountFrom = new Account("ACC_008");
		final FundTransfer transfer = new FundTransfer("ACC_008", "ACC_00X1", new BigDecimal("2.00"));

		try {
			iMoneyTransfer.fundTransfer(accountFrom, null, transfer);
			fail("Account with ACC_00X1 not be found");
		} catch (NoAccountFoundException ace) {
			assertThat(ace.getMessage()).isEqualTo("Account ACC_00X1 not found.");
		}
	}

	@Test
	public void testNotEnoughFunds() throws Exception {
		final Account accountFrom = new Account("Axc001");
		final Account accountTo = new Account("Axc002");
		final FundTransfer transfer = new FundTransfer("Axc001", "Axc002", new BigDecimal("2.00"));

		try {
			iMoneyTransfer.fundTransfer(accountFrom, accountTo, transfer);
			fail("Not enough funds");
		} catch (NotEnoughBalanceException nbe) {
			assertThat(nbe.getMessage()).isEqualTo("No sufficient Balance to transfer account Axc001 balance=0");
		}
	}

	//Not in the same account tranfer test case execution 
	@Test
	public void excpetionWhenTransferredBetweenSameAccount() throws Exception {
		final Account accountFrom = new Account("Axc001", new BigDecimal("2.00"));
		final Account accountTo = new Account("Axc001");
		final FundTransfer transfer = new FundTransfer("Axc001", "Axc001", new BigDecimal("2.00"));

		try {
			iMoneyTransfer.fundTransfer(accountFrom, accountTo, transfer);
			fail("Same account transfer currently not available");
		} catch (SameAccountFundTransferException sae) {
			assertThat(sae.getMessage()).isEqualTo("Transfer to self not permisable.");
		}
	}

}