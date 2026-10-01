package service;

import dao.ClientDAO;
import entity.Client;

import java.util.List;

public class ClientService {

    private ClientDAO clientDAO = new ClientDAO();

    public void addClient(String name, String email) {
        Client client = new Client(0, name, email);
        clientDAO.add(client);
    }

    public List<Client> getAllClients() {
        return clientDAO.findAll();
    }
}