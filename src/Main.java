import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static View view = new View();
    static Model model = new Model();
    static String encoding = System.getProperty("console.encoding", "UTF-8");
    static Scanner scanner = new Scanner(System.in, encoding);
    public static void main(String[] args) {
        //View view = new View();
        //Model model = new Model();
        // ToyCatalogController controller = new ToyCatalogController(model, view);

        System.out.print("\033[H\033[J");
        boolean running = true;

        while (running) {
            view.printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addToy();
                    break;
                case 2:
                    removeToy();
                    break;
                case 3:
                    editToy();
                    break;
                case 4:
                    view.printToyCatalog(model.getToys());
                    break;
                case 5:
                    runLottery();
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    view.printMessage("Неверный выбор. Пожалуйста, введите номер действия от 1 до 6.");
                    break;
            }
        }

        saveToysToFile("toys.txt");
        view.printMessage("Программа завершена.");
    }
   
    public static void addToy() {
        //Scanner scanner = new Scanner(System.in);
        System.out.print("Введите ID игрушки: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Введите название игрушки: ");
        String name = scanner.nextLine();
        System.out.print("Введите частоту выпадения игрушки: ");
        int weight = scanner.nextInt();
        scanner.nextLine(); 

        model.addToy(new Toy(id, name, weight));
        view.printMessage("Игрушка успешно добавлена в каталог.");
     }

    private static void removeToy() {
        System.out.print("Введите ID игрушки, которую нужно удалить: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        model.removeToy(id);
        view.printMessage("Игрушка успешно удалена из каталога.");
    }

    private static void editToy() {
        System.out.print("Введите ID игрушки, которую нужно отредактировать: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Введите новое название игрушки: ");
        String newName = scanner.nextLine();
        System.out.print("Введите новую частоту выпадения игрушки: ");
        int newWeight = scanner.nextInt();
        scanner.nextLine();

        model.editToy(id, newName, newWeight);
        view.printMessage("Игрушка успешно отредактирована.");
    }

    private static void runLottery() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("results.txt"), "UTF-8"))) {
            Random random = new Random();
            for (int i = 0; i < 10; i++) {
                int totalWeight = 0;
                for (Toy toy : model.getToys()) {
                    totalWeight += toy.getWeight();
                }

                int randomNumber = random.nextInt(totalWeight) + 1;
                int currentWeight = 0;
                for (Toy toy : model.getToys()) {
                    currentWeight += toy.getWeight();
                    if (randomNumber <= currentWeight) {
                        String result = toy.getId() + " - " + toy.getName();
                        view.printMessage(result);
                        writer.write(result);
                        writer.newLine();
                        break;
                    }
                }
            }
        } catch (IOException e) {
            view.printMessage("Ошибка при записи результатов розыгрыша: " + e.getMessage());
        }
    }

    private static void saveToysToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"))) {
            for (Toy toy : model.getToys()) {
                writer.write(toy.getId() + " " + toy.getName() + " " + toy.getWeight());
                writer.newLine();
            }
        } catch (IOException e) {
            view.printMessage("Ошибка при сохранении игрушек в файл: " + e.getMessage());
        }
    }
}
