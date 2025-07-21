package hotelsystem.UI.action.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.Controller.ManagerHotel;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class findClientByIdAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(findClientByIdAction.class);
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);

    public findClientByIdAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        logger.info("Начало выполнения FindClientByIdAction");

        try {
            System.out.println("\n=== Поиск клиента ===");
            System.out.print("Введите ID клиента: ");
            String id = scanner.nextLine().trim();

            if (id.isEmpty()) {
                String errorMsg = "ID клиента не может быть пустым";
                logger.error(errorMsg);
                throw new IllegalArgumentException(errorMsg);
            }

            logger.debug("Поиск клиента с ID: {}", id);

            manager.findClientById(id)
                    .ifPresentOrElse(
                            client -> {
                                System.out.println("\nНайден клиент:\n" + client);
                                logger.info("Клиент с ID {} успешно найден", id);
                            },
                            () -> {
                                String errorMsg = "Клиент с ID '" + id + "' не найден";
                                logger.warn(errorMsg);
                                throw new NoSuchElementException(errorMsg);
                            }
                    );

        } catch (IllegalArgumentException e) {
            logger.error("Ошибка валидации: {}", e.getMessage());
            System.err.println("Ошибка ввода: " + e.getMessage());
        } catch (NoSuchElementException e) {
            logger.error("Ошибка поиска: {}", e.getMessage());
            System.err.println("Ошибка поиска: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Неожиданная ошибка при поиске клиента", e);
            System.err.println("Системная ошибка: " + e.getMessage());
        } finally {
            logger.info("Завершение выполнения FindClientByIdAction");
        }
    }
}