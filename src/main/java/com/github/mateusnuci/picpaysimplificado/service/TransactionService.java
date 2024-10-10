package com.github.mateusnuci.picpaysimplificado.service;


import com.github.mateusnuci.picpaysimplificado.domain.transactional.Transaction;
import com.github.mateusnuci.picpaysimplificado.domain.user.User;
import com.github.mateusnuci.picpaysimplificado.dto.TransactionDTO;
import com.github.mateusnuci.picpaysimplificado.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificationService notificationService;

    public Transaction createTransaction(TransactionDTO transactionDTO) throws Exception {
        User sender = userService.findUserById(transactionDTO.senderId());
        User receiver = userService.findUserById(transactionDTO.receiverId());

        userService.validateTransaction(sender, transactionDTO.amount());

        if (!this.authorizeTransaction(sender, transactionDTO.amount())) {
            throw new Exception("Transação não autorizada");
        }

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transactionDTO.amount());
        newTransaction.setReceiver(receiver);
        newTransaction.setSender(sender);
        newTransaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transactionDTO.amount()));
        receiver.setBalance(receiver.getBalance().add(transactionDTO.amount()));

        repository.save(newTransaction);
        userService.saveUser(sender);
        userService.saveUser(receiver);

        notificationService.sendNotification(sender, "Transação realizada com sucessso");
        notificationService.sendNotification(receiver, "Transação recebida com sucessso");

        return newTransaction;


    }

    private boolean authorizeTransaction(User sender, BigDecimal amount) {
        ResponseEntity<Map> authorizationResponse =
                restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);
        return authorizationResponse.getStatusCode() == HttpStatus.OK &&
                ((Map<String, Object>) authorizationResponse.getBody().get("data"))
                        .getOrDefault("authorization", false).equals(true);
    }


}
