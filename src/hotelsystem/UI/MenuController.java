package hotelsystem.UI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.Controller.ManagerHotel;
import hotelsystem.UI.action_factory.ActionFactory;
import hotelsystem.dependencies.annotation.Component;
import hotelsystem.dependencies.annotation.Inject;
import hotelsystem.dependencies.annotation.PostConstruct;

import java.util.Scanner;

@Component
public class MenuController {
    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Inject
    private ManagerHotel dataManager;

    @Inject
    private ActionFactory actionFactory;

    @Inject
    private Builder builder;

    private Navigator navigator;
    private final Object navigatorLock = new Object();
    private boolean initialized = false;

    @PostConstruct
    public void init() {
        if (initialized) {
            return;
        }

        logger.info("Инициализация MenuController");
        logger.debug("Manager в MenuController: {}", dataManager);

        // Гарантируем, что меню построено
        builder.getRootMenu();

        initialized = true;
    }

    private Navigator getNavigator() {
        if (navigator == null) {
            synchronized (navigatorLock) {
                if (navigator == null) {
                    navigator = new Navigator(builder.getRootMenu());
                }
            }
        }
        return navigator;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean isRun = true;
        Navigator currentNavigator = getNavigator();

        while (isRun) {
            currentNavigator.printMenu();

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                logger.debug("Пользователь выбрал пункт: {}", choice);
            } catch (Exception e) {
                logger.error("Ошибка ввода: {}", e.getMessage());
                System.out.println("Ошибка ввода! Пожалуйста, введите число.");
                scanner.nextLine();
                continue;
            }

            if (choice == 0) {
                if (currentNavigator.isEmpty()) {
                    logger.info("Завершение работы меню");
                    isRun = false;
                } else {
                    logger.debug("Возврат в предыдущее меню");
                    currentNavigator.backMenu();
                }
            } else {
                currentNavigator.navigate(choice);
            }
        }

        scanner.close();
    }
}