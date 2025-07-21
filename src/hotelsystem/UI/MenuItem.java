package hotelsystem.UI;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;

@Getter
public class MenuItem {
    private static final Logger logger = LoggerFactory.getLogger(MenuItem.class);
    private String title;
    private Action action;
    private Menu nextMenu;

    public MenuItem(String title, Action action, Menu nextMenu) {
        this.title = title;
        this.action = action;
        this.nextMenu = nextMenu;
        logger.debug("Создан пункт меню: {}", title);
    }

    public void doAction() {
        if (action != null) {
            logger.info("Выполнение действия: {}", title);
            action.execute();
        }
    }
}