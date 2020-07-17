package com.db.awmd.challenge.service;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.FundTransfer;
import com.db.awmd.challenge.exception.NoAccountFoundException;
import com.db.awmd.challenge.exception.NotEnoughBalanceException;

interface IMoneyTransfer {

    void fundTransfer(final Account accountFrom, final Account accountTo, final FundTransfer transfer) 
    		throws NoAccountFoundException, NotEnoughBalanceException;

}
