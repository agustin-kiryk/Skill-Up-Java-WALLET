package com.alkemy.wallet.controller;


import com.alkemy.wallet.dto.AccountDto;
import com.alkemy.wallet.dto.TransactionDto;

import com.alkemy.wallet.enumeration.TypeTransaction;

import com.alkemy.wallet.service.IAccountService;
import com.alkemy.wallet.service.ITransactionService;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/transactions")
public class TransactionController {

  @Autowired
  RestExceptionHandler restExceptionHandler;
  @Autowired
  private ITransactionService transactionService;

  @GetMapping("/{userId}")
  public ResponseEntity<List<TransactionDto>> getTransactionsById(@PathVariable Long userId) {
    List<TransactionDto> transactionsList = transactionService.transactionsById(userId);
    return ResponseEntity.ok().body(transactionsList);

  }

  @PostMapping("/payment")
  public ResponseEntity<TransactionDto> makeAPayment(@RequestBody TransactionDto dto) {

    dto.setType(TypeTransaction.PAYMENT);
    TransactionDto transactionDto = transactionService.createTransaction(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(transactionDto);
  }

  @GetMapping("/{transactionId}")

  public ResponseEntity<TransactionDto> getDetailTransaction(@PathVariable Long transactionId) {
    TransactionDto transaction = this.transactionService.getDetailById(transactionId);
    return ResponseEntity.ok(transaction);
  }

  @GetMapping
  @PostMapping("/transactions/sendArs")
  public String moneySendInPesos(@Valid @RequestParam("idUser") Long idUser,
      @RequestParam("TYPE_TRANSACTION") Long typeTransaction,
      @RequestParam("AMOUNT") Double amount) {
    return transactionService.moneySendInPesos(idUser, typeTransaction, amount);
  }

  @PostMapping("/transactions/sendUsd")
  public String moneySendInUsd(@RequestParam("idUser") Long idUser,
      @RequestParam("TYPE_TRANSACTION") Long typeTransaction,
      @RequestParam("AMOUNT") Double amount) {
    return transactionService.moneySendInUsd(idUser, typeTransaction, amount);

  }

  @GetMapping("/account/balance/{idUser}")
  public ResponseEntity<AccountDto> getAccountBalance(@PathVariable("idUser") Long idUser) {
    return new ResponseEntity<AccountDto>((AccountDto) IAccountService.getAccountBalance(idUser), HttpStatus.OK);


  }
}