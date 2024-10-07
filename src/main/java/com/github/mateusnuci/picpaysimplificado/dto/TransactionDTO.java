package com.github.mateusnuci.picpaysimplificado.dto;

import java.math.BigDecimal;


public  record TransactionDTO(BigDecimal amount, Long receiverId, Long senderId) {
}
