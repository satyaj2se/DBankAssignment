package com.db.awmd.challenge.service;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.FundTransfer;
import com.db.awmd.challenge.exception.NoAccountFoundException;
import com.db.awmd.challenge.exception.NotEnoughBalanceException;
import com.db.awmd.challenge.exception.SameAccountFundTransferException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class IMoneyTransferImplValidation implements IMoneyTransfer {

	public void fundTransfer(final Account currAccountFrom, final Account currAccountTo, final FundTransfer transfer)
			throws NoAccountFoundException, NotEnoughBalanceException, SameAccountFundTransferException {

		if (currAccountFrom == null) {
			throw new NoAccountFoundException("Account " + transfer.getAccountFromId() + " not found.");
		}

		if (currAccountTo == null) {
			throw new NoAccountFoundException("Account " + transfer.getAccountToId() + " not found.");
		}

		if (sameAccountIDCheck(transfer)) {
			throw new SameAccountFundTransferException("Transfer to self not permisable.");
		}

		if (!balanceCheck(currAccountFrom, transfer.getAmount())) {
			throw new NotEnoughBalanceException("No sufficient Balance to transfer account " + currAccountFrom.getAccountId()
					+ " balance=" + currAccountFrom.getBalance());
		}
	}

	private boolean sameAccountIDCheck(final FundTransfer transfer) {
		return transfer.getAccountFromId().equals(transfer.getAccountToId());
	}

	private boolean balanceCheck(final Account account, final BigDecimal amount) {
		return account.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) >= 0;
	}

}
