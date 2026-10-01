package dao;

import entity.Account;
import entity.CurrentAccount;
import entity.SavingsAccount;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    public void add(Account account) {

        String sql = """
                INSERT INTO accounts
                (number, balance, client_id, account_type, overdraft, interest_rate)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, account.getNumber());
            statement.setDouble(2, account.getBalance());
            statement.setInt(3, account.getClientId());

            if (account instanceof CurrentAccount currentAccount) {
                statement.setString(4, "CURRENT");
                statement.setDouble(5, currentAccount.getOverdraft());
                statement.setNull(6, Types.DOUBLE);
            }

            if (account instanceof SavingsAccount savingsAccount) {
                statement.setString(4, "SAVINGS");
                statement.setNull(5, Types.DOUBLE);
                statement.setDouble(6, savingsAccount.getInterestRate());
            }

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public List<Account> findAll() {

        List<Account> accounts = new ArrayList<>();

        String sql = "SELECT * FROM accounts";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet result = statement.executeQuery()
        ) {

            while (result.next()) {

                String type = result.getString("account_type");

                if (type.equals("CURRENT")) {

                    Account account = new CurrentAccount(
                            result.getInt("id"),
                            result.getString("number"),
                            result.getDouble("balance"),
                            result.getInt("client_id"),
                            result.getDouble("overdraft")
                    );

                    accounts.add(account);

                } else if (type.equals("SAVINGS")) {

                    Account account = new SavingsAccount(
                            result.getInt("id"),
                            result.getString("number"),
                            result.getDouble("balance"),
                            result.getInt("client_id"),
                            result.getDouble("interest_rate")
                    );

                    accounts.add(account);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return accounts;
    }
}