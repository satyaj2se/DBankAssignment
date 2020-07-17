package com.db.awmd.challenge.service;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.UpdateAccount;
import com.db.awmd.challenge.domain.FundTransfer;
import com.db.awmd.challenge.exception.NoAccountFoundException;
import com.db.awmd.challenge.exception.NotEnoughBalanceException;
import com.db.awmd.challenge.exception.SameAccountFundTransferException;
import com.db.awmd.challenge.repository.AccountsRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;

//Can act as on all Banking Account Services
@Service
public class AccountsService {

	@Getter
	private final AccountsRepository accountsRepository;

	@Getter
	private final NotificationService notificationService;

	@Autowired
	private IMoneyTransfer iMoneyTransfer;

	@Autowired
	public AccountsService(AccountsRepository accountsRepository, NotificationService notificationService) {
		this.accountsRepository = accountsRepository;
		this.notificationService = notificationService;
	}

	public void createAccount(Account account) {
		this.accountsRepository.createAccount(account);
	}

	public Account getAccount(String accountId) {
		return this.accountsRepository.getAccount(accountId);
	}

	/**
	 * Added functionality for the fund-transfer
	 */
	public void doTransfer(FundTransfer transfer)
			throws NoAccountFoundException, NotEnoughBalanceException, SameAccountFundTransferException {

		final Account accountFrom = accountsRepository.getAccount(transfer.getAccountFromId());
		final Account accountTo = accountsRepository.getAccount(transfer.getAccountToId());
		final BigDecimal amount = transfer.getAmount();

		iMoneyTransfer.fundTransfer(accountFrom, accountTo, transfer);

		boolean successful = accountsRepository
				.updateAccounts(Arrays.asList(new UpdateAccount(accountFrom.getAccountId(), amount.negate()),
						new UpdateAccount(accountTo.getAccountId(), amount)));

		if (successful) {
			notificationService.notifyAboutTransfer(accountFrom, "The transfer to the account...From "
					+ accountTo.getAccountId() + " is now complete for the amount of " + transfer.getAmount() + ".");
			notificationService.notifyAboutTransfer(accountTo, "Amount to Transferred...To + "
					+ accountFrom.getAccountId() + " has transferred " + transfer.getAmount() + " into your account.");
		}
	}

}
