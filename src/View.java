import java.util.*;

public class View {
    public void printMenu() {
        System.out.println("________________________________");
        System.out.println("1. Добавить игрушку");
        System.out.println("2. Удалить игрушку");
        System.out.println("3. Редактировать игрушку");
        System.out.println("4. Просмотреть каталог игрушек");
        System.out.println("5. Розыгрыш");
        System.out.println("6. Выход");
        System.out.println("________________________________");
        System.out.print("Введите номер действия: ");
        
    }

    public void printToyCatalog(List<Toy> toys) {
        for (Toy toy : toys) {
            System.out.println(toy.getId() + " - " + toy.getName() + " - " + toy.getWeight());
        }
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
