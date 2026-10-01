package ui;

import entity.Account;
import entity.Client;
import service.AccountService;
import service.ClientService;
import entity.Transaction;
import entity.TransactionType;
import service.TransactionService;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ClientService clientService = new ClientService();
        AccountService accountService = new AccountService();
        TransactionService transactionService = new TransactionService();

        int choice = -1;

        while (choice != 0) {

            System.out.println("\n===== BANK ANALYSIS =====");
            System.out.println("1. Add client");
            System.out.println("2. Show clients");
            System.out.println("3. Add current account");
            System.out.println("4. Add savings account");
            System.out.println("5. Show accounts");
            System.out.println("6. Highest balance account");
            System.out.println("7. Add transaction");
            System.out.println("8. Show transactions");
            System.out.println("9. Total transaction amount");
            System.out.println("10. Suspicious transactions");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            try {

                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {

                    case 1 -> {

                        System.out.print("Name: ");
                        String name = scanner.nextLine();

                        System.out.print("Email: ");
                        String email = scanner.nextLine();

                        clientService.addClient(name, email);

                        System.out.println("Client added.");
                    }

                    case 2 -> {

                        for (Client client : clientService.getAllClients()) {
                            System.out.println(
                                    client.id() + " | " +
                                            client.name() + " | " +
                                            client.email()
                            );
                        }
                    }

                    case 3 -> {

                        System.out.print("Account number: ");
                        String number = scanner.nextLine();

                        System.out.print("Balance: ");
                        double balance = Double.parseDouble(scanner.nextLine());

                        System.out.print("Client ID: ");
                        int clientId = Integer.parseInt(scanner.nextLine());

                        System.out.print("Overdraft: ");
                        double overdraft = Double.parseDouble(scanner.nextLine());

                        accountService.addCurrentAccount(
                                number,
                                balance,
                                clientId,
                                overdraft
                        );

                        System.out.println("Current account added.");
                    }

                    case 4 -> {

                        System.out.print("Account number: ");
                        String number = scanner.nextLine();

                        System.out.print("Balance: ");
                        double balance = Double.parseDouble(scanner.nextLine());

                        System.out.print("Client ID: ");
                        int clientId = Integer.parseInt(scanner.nextLine());

                        System.out.print("Interest rate: ");
                        double interestRate = Double.parseDouble(scanner.nextLine());

                        accountService.addSavingsAccount(
                                number,
                                balance,
                                clientId,
                                interestRate
                        );

                        System.out.println("Savings account added.");
                    }

                    case 5 -> {

                        for (Account account : accountService.getAllAccounts()) {

                            System.out.println(
                                    account.getId() + " | " +
                                            account.getNumber() + " | " +
                                            account.getBalance() + " | Client: " +
                                            account.getClientId()
                            );
                        }
                    }

                    case 6 -> {

                        accountService.getHighestBalanceAccount()
                                .ifPresentOrElse(
                                        account -> System.out.println(
                                                "Highest: " +
                                                        account.getNumber() +
                                                        " | " +
                                                        account.getBalance()
                                        ),
                                        () -> System.out.println("No accounts found.")
                                );
                    }
                    case 7 -> {

                        System.out.print("Amount: ");
                        double amount = Double.parseDouble(scanner.nextLine());

                        System.out.println("1. DEPOSIT");
                        System.out.println("2. WITHDRAWAL");
                        System.out.println("3. TRANSFER");
                        System.out.print("Type: ");

                        int typeChoice = Integer.parseInt(scanner.nextLine());

                        TransactionType type;

                        if (typeChoice == 1) {
                            type = TransactionType.DEPOSIT;
                        } else if (typeChoice == 2) {
                            type = TransactionType.WITHDRAWAL;
                        } else {
                            type = TransactionType.TRANSFER;
                        }

                        System.out.print("Location: ");
                        String location = scanner.nextLine();

                        System.out.print("Account ID: ");
                        int accountId = Integer.parseInt(scanner.nextLine());

                        Transaction transaction = new Transaction(
                                0,
                                LocalDateTime.now(),
                                amount,
                                type,
                                location,
                                accountId
                        );

                        transactionService.addTransaction(transaction);

                        System.out.println("Transaction added.");
                    }

                    case 8 -> {

                        for (Transaction transaction : transactionService.getAllTransactions()) {
                            System.out.println(
                                    transaction.id() + " | " +
                                            transaction.date() + " | " +
                                            transaction.amount() + " | " +
                                            transaction.type() + " | " +
                                            transaction.location() + " | Account: " +
                                            transaction.accountId()
                            );
                        }
                    }

                    case 9 -> {

                        double total = transactionService.getTotalAmount();

                        System.out.println("Total amount: " + total);
                    }

                    case 10 -> {

                        for (Transaction transaction : transactionService.getSuspiciousTransactions()) {

                            System.out.println(
                                    "SUSPICIOUS | " +
                                            transaction.amount() + " | " +
                                            transaction.type() + " | " +
                                            transaction.location()
                            );
                        }
                    }

                    case 0 -> System.out.println("Goodbye.");

                    default -> System.out.println("Invalid option.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter valid numbers.");
            }
        }
    }
}