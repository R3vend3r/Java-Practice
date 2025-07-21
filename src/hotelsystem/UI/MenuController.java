package hotelsystem.UI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.Controller.ManagerHotel;
import hotelsystem.UI.action_factory.ActionFactory;
import hotelsystem.dependencies.annotation.Inject;
import hotelsystem.dependencies.annotation.PostConstruct;
import java.util.Scanner;

public class MenuController {
    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Inject
    private ManagerHotel dataManager;

    @Inject
    private ActionFactory actionFactory;

    @Inject
    private Builder builder;

    @Inject
    private Navigator navigator;

    @PostConstruct
    public void init() {
        logger.info("Инициализация MenuController");
        logger.debug("Manager в MenuController: {}", dataManager);
        this.navigator = new Navigator(builder.getRootMenu());
    }

    public void run() {
        logger.info("Запуск меню");
        Scanner scanner = new Scanner(System.in);
        boolean isRun = true;

        while (isRun) {
            navigator.printMenu();
            int number;
            try {
                number = scanner.nextInt();
                scanner.nextLine();
                logger.debug("Пользователь выбрал пункт: {}", number);
            }
            catch (Exception exception) {
                logger.error("Ошибка ввода: {}", exception.getMessage());
                System.out.println("Ошибка ввода! ");
                scanner.nextLine();
                continue;
            }

            if (number == 0) {
                if (navigator.isEmpty()) {
                    logger.info("Завершение работы меню");
                    isRun = false;
                }
                else {
                    logger.debug("Возврат в предыдущее меню");
                    navigator.backMenu();
                }
            }
            else {
                navigator.navigate(number);
            }
        }
    }
}