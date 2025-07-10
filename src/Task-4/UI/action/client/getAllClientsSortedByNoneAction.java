package UI.action.client;

import UI.action.Action;
import Controller.ManagerHotel;

public class getAllClientsSortedByNoneAction implements Action {
    private final ManagerHotel manager;

    public getAllClientsSortedByNoneAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        System.out.println("\n=== Список клиентов ===");
        manager.getAllClients().forEach(client -> {
            String roomInfo = client.getRoomNumber() > 0 ?
                    "Номер " + client.getRoomNumber() : "Не заселен";
            System.out.printf("%s %s | %s | ID: %s%n",
                    client.getName(),
                    client.getSurname(),
                    roomInfo,
                    client.getClientId());
        });
    }
}