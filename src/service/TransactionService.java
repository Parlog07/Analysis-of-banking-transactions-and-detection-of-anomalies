package service;

import dao.TransactionDAO;
import entity.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class TransactionService {

    private TransactionDAO transactionDAO = new TransactionDAO();

    public List<Transaction> getAllTransactions() {
        return transactionDAO.findAll();
    }

    public double getTotalAmount() {
        return transactionDAO.findAll()
                .stream()
                .mapToDouble(Transaction::amount)
                .sum();
    }

    public List<Transaction> getSuspiciousTransactions() {
        return transactionDAO.findAll()
                .stream()
                .filter(transaction -> transaction.amount() > 10000)
                .collect(Collectors.toList());
    }
    public void addTransaction(Transaction transaction) {
        transactionDAO.add(transaction);
    }
}