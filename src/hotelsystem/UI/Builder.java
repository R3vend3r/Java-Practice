package hotelsystem.UI;

import hotelsystem.Controller.ManagerHotel;
import hotelsystem.UI.action.Action;
import hotelsystem.UI.action_factory.ActionFactory;
import hotelsystem.dependencies.annotation.Inject;
import hotelsystem.dependencies.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Builder implements Action {
    private static final Logger logger = LoggerFactory.getLogger(Builder.class);

    private Menu rootMenu;
    @Inject
    private ManagerHotel managerHotel;
    @Inject
    private ActionFactory actionFactory;

    @PostConstruct
    public void init() {
        logger.info("Инициализация Builder");
        execute();
    }

    public void buildMenu() {
        logger.info("Построение структуры меню");
        rootMenu = new Menu("Главное меню гостиницы");

        rootMenu.addMenuItem(new MenuItem("Номера", null, buildRoomsMenu()));

        rootMenu.addMenuItem(new MenuItem("Клиенты", null, buildClientsMenu()));

        rootMenu.addMenuItem(new MenuItem("Услуги", null, buildAmenitiesMenu()));

        rootMenu.addMenuItem(new MenuItem("Отчеты", null, buildReportsMenu()));

        rootMenu.addMenuItem(new MenuItem("Операции", null, buildOperationsMenu()));

        rootMenu.addMenuItem(new MenuItem("Импорт/Экспорт", null, buildImportExportMenu()));

    }

    private Menu buildRoomsMenu() {
        logger.debug("Создание меню для управления номерами");
        System.out.println();
        Menu roomsMenu = new Menu("Управление номерами");

        roomsMenu.addMenuItem(new MenuItem("Добавить номер", actionFactory.addRoomAction(), null));
        roomsMenu.addMenuItem(new MenuItem("Изменить статус номера", actionFactory.changeRoomStatusAction(), null));
        roomsMenu.addMenuItem(new MenuItem("Изменить цену номера", actionFactory.updateRoomPriceAction(), null));

        Menu viewRoomsMenu = new Menu("Просмотр номеров");
        viewRoomsMenu.addMenuItem(new MenuItem("Все номера", actionFactory.getAllRoomsAction(), null));
        viewRoomsMenu.addMenuItem(new MenuItem("Свободные номера", actionFactory.getAllAvailableRoomsAction(), null));

        Menu sortRoomsMenu = new Menu("Сортировка номеров");
        sortRoomsMenu.addMenuItem(new MenuItem("По цене", actionFactory.getAllRoomsSortedByPriceAction(), null));
        sortRoomsMenu.addMenuItem(new MenuItem("По вместимости", actionFactory.getAllRoomsSortedByCapacityAction(), null));
        sortRoomsMenu.addMenuItem(new MenuItem("По звездам", actionFactory.getAllRoomsSortedByStarsAction(), null));
        sortRoomsMenu.addMenuItem(new MenuItem("По типу", actionFactory.getAllRoomsSortedByTypeAction(), null));

        Menu sortAvailableRoomsMenu = new Menu("Сортировка свободных номеров");
        sortAvailableRoomsMenu.addMenuItem(new MenuItem("По цене", actionFactory.getAllAvailableRoomsSortedByPriceAction(), null));
        sortAvailableRoomsMenu.addMenuItem(new MenuItem("По вместимости", actionFactory.getAllAvailableRoomsSortedByCapacityAction(), null));
        sortAvailableRoomsMenu.addMenuItem(new MenuItem("По звездам", actionFactory.getAllAvailableRoomsSortedByStarsAction(), null));
        sortAvailableRoomsMenu.addMenuItem(new MenuItem("По типу", actionFactory.getAllAvailableRoomsSortedByTypeAction(), null));


        viewRoomsMenu.addMenuItem(new MenuItem("Сортировка всех", null, sortRoomsMenu));
        viewRoomsMenu.addMenuItem(new MenuItem("Сортировка свободных", null, sortAvailableRoomsMenu));
        roomsMenu.addMenuItem(new MenuItem("Просмотр номеров", null, viewRoomsMenu));

        roomsMenu.addMenuItem(new MenuItem("Детали номера", actionFactory.FindRoomAction(), null));

        return roomsMenu;
    }

    private Menu buildClientsMenu() {
        Menu clientsMenu = new Menu("Управление клиентами");

        clientsMenu.addMenuItem(new MenuItem("Зарегистрировать и поселить", actionFactory.settleClientAction(), null));
        clientsMenu.addMenuItem(new MenuItem("Выселить клиента", actionFactory.evictClientAction(), null));
        clientsMenu.addMenuItem(new MenuItem("Найти клиента", actionFactory.findClientByIdAction(), null));

        Menu viewClientsMenu = new Menu("Просмотр клиентов");
        viewClientsMenu.addMenuItem(new MenuItem("Все клиенты", actionFactory.getAllClientsSortedByNoneAction(), null));

        Menu sortClientsMenu = new Menu("Сортировка клиентов");
        sortClientsMenu.addMenuItem(new MenuItem("По алфавиту", actionFactory.getAllClientsSortedByAlphabetAction(), null));
        sortClientsMenu.addMenuItem(new MenuItem("По дате выезда", actionFactory.getAllClientsSortedByDateEndAction(), null));

        viewClientsMenu.addMenuItem(new MenuItem("Сортировка", null, sortClientsMenu));
        clientsMenu.addMenuItem(new MenuItem("Просмотр клиентов", null, viewClientsMenu));

        return clientsMenu;
    }

    private Menu buildAmenitiesMenu() {
        Menu amenitiesMenu = new Menu("Управление услугами");

        amenitiesMenu.addMenuItem(new MenuItem("Добавить услугу", actionFactory.addAmenityAction(), null));
        amenitiesMenu.addMenuItem(new MenuItem("Изменить цену услуги", actionFactory.updateAmenityPriceAction(), null));

        Menu viewAmenitiesMenu = new Menu("Просмотр услуг");
        viewAmenitiesMenu.addMenuItem(new MenuItem("Все услуги", actionFactory.getAllAmenityAction(), null));

        Menu sortAmenitiesMenu = new Menu("Сортировка услуг");
        sortAmenitiesMenu.addMenuItem(new MenuItem("По цене", actionFactory.getAllAmenitiesSortedByPriceAction(), null));
        sortAmenitiesMenu.addMenuItem(new MenuItem("По названию", actionFactory.getAllAmenitiesSortedByNameAction(), null));

        viewAmenitiesMenu.addMenuItem(new MenuItem("Сортировка", null, sortAmenitiesMenu));
        amenitiesMenu.addMenuItem(new MenuItem("Просмотр услуг", null, viewAmenitiesMenu));

        Menu clientAmenitiesMenu = new Menu("Услуги клиента");
        clientAmenitiesMenu.addMenuItem(new MenuItem("Все услуги клиента", actionFactory.getAmenitiesClientSortedByNoneAction(), null));
        clientAmenitiesMenu.addMenuItem(new MenuItem("Сортировка по дате", actionFactory.getAmenitiesClientSortedByDateAction(), null));
        clientAmenitiesMenu.addMenuItem(new MenuItem("Сортировка по цене", actionFactory.getAmenitiesClientSortedByPriceAction(), null));

        amenitiesMenu.addMenuItem(new MenuItem("Услуги клиентов", null, clientAmenitiesMenu));

        return amenitiesMenu;
    }

    private Menu buildReportsMenu() {
        Menu reportsMenu = new Menu("Отчеты и аналитика");

        reportsMenu.addMenuItem(new MenuItem("Количество свободных номеров", actionFactory.getTotalCountAvailableRooms(), null));
        reportsMenu.addMenuItem(new MenuItem("Количество клиентов", actionFactory.getTotalServicedClientAction(), null));
        reportsMenu.addMenuItem(new MenuItem("Общий доход", actionFactory.showTotalRevenueAction(), null));

        Menu bookingHistoryMenu = new Menu("История бронирований");
        bookingHistoryMenu.addMenuItem(new MenuItem("Последние 3 постояльца", actionFactory.getLastThreeRoomClientsAction(), null));
        bookingHistoryMenu.addMenuItem(new MenuItem("Полная история номера", actionFactory.getFullRoomHistoryAction(), null));
        bookingHistoryMenu.addMenuItem(new MenuItem("Все завершенные бронирования", actionFactory.getAllCompletedBookingsAction(), null));

        reportsMenu.addMenuItem(new MenuItem("История бронирований", null, bookingHistoryMenu));

        return reportsMenu;
    }

    private Menu buildOperationsMenu() {
        Menu operationsMenu = new Menu("Дополнительные операции");

        operationsMenu.addMenuItem(new MenuItem("Рассчитать стоимость проживания", actionFactory.calculateRoomPaymentAction(), null));
        operationsMenu.addMenuItem(new MenuItem("Найти свободные номера к дате", actionFactory.getAvailableRoomsByDateAction(), null));
        operationsMenu.addMenuItem(new MenuItem("Проверить доступность номера", actionFactory.checkRoomAvailabilityAction(), null));
        operationsMenu.addMenuItem(new MenuItem("Добавить услугу клиенту", actionFactory.addAmenityToClientAction(), null));

//        operationsMenu.addMenuItem(new MenuItem("Сохранить состояние", actionFactory.saveStateAction(), null));
//        operationsMenu.addMenuItem(new MenuItem("Загрузить состояние", actionFactory.loadStateAction(), null));

        return operationsMenu;
    }

    private Menu buildImportExportMenu() {
        Menu importExportMenu = new Menu("Импорт/Экспорт данных");

        Menu roomsImportExport = new Menu("Номера");
        roomsImportExport.addMenuItem(new MenuItem("Экспорт номеров в CSV", actionFactory.exportRoomsCsvAction(), null));
        roomsImportExport.addMenuItem(new MenuItem("Импорт номеров из CSV", actionFactory.importRoomsCsvAction(), null));
        importExportMenu.addMenuItem(new MenuItem("Номера", null, roomsImportExport));

        Menu clientsImportExport = new Menu("Клиенты");
        clientsImportExport.addMenuItem(new MenuItem("Экспорт клиентов в CSV", actionFactory.exportClientsCsvAction(), null));
        clientsImportExport.addMenuItem(new MenuItem("Импорт клиентов из CSV", actionFactory.importClientsCsvAction(), null));
        importExportMenu.addMenuItem(new MenuItem("Клиенты", null, clientsImportExport));

        Menu amenitiesImportExport = new Menu("Услуги");
        amenitiesImportExport.addMenuItem(new MenuItem("Экспорт услуг в CSV", actionFactory.exportAmenitiesCsvAction(), null));
        amenitiesImportExport.addMenuItem(new MenuItem("Импорт услуг из CSV", actionFactory.importAmenitiesCsvAction(), null));
        importExportMenu.addMenuItem(new MenuItem("Услуги", null, amenitiesImportExport));

        Menu bookingsImportExport = new Menu("Бронирования");
        bookingsImportExport.addMenuItem(new MenuItem("Экспорт бронирований в CSV", actionFactory.exportBookingsCsvAction(), null));
        bookingsImportExport.addMenuItem(new MenuItem("Импорт бронирований из CSV", actionFactory.importBookingsCsvAction(), null));
        importExportMenu.addMenuItem(new MenuItem("Бронирования", null, bookingsImportExport));

        Menu amenityOrdersImportExport = new Menu("Заказы услуг");
        amenityOrdersImportExport.addMenuItem(new MenuItem("Экспорт заказов в CSV", actionFactory.exportAmenityOrdersCsvAction(), null));
        amenityOrdersImportExport.addMenuItem(new MenuItem("Импорт заказов из CSV", actionFactory.importAmenityOrdersCsvAction(), null));
        importExportMenu.addMenuItem(new MenuItem("Заказы услуг", null, amenityOrdersImportExport));

        return importExportMenu;
    }

    public Menu getRootMenu() {
        if (rootMenu == null) {
            buildMenu();
        }
        return rootMenu;
    }
    @Override
    public void execute() {
        logger.info("Выполнение построения меню");
        buildMenu();
    }
}