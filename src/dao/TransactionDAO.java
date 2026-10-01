package dao;

import entity.Transaction;
import entity.TransactionType;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    public void add(Transaction transaction) {

        String sql = """
                INSERT INTO transactions
                (transaction_date, amount, type, location, account_id)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setTimestamp(
                    1,
                    Timestamp.valueOf(transaction.date())
            );

            statement.setDouble(2, transaction.amount());
            statement.setString(3, transaction.type().name());
            statement.setString(4, transaction.location());
            statement.setInt(5, transaction.accountId());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public List<Transaction> findAll() {

        List<Transaction> transactions = new ArrayList<>();

        String sql = "SELECT * FROM transactions";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet result = statement.executeQuery()
        ) {

            while (result.next()) {

                Transaction transaction = new Transaction(
                        result.getInt("id"),
                        result.getTimestamp("transaction_date").toLocalDateTime(),
                        result.getDouble("amount"),
                        TransactionType.valueOf(result.getString("type")),
                        result.getString("location"),
                        result.getInt("account_id")
                );

                transactions.add(transaction);
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return transactions;
    }
}