package model;

import enums.RoomCondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientCol {
    private List<Client> clientsList;
    private int countClient = 0;

    public ClientCol() {
        this.clientsList = new ArrayList<>();
    }

    public void addClient(Client client){
        if (client == null || client.getClientId() == null) {
            System.out.println("Ошибка: Неверные данные клиента");
            return;
        }

        if (checkClientInList(client.getClientId())) {
            System.out.println("Клиент с ID " + client.getClientId() + " уже существует");
            return;
        }

        clientsList.add(client);
        countClient++;
        System.out.println("Клиент " + client.getName() + " успешно добавлен");
    }

    public boolean checkClientInList(String clientId) {
        return clientsList.stream()
                .anyMatch(c -> c.getClientId().equals(clientId));
    }

    public Optional<Client> findClientById(String clientId) {
        return clientsList.stream()
                .filter(c -> c.getClientId().equals(clientId))
                .findFirst();
    }

    public int getCountClients(){
        return countClient;
    }

    public List<Client> getAllClients() {
        return new ArrayList<>(clientsList);
    }
}
