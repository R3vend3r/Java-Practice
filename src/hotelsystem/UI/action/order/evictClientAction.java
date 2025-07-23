package hotelsystem.UI.action.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.Controller.ManagerHotel;

import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class evictClientAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(evictClientAction.class);
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);

    public evictClientAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        logger.info("Начало процесса выселения клиента");
        try {
            System.out.println("\n=== Выселение клиента ===");
            System.out.print("Номер комнаты: ");
            int roomNumber = scanner.nextInt();
            scanner.nextLine();

            manager.findClientByRoom(roomNumber).ifPresentOrElse(
                    client -> {
                        logger.info("Найден клиент в комнате {}: {}", roomNumber, client);
                        System.out.println("Клиент: " + client);
                        System.out.print("Выселить (да/нет)? ");
                        if(scanner.nextLine().equalsIgnoreCase("да")) {
                                manager.evictClient(roomNumber);
                                logger.info("Клиент успешно выселен из комнаты {}", roomNumber);
                                System.out.println("Клиент выселен");
                        } else {
                            logger.info("Выселение клиента отменено пользователем");
                        }
                    },
                    () -> {
                        logger.warn("Попытка выселения из свободной комнаты {}", roomNumber);
                        System.out.println("Номер свободен или не существует");
                    }
            );
        } catch (IllegalArgumentException e) {
            logger.error("Ошибка ввода данных: {}", e.getMessage());
            System.err.println("Ошибка ввода: " + e.getMessage());
        } catch (NoSuchElementException e) {
            logger.error("Клиент не найден: {}", e.getMessage());
            System.err.println("Ошибка поиска: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Неожиданная ошибка при выселении клиента", e);
            System.err.println("Ошибка: " + e.getMessage());
        } finally {
            logger.info("Завершение процесса выселения клиента");
        }
    }
}