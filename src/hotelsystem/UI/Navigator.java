package hotelsystem.UI;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.dependencies.annotation.Inject;
import java.util.List;
import java.util.Stack;

public class Navigator {
    private static final Logger logger = LoggerFactory.getLogger(Navigator.class);
    @Getter
    @Setter
    private Menu currentMenu;
    private final Stack<Menu> history = new Stack<>();

    public Navigator() {}

    @Inject
    public Navigator(Menu currentMenu) {
        logger.debug("Инициализация Navigator с меню: {}", currentMenu.getName());
        this.currentMenu = currentMenu;
    }

    public boolean isEmpty() {
        return history.isEmpty();
    }

    public void printMenu() {
        logger.debug("Отображение меню: {}", currentMenu.getName());
        System.out.println("\n=== " + currentMenu.getName() + " ===");
        System.out.println("Выберите действие:");

        int index = 1;
        for(MenuItem item: currentMenu.getMenuItems()) {
            System.out.println(index + " - "+ item.getTitle());
            index++;
        }
        System.out.println("0 - " + (history.isEmpty() ? "Выход" : "Назад"));
    }

    public void navigate(int number) {
        List<MenuItem> itemList = currentMenu.getMenuItems();
        if(number < 1 || number > itemList.size()) {
            logger.warn("Некорректный выбор пункта меню: {}", number);
            System.out.println("Некорректный ввод!");
            return;
        }

        MenuItem selectedItem = itemList.get(number-1);
        logger.debug("Выбран пункт: {}", selectedItem.getTitle());

        if(selectedItem.getAction() != null) {
            logger.info("Выполнение действия для: {}", selectedItem.getTitle());
            selectedItem.getAction().execute();
        }

        if (selectedItem.getNextMenu() != null) {
            logger.debug("Переход в подменю: {}", selectedItem.getNextMenu().getName());
            history.push(currentMenu);
            currentMenu = selectedItem.getNextMenu();
        }
    }

    public void backMenu() {
        if (!history.isEmpty()) {
            Menu previousMenu = history.pop();
            logger.debug("Возврат в меню: {}", previousMenu.getName());
            currentMenu = previousMenu;
        }
        else {
            logger.warn("Попытка вернуться из главного меню");
            System.out.println("Это главное меню.");
        }
    }
}