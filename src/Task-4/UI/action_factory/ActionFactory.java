package UI.action_factory;

import UI.action.Action;

public interface ActionFactory {
    public Action settleClientAction();
    public Action evictClientAction();

    public Action findClientByIdAction();

    public Action addRoomAction();
    public Action changeRoomStatusAction();
    public Action updateRoomPriceAction();

    public Action getAllRoomsAction();
    public Action getAllAvailableRoomsAction();//size для кол-ва

    public Action getAllRoomsSortedByPriceAction();
    public Action getAllRoomsSortedByCapacityAction();
    public Action getAllRoomsSortedByStarsAction();
    public Action getAllRoomsSortedByTypeAction();

    public Action checkRoomAvailabilityAction();
    public Action getAllAvailableRoomsSortedByPriceAction();
    public Action getAllAvailableRoomsSortedByCapacityAction();
    public Action getAllAvailableRoomsSortedByStarsAction();
    public Action getAllAvailableRoomsSortedByTypeAction();

    public Action getAllCompletedBookingsAction();
    public Action getRoomDetailsAction();
    public Action getAvailableRoomsByDateAction();

    public Action getAllClientsSortedByAlphabetAction();
    public Action getAllClientsSortedByDateEndAction();
    public Action getAllClientsSortedByNoneAction(); //size для кол-ва

    public Action addAmenityAction();
    public Action updateAmenityPriceAction();
    public Action addAmenityToClientAction();
    public Action getAllAmenityAction();

    public Action getAmenitiesClientSortedByDateAction();
    public Action getAmenitiesClientSortedByPriceAction();
    public Action getAmenitiesClientSortedByNoneAction();

    public Action getAllAmenitiesSortedByPriceAction();
    public Action getAllAmenitiesSortedByNameAction();

    public Action getLastThreeRoomClientsAction();
    public Action calculateRoomPaymentAction();
    public Action getTotalServicedClientAction();
    public Action showTotalRevenueAction();
    public Action getTotalCountAvailableRooms();
}
