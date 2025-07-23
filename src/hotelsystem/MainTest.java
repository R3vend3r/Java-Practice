package hotelsystem;

import hotelsystem.Controller.ManagerHotel;
import hotelsystem.UI.Builder;
import hotelsystem.UI.MenuController;
import hotelsystem.dependencies.context.AppContext;
import hotelsystem.dependencies.factory.BeanFactory;
//import org.apache.log4j.BasicConfigurator;

public class MainTest {

    public AppContext initializeContext() {
        AppContext context = new AppContext();
        BeanFactory beanFactory = new BeanFactory(context);
        context.setBeanFactory(beanFactory);
        return context;
    }

    public static void main(String[] args) {

        MainTest app = new MainTest();
        AppContext context = app.initializeContext();

        ManagerHotel dataManager = context.getBean(ManagerHotel.class);

        Builder builder = context.getBean(Builder.class);
        builder.buildMenu();

        MenuController menuController = context.getBean(MenuController.class);
        menuController.run();
    }
}