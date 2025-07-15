package hotelsystem.UI.action.amenity;

import hotelsystem.UI.action.Action;
import hotelsystem.enums.SortType;
import hotelsystem.Controller.ManagerHotel;
import java.util.Scanner;

public class getAmenitiesClientSortedByPriceAction implements Action {
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);

    public getAmenitiesClientSortedByPriceAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        System.out.print("\nУслуги клиента (по цене)\nНомер комнаты: ");
        int roomNumber = scanner.nextInt();
        scanner.nextLine();

        manager.findClientByRoom(roomNumber).ifPresent(client ->
                manager.getClientAmenitiesSorted(client, SortType.PRICE)
                        .forEach(a -> System.out.printf("%.2f руб. - %s%n",
                                a.getAmenity().getPrice(), a.getAmenity().getName()))
        );
    }
}