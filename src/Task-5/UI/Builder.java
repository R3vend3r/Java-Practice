package UI;

import UI.action.Action;
import UI.action_factory.ActionFactory;

public class Builder implements Action {
    private Menu rootMenu;
    private final ActionFactory actionFactory;

    public Builder(ActionFactory actionFactory) {
        this.actionFactory = actionFactory;
        execute();
    }

    private void buildMenu() {
        rootMenu = new Menu("Главное меню гостиницы");

        rootMenu.addMenuItem(new MenuItem("Номера", null, buildRoomsMenu()));

        rootMenu.addMenuItem(new MenuItem("Клиенты", null, buildClientsMenu()));

        rootMenu.addMenuItem(new MenuItem("Услуги", null, buildAmenitiesMenu()));

        rootMenu.addMenuItem(new MenuItem("Отчеты", null, buildReportsMenu()));

        rootMenu.addMenuItem(new MenuItem("Операции", null, buildOperationsMenu()));
    }

    private Menu buildRoomsMenu() {
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

        // Сортировка свободных номеров
        Menu sortAvailableRoomsMenu = new Menu("Сортировка свободных номеров");
        sortAvailableRoomsMenu.addMenuItem(new MenuItem("По цене", actionFactory.getAllAvailableRoomsSortedByPriceAction(), null));
        sortAvailableRoomsMenu.addMenuItem(new MenuItem("По вместимости", actionFactory.getAllAvailableRoomsSortedByCapacityAction(), null));
        sortAvailableRoomsMenu.addMenuItem(new MenuItem("По звездам", actionFactory.getAllAvailableRoomsSortedByStarsAction(), null));
        sortAvailableRoomsMenu.addMenuItem(new MenuItem("По типу", actionFactory.getAllAvailableRoomsSortedByTypeAction(), null));


        viewRoomsMenu.addMenuItem(new MenuItem("Сортировка всех", null, sortRoomsMenu));
        viewRoomsMenu.addMenuItem(new MenuItem("Сортировка свободных", null, sortAvailableRoomsMenu));
        roomsMenu.addMenuItem(new MenuItem("Просмотр номеров", null, viewRoomsMenu));

        // Детали номера
        roomsMenu.addMenuItem(new MenuItem("Детали номера", actionFactory.getRoomDetailsAction(), null));

        return roomsMenu;
    }

    private Menu buildClientsMenu() {
        Menu clientsMenu = new Menu("Управление клиентами");

        // Основные операции
        clientsMenu.addMenuItem(new MenuItem("Зарегистрировать и поселить", actionFactory.settleClientAction(), null));
        clientsMenu.addMenuItem(new MenuItem("Выселить клиента", actionFactory.evictClientAction(), null));
        clientsMenu.addMenuItem(new MenuItem("Найти клиента", actionFactory.findClientByIdAction(), null));

        // Просмотр клиентов
        Menu viewClientsMenu = new Menu("Просмотр клиентов");
        viewClientsMenu.addMenuItem(new MenuItem("Все клиенты", actionFactory.getAllClientsSortedByNoneAction(), null));

        // Сортировка клиентов
        Menu sortClientsMenu = new Menu("Сортировка клиентов");
        sortClientsMenu.addMenuItem(new MenuItem("По алфавиту", actionFactory.getAllClientsSortedByAlphabetAction(), null));
        sortClientsMenu.addMenuItem(new MenuItem("По дате выезда", actionFactory.getAllClientsSortedByDateEndAction(), null));

        viewClientsMenu.addMenuItem(new MenuItem("Сортировка", null, sortClientsMenu));
        clientsMenu.addMenuItem(new MenuItem("Просмотр клиентов", null, viewClientsMenu));

        return clientsMenu;
    }

    private Menu buildAmenitiesMenu() {
        Menu amenitiesMenu = new Menu("Управление услугами");

        // Основные операции
        amenitiesMenu.addMenuItem(new MenuItem("Добавить услугу", actionFactory.addAmenityAction(), null));
        amenitiesMenu.addMenuItem(new MenuItem("Изменить цену услуги", actionFactory.updateAmenityPriceAction(), null));

        // Просмотр услуг
        Menu viewAmenitiesMenu = new Menu("Просмотр услуг");
        viewAmenitiesMenu.addMenuItem(new MenuItem("Все услуги", actionFactory.getAllAmenityAction(), null));

        // Сортировка услуг
        Menu sortAmenitiesMenu = new Menu("Сортировка услуг");
        sortAmenitiesMenu.addMenuItem(new MenuItem("По цене", actionFactory.getAllAmenitiesSortedByPriceAction(), null));
        sortAmenitiesMenu.addMenuItem(new MenuItem("По названию", actionFactory.getAllAmenitiesSortedByNameAction(), null));

        viewAmenitiesMenu.addMenuItem(new MenuItem("Сортировка", null, sortAmenitiesMenu));
        amenitiesMenu.addMenuItem(new MenuItem("Просмотр услуг", null, viewAmenitiesMenu));

        // Услуги клиента
        Menu clientAmenitiesMenu = new Menu("Услуги клиента");
        clientAmenitiesMenu.addMenuItem(new MenuItem("Все услуги клиента", actionFactory.getAmenitiesClientSortedByNoneAction(), null));
        clientAmenitiesMenu.addMenuItem(new MenuItem("Сортировка по дате", actionFactory.getAmenitiesClientSortedByDateAction(), null));
        clientAmenitiesMenu.addMenuItem(new MenuItem("Сортировка по цене", actionFactory.getAmenitiesClientSortedByPriceAction(), null));

        amenitiesMenu.addMenuItem(new MenuItem("Услуги клиентов", null, clientAmenitiesMenu));

        return amenitiesMenu;
    }

    private Menu buildReportsMenu() {
        Menu reportsMenu = new Menu("Отчеты и аналитика");

        // Основная статистика
        reportsMenu.addMenuItem(new MenuItem("Количество свободных номеров", actionFactory.getTotalCountAvailableRooms(), null));
        reportsMenu.addMenuItem(new MenuItem("Количество клиентов", actionFactory.getTotalServicedClientAction(), null));
        reportsMenu.addMenuItem(new MenuItem("Общий доход", actionFactory.showTotalRevenueAction(), null));

        // История бронирований
        Menu bookingHistoryMenu = new Menu("История бронирований");
        bookingHistoryMenu.addMenuItem(new MenuItem("Последние 3 постояльца", actionFactory.getLastThreeRoomClientsAction(), null));
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

        return operationsMenu;
    }

    public Menu getRootMenu() {
        return rootMenu;
    }

    @Override
    public void execute() {
        buildMenu();
    }
}