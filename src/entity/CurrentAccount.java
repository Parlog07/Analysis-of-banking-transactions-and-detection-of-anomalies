package entity;

public final class CurrentAccount extends Account {

    private double overdraft;

    public CurrentAccount(int id, String number, double balance, int clientId, double overdraft) {
        super(id, number, balance, clientId);
        this.overdraft = overdraft;
    }

    public double getOverdraft() {
        return overdraft;
    }
}