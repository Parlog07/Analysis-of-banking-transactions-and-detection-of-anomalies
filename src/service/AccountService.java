package service;

import dao.AccountDAO;
import entity.Account;
import entity.CurrentAccount;
import entity.SavingsAccount;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class AccountService {

    private AccountDAO accountDAO = new AccountDAO();

    public void addCurrentAccount(String number, double balance, int clientId, double overdraft) {
        CurrentAccount account =
                new CurrentAccount(0, number, balance, clientId, overdraft);

        accountDAO.add(account);
    }

    public void addSavingsAccount(String number, double balance, int clientId, double interestRate) {
        SavingsAccount account =
                new SavingsAccount(0, number, balance, clientId, interestRate);

        accountDAO.add(account);
    }

    public List<Account> getAllAccounts() {
        return accountDAO.findAll();
    }

    public Optional<Account> getHighestBalanceAccount() {
        return accountDAO.findAll()
                .stream()
                .max(Comparator.comparing(Account::getBalance));
    }
}