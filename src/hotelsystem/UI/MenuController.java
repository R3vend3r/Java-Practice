package hotelsystem.UI;

import hotelsystem.UI.action_factory.ActionFactory;
import hotelsystem.Controller.ManagerHotel;
import hotelsystem.dependencies.annotation.Inject;
import hotelsystem.dependencies.annotation.PostConstruct;
import lombok.Setter;

import java.util.Scanner;

public class MenuController {
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
        System.out.println("Manager в MenuController: " + dataManager);
        this.navigator = new Navigator(builder.getRootMenu());
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean isRun = true;

        while (isRun) {
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
