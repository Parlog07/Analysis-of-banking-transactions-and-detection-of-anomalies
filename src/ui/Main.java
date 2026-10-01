package ui;

import entity.Account;
import entity.Client;
import service.AccountService;
import service.ClientService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ClientService clientService = new ClientService();
        AccountService accountService = new AccountService();

        int choice = -1;

        while (choice != 0) {

            System.out.println("\n===== BANK ANALYSIS =====");
            System.out.println("1. Add client");
            System.out.println("2. Show clients");
            System.out.println("3. Add current account");
            System.out.println("4. Add savings account");
            System.out.println("5. Show accounts");
            System.out.println("6. Highest balance account");
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

                    case 0 -> System.out.println("Goodbye.");

                    default -> System.out.println("Invalid option.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter valid numbers.");
            }
        }
    }
}