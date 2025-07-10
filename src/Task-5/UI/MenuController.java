package UI;

import UI.action_factory.ActionFactory;
import UI.action_factory.ActionFactoryController;
import Controller.ManagerHotel;

import java.util.Scanner;

public class MenuController {
    private static MenuController instance;
    private final ManagerHotel dataManager;
    private Scanner scanner;
    private Builder builder;
    private Navigator navigator;

    private MenuController() {
        this.dataManager = new ManagerHotel("Grand Hotel");
        ActionFactory actionFactory = new ActionFactoryController(dataManager);
        this.builder = new Builder(actionFactory);
        this.navigator = new Navigator(builder.getRootMenu());
    }

    public static MenuController getInstance() {
        if (instance == null) {
            instance = new MenuController();
        }
        return instance;
    }

    public void run(){
        scanner = new Scanner(System.in);
        boolean isRun = true;

        while (isRun){
            navigator.printMenu();
            int number;
            try {
                number = scanner.nextInt();
                scanner.nextLine();
            }
            catch (Exception exception){
                System.out.println("Ошибка ввода! ");
                scanner.nextLine();
                continue;
            }
            if (number == 0){
                if (navigator.isEmpty()){
                    isRun = false;
                }
                else{
                    navigator.backMenu();
                }
            }
            else{
                navigator.navigate(number);
            }
        }
    }
}
