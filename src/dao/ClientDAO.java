package dao;

import entity.Client;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    public void add(Client client) {

        String sql = "INSERT INTO clients(name, email) VALUES (?, ?)";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, client.name());
            statement.setString(2, client.email());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public List<Client> findAll() {

        List<Client> clients = new ArrayList<>();

        String sql = "SELECT * FROM clients";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet result = statement.executeQuery()
        ) {

            while (result.next()) {

                Client client = new Client(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("email")
                );

                clients.add(client);
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return clients;
    }
}