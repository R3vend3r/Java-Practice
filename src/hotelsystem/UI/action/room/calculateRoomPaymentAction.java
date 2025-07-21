package hotelsystem.UI.action.room;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.Controller.ManagerHotel;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class calculateRoomPaymentAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(calculateRoomPaymentAction.class);
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);

    public calculateRoomPaymentAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        logger.info("Начало расчета оплаты за номер");
        try {
            System.out.print("\nРасчет оплаты\nНомер комнаты: ");
            int roomNumber = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Дата выезда (дд.мм.гггг): ");
            String endDate = scanner.nextLine();

            double cost = manager.calculateRoomPayment(roomNumber,
                    new SimpleDateFormat("dd.MM.yyyy").parse(endDate));

            logger.info("Рассчитана стоимость для номера {}: {} руб.", roomNumber, cost);
            System.out.printf("Итого к оплате: %.2f руб.%n", cost);
        } catch (Exception e) {
            logger.error("Ошибка расчета оплаты: {}", e.getMessage());
            System.out.println("Ошибка расчета");
        }
    }
}