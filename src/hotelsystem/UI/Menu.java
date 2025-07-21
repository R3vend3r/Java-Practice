package hotelsystem.UI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

public class Menu {
    private static final Logger logger = LoggerFactory.getLogger(Menu.class);
    private String name;
    private List<MenuItem> menuItems;

    public Menu(String name) {
        this.name = name;
        this.menuItems = new ArrayList<>();
        logger.debug("Создано меню: {}", name);
    }

    public String getName() {
        return name;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void addMenuItem(MenuItem menuItem) {
        logger.debug("Добавлен пункт '{}' в меню '{}'", menuItem.getTitle(), name);
        this.menuItems.add(menuItem);
    }
}