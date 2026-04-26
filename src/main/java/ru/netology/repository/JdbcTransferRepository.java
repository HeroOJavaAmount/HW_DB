package ru.netology.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.netology.dto.TransferRequest;
import ru.netology.exception.InvalidInputException;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.UUID;

import static ru.netology.config.ConstantContainer.ERROR_INVALID_CARD_DATA;

@Repository
@Profile({"db", "postgres"})
public class JdbcTransferRepository implements TransferRepository {

    private final JdbcTemplate jdbc;

    public JdbcTransferRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public boolean cardExists(String cardNumber) {
        String sql = "SELECT COUNT(*) FROM card WHERE number = ?";
        Integer count = jdbc.queryForObject(sql, Integer.class, cardNumber);
        return count != null && count > 0;
    }

    @Override
    public boolean hasSufficientFunds(String cardNumber, BigDecimal amountInKopeks) {
        BigDecimal amountInRubles = amountInKopeks.movePointLeft(2);
        String sql = "SELECT balance >= ? FROM card WHERE number = ?";
        Boolean result = jdbc.queryForObject(sql, Boolean.class, amountInRubles, cardNumber);
        if (result == null) throw new InvalidInputException("Card not found", ERROR_INVALID_CARD_DATA);
        return result;
    }

    @Override
    @Transactional
    public boolean withdraw(String cardNumber, BigDecimal amountInKopeks) {
        BigDecimal amountInRubles = amountInKopeks.movePointLeft(2);
        String sql = "UPDATE card SET balance = balance - ? WHERE number = ? AND balance >= ?";
        int rows = jdbc.update(sql, amountInRubles, cardNumber, amountInRubles);
        return rows > 0;
    }

    @Override
    @Transactional
    public boolean deposit(String cardNumber, BigDecimal amountInKopeks) {
        BigDecimal amountInRubles = amountInKopeks.movePointLeft(2);
        String sql = "UPDATE card SET balance = balance + ? WHERE number = ?";
        int rows = jdbc.update(sql, amountInRubles, cardNumber);
        return rows > 0;
    }

    @Override
    @Transactional
    public String saveOperation(TransferRequest request) {
        String operationId = UUID.randomUUID().toString();
        String sql = "INSERT INTO transfer_operation (operation_id, card_from_number, card_to_number, amount, commission, status) " +
                "VALUES (?::uuid, ?, ?, ?, ?, 'PENDING')";
        BigDecimal amount = BigDecimal.valueOf(request.amount().value(), 2);
        jdbc.update(sql, operationId, request.cardFromNumber(), request.cardToNumber(), amount, BigDecimal.ZERO);
        return operationId;
    }

    @Override
    public TransferRequest getOperation(String operationId) {
        String sql = "SELECT card_from_number, card_to_number, amount FROM transfer_operation WHERE operation_id = ?::uuid";
        return jdbc.queryForObject(sql, (ResultSet rs, int rowNum) -> {
            String cardFrom = rs.getString("card_from_number");
            String cardTo = rs.getString("card_to_number");
            BigDecimal amountValue = rs.getBigDecimal("amount");
            return new TransferRequest(
                    cardFrom, "12/25", "123", cardTo,
                    new TransferRequest.Amount(amountValue.multiply(BigDecimal.valueOf(100)).intValue(), "RUB")
            );
        }, operationId);
    }

    @Override
    public String getStatus(String operationId) {
        String sql = "SELECT status FROM transfer_operation WHERE operation_id = ?::uuid";
        return jdbc.queryForObject(sql, String.class, operationId);
    }

    @Override
    @Transactional
    public void updateStatus(String operationId, String status) {
        String sql = "UPDATE transfer_operation SET status = ? WHERE operation_id = ?::uuid";
        jdbc.update(sql, status, operationId);
    }
}