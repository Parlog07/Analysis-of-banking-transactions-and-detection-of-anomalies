package entity;

public sealed class Account permits CurrentAccount, SavingsAccount {

    private int id;
    private String number;
    private double balance;
    private int clientId;

    public Account(int id, String number, double balance, int clientId) {
        this.id = id;
        this.number = number;
        this.balance = balance;
        this.clientId = clientId;
    }

    public int getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public double getBalance() {
        return balance;
    }

    public int getClientId() {
        return clientId;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}