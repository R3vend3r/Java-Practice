package UI.action_factory;

import UI.action.Action;
import UI.action.amenity.*;
import UI.action.client.*;
import UI.action.order.evictClientAction;
import UI.action.order.getAllCompletedBookingsAction;
import UI.action.order.settleClientAction;
import UI.action.order.showTotalRevenueAction;
import UI.action.room.*;
import Controller.ManagerHotel;

public class ActionFactoryController implements ActionFactory {
    private final ManagerHotel actionFactoryController;

    public ActionFactoryController(ManagerHotel command) {
        this.actionFactoryController = command;
    }

    @Override
    public Action settleClientAction() {
        return new settleClientAction(actionFactoryController);
    }

    @Override
    public Action evictClientAction() {
        return new evictClientAction(actionFactoryController);
    }

    @Override
    public Action findClientByIdAction() {
        return new findClientByIdAction(actionFactoryController);
    }

    @Override
    public Action addRoomAction() {
        return new addRoomAction(actionFactoryController);
    }

    @Override
    public Action changeRoomStatusAction() {
        return new changeRoomStatusAction(actionFactoryController);
    }

    @Override
    public Action updateRoomPriceAction() {
        return new updateRoomPriceAction(actionFactoryController);
    }

    @Override
    public Action getAllRoomsAction() {
        return new getAllRoomsAction(actionFactoryController);
    }

    @Override
    public Action getAllCompletedBookingsAction() {
        return new getAllCompletedBookingsAction(actionFactoryController);
    }

    @Override
    public Action checkRoomAvailabilityAction() {
        return new checkRoomAvailabilityAction(actionFactoryController);
    }

    @Override
    public Action addAmenityToClientAction() {
        return new addAmenityToClientAction(actionFactoryController);
    }

    @Override
    public Action getAllAvailableRoomsAction() {
        return new getAllAvailableRoomsAction(actionFactoryController);
    }

    @Override
    public Action getRoomDetailsAction() {
        return new getRoomDetailsAction(actionFactoryController);
    }

    @Override
    public Action getAllClientsSortedByAlphabetAction() {
        return new getAllClientsSortedByAlphabetAction(actionFactoryController);
    }

    @Override
    public Action getAllClientsSortedByDateEndAction() {
        return new getAllClientsSortedByDateEndAction(actionFactoryController);
    }

    @Override
    public Action getAllRoomsSortedByTypeAction() {
        return new getAllRoomsSortedByTypeAction(actionFactoryController);
    }

    @Override
    public Action getAllClientsSortedByNoneAction() {
        return new getAllClientsSortedByNoneAction(actionFactoryController);
    }

    @Override
    public Action getAmenitiesClientSortedByDateAction() {
        return new getAmenitiesClientSortedByDateAction(actionFactoryController);
    }

    @Override
    public Action getAmenitiesClientSortedByPriceAction() {
        return new getAmenitiesClientSortedByPriceAction(actionFactoryController);
    }

    @Override
    public Action getAmenitiesClientSortedByNoneAction() {
        return new getAmenitiesClientSortedByNoneAction(actionFactoryController);
    }

    @Override
    public Action getAvailableRoomsByDateAction() {
        return new getAvailableRoomsByDateAction(actionFactoryController);
    }

    @Override
    public Action getAllRoomsSortedByPriceAction() {
        return new getAllRoomsSortedByPriceAction(actionFactoryController);
    }

    @Override
    public Action getAllAvailableRoomsSortedByTypeAction() {
        return new getAllAvailableRoomsSortedByTypeAction(actionFactoryController);
    }

    @Override
    public Action getAllRoomsSortedByCapacityAction() {
        return new getAllRoomsSortedByCapacityAction(actionFactoryController);
    }

    @Override
    public Action getAllRoomsSortedByStarsAction() {
        return new getAllRoomsSortedByStarsAction(actionFactoryController);
    }

    @Override
    public Action getAllAvailableRoomsSortedByPriceAction() {
        return new getAllAvailableRoomsSortedByPriceAction(actionFactoryController);
    }

    @Override
    public Action getAllAvailableRoomsSortedByCapacityAction() {
        return new getAllAvailableRoomsSortedByCapacityAction(actionFactoryController);
    }

    @Override
    public Action addAmenityAction() {
        return new addAmenityAction(actionFactoryController);
    }

    @Override
    public Action updateAmenityPriceAction() {
        return new updateAmenityPriceAction(actionFactoryController);
    }

    @Override
    public Action getAllAmenityAction() {
        return new getAllAmenityAction(actionFactoryController);
    }

    @Override
    public Action getAllAmenitiesSortedByNameAction() {
        return new getAllAmenitiesSortedByNameAction(actionFactoryController);
    }

    @Override
    public Action getAllAvailableRoomsSortedByStarsAction() {
        return new getAllAvailableRoomsSortedByStarsAction(actionFactoryController);
    }

    @Override
    public Action getAllAmenitiesSortedByPriceAction() {
        return new getAllAmenitiesSortedByPriceAction(actionFactoryController);
    }

    @Override
    public Action getLastThreeRoomClientsAction() {
        return new getLastThreeRoomClientsAction(actionFactoryController);
    }

    @Override
    public Action calculateRoomPaymentAction() {
        return new calculateRoomPaymentAction(actionFactoryController);
    }

    @Override
    public Action getTotalServicedClientAction() {
        return new getTotalServicedClientAction(actionFactoryController);
    }

    @Override
    public Action showTotalRevenueAction() {
        return new showTotalRevenueAction(actionFactoryController);
    }

    @Override
    public Action getTotalCountAvailableRooms() {
        return new getTotalCountAvailableRooms(actionFactoryController);
    }
}
