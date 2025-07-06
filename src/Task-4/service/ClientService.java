package service;

import model.Client;
import model.ClientCol;

import java.util.Optional;

public class ClientService {
    private ClientCol clientCol;

    public ClientService() {
        this.clientCol = new ClientCol();
    }

    public void registerClient(Client client){
        clientCol.addClient(client);
    }

    public boolean checkClient(Client client){
        if (client == null || clientCol== null) {
            return false;
        }
        return clientCol.checkClientInList(client.getClientId());
    }

    public Optional<Client> findClientById(String clientId) {
        return clientCol.findClientById(clientId);
    }

    public int getClientCount() {
        return clientCol.getCountClients();
    }
}
