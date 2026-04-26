package ru.netology.repository;

import ru.netology.dto.TransferRequest;
import java.math.BigDecimal;

public interface TransferRepository {
    boolean cardExists(String cardNumber);
    boolean hasSufficientFunds(String cardNumber, BigDecimal amountInKopeks);
    boolean withdraw(String cardNumber, BigDecimal amountInKopeks);
    boolean deposit(String cardNumber, BigDecimal amountInKopeks);
    String saveOperation(TransferRequest request);
    TransferRequest getOperation(String operationId);
    String getStatus(String operationId);
    void updateStatus(String operationId, String status);
}