package hotelsystem.UI.action_factory;

import hotelsystem.UI.action.Action;
import hotelsystem.UI.action.amenity.*;
import hotelsystem.UI.action.client.*;
import hotelsystem.UI.action.import_export.amenity.exportAmenitiesCsvAction;
import hotelsystem.UI.action.import_export.amenity.importAmenitiesCsvAction;
import hotelsystem.UI.action.import_export.amenityOrder.exportAmenityOrdersCsvAction;
import hotelsystem.UI.action.import_export.amenityOrder.importAmenityOrdersCsvAction;
import hotelsystem.UI.action.import_export.booking.exportBookingsCsvAction;
import hotelsystem.UI.action.import_export.booking.importBookingsCsvAction;
import hotelsystem.UI.action.import_export.client.exportClientsCsvAction;
import hotelsystem.UI.action.import_export.client.importClientsCsvAction;
import hotelsystem.UI.action.import_export.room.importRoomsCsvAction;
import hotelsystem.UI.action.import_export.room.exportRoomsCsvAction;
import hotelsystem.UI.action.order.evictClientAction;
import hotelsystem.UI.action.order.getAllCompletedBookingsAction;
import hotelsystem.UI.action.order.settleClientAction;
import hotelsystem.UI.action.order.showTotalRevenueAction;
import hotelsystem.UI.action.room.*;
import hotelsystem.Controller.ManagerHotel;
import hotelsystem.dependencies.annotation.Inject;

public class ActionFactoryController implements ActionFactory {
    @Inject
    private ManagerHotel actionFactoryController;

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
    public Action FindRoomAction() {
        return new FindRoomAction(actionFactoryController);
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

    @Override
    public Action importRoomsCsvAction() {
        return new importRoomsCsvAction(actionFactoryController);
    }

    @Override
    public Action exportRoomsCsvAction() {
        return new exportRoomsCsvAction(actionFactoryController);
    }

    @Override
    public Action exportClientsCsvAction() {
        return new exportClientsCsvAction(actionFactoryController);
    }

    @Override
    public Action importClientsCsvAction() {
        return new importClientsCsvAction(actionFactoryController);
    }

    @Override
    public Action exportAmenitiesCsvAction() {
        return new exportAmenitiesCsvAction(actionFactoryController);
    }

    @Override
    public Action importAmenitiesCsvAction() {
        return new importAmenitiesCsvAction(actionFactoryController);
    }

    @Override
    public Action exportBookingsCsvAction() {
        return new exportBookingsCsvAction(actionFactoryController);
    }

    @Override
    public Action importBookingsCsvAction() {
        return new importBookingsCsvAction(actionFactoryController);
    }

    @Override
    public Action exportAmenityOrdersCsvAction() {
        return new exportAmenityOrdersCsvAction(actionFactoryController);
    }

    @Override
    public Action importAmenityOrdersCsvAction() {
        return new importAmenityOrdersCsvAction(actionFactoryController);
    }

    @Override
    public Action getFullRoomHistoryAction() {
        return new getFullRoomHistoryAction(actionFactoryController);
    }

//    @Override
//    public Action saveStateAction() {
//        return new saveStateAction(actionFactoryController);
//    }
//
//    @Override
//    public Action loadStateAction() {
//        return new loadStateAction(actionFactoryController);
//    }
}
