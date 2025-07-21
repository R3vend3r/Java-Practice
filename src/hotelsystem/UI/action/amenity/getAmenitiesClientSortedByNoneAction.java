package hotelsystem.UI.action.amenity;

import hotelsystem.UI.action.Action;
import hotelsystem.enums.SortType;
import hotelsystem.Controller.ManagerHotel;
import java.util.Scanner;

public class getAmenitiesClientSortedByNoneAction implements Action {
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);

    public getAmenitiesClientSortedByNoneAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        System.out.print("\nВсе услуги клиента\nНомер комнаты: ");
        int roomNumber = scanner.nextInt();
        scanner.nextLine();

        manager.findClientByRoom(roomNumber).ifPresent(client ->
                manager.getClientAmenitiesSorted(client, SortType.NONE)
                        .forEach(a -> System.out.println(a.getAmenity()))
        );
    }
}