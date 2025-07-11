package UI;

import java.util.List;
import java.util.Stack;

public class Navigator {
    private Menu currentMenu;
    private final Stack<Menu> history;

    public Navigator(Menu currentMenu) {
        this.currentMenu = currentMenu;
        history = new Stack<>();
    }

    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }

    public Menu getCurrentMenu() {
        return currentMenu;
    }

    public boolean isEmpty(){
        return history.isEmpty();
    }

    public void printMenu(){
        System.out.println("Это раздел " + currentMenu.getName());
        System.out.println("Выберите действие");

        int index = 1;
        for(MenuItem item: currentMenu.getMenuItems()){
            System.out.println(index + " - "+ item.getTitle());
            index++;
        }
        System.out.println("0 - Назад");
    }

    public void navigate(int number){
        List<MenuItem> itemList = currentMenu.getMenuItems();
        if(number< 1 || number >itemList.size() ){
            System.out.println("Некорректный ввод!");
            return;
        }
        MenuItem selectNumber = itemList.get(number-1);

        if(selectNumber.getAction() != null)
            selectNumber.getAction().execute();

        if (selectNumber.getNextMenu()!= null){
            history.push(currentMenu);
            currentMenu = selectNumber.getNextMenu();
        }
    }

    public void backMenu(){
        if (!history.isEmpty()){
            currentMenu = history.pop();
        }
        else{
            System.out.println("Это главное меню.");
        }
    }
}
