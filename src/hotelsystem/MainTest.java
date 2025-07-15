package hotelsystem;

import hotelsystem.Controller.ManagerHotel;
import hotelsystem.UI.Builder;
import hotelsystem.UI.MenuController;
import hotelsystem.UI.action_factory.ActionFactory;
import hotelsystem.dependencies.context.AppContext;
import hotelsystem.dependencies.factory.BeanFactory;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainTest {
    public AppContext run() {
        AppContext applicationContext = new AppContext();
        BeanFactory beanFactory = new BeanFactory(applicationContext);
        applicationContext.setBeanFactory(beanFactory);

        return applicationContext;
    }

    public static void main(String[] args) {
        BasicConfigurator.configure();
        MainTest main = new MainTest();
        AppContext applicationContext = main.run();

        ManagerHotel dataManager = applicationContext.getBean(ManagerHotel.class);
        //System.out.println("DataManager в Main: " + dataManager);

        dataManager.loadStateFromJson("hotel_db.json");

        Builder builder = applicationContext.getBean(Builder.class);
        builder.buildMenu();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Сохраняем состояние...");
            dataManager.saveStateToJson("hotel_db.json");
        }));

        MenuController menuController = applicationContext.getBean(MenuController.class);
        menuController.run();
    }
}
