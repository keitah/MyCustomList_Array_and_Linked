import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MyCustomArrayList<E extends Comparable<E>> {

    private Comparable[] elements;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public MyCustomArrayList() {
        elements = new Comparable[DEFAULT_CAPACITY];
    }

    public void add(E element) {
        ensureCapacity(size + 1);
        elements[size++] = element;
        sort();
    }

    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        ensureCapacity(size + 1);
        for (int i = size; i > index; i--) {
            elements[i] = elements[i - 1];
        }
        elements[index] = element;
        size++;
        sort();
    }

    @SuppressWarnings("unchecked")
    public E get(int index) {
        checkIndex(index);
        return (E) elements[index];
    }

    @SuppressWarnings("unchecked")
    public E remove(int index) {
        checkIndex(index);
        E removed = (E) elements[index];
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        elements[--size] = null;
        return removed;
    }

    public int indexOf(E value) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void sort() {
        Arrays.sort(elements, 0, size);
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity > elements.length) {
            int newCapacity = elements.length * 2;
            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }
            elements = Arrays.copyOf(elements, newCapacity);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOf(elements, size));
    }

    public static void main(String[] args) {
        MyCustomArrayList<String> list = new MyCustomArrayList<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1 - Добавить в конец");
            System.out.println("2 - Добавить по индексу");
            System.out.println("3 - Показать список");
            System.out.println("4 - Получить по индексу");
            System.out.println("5 - Удалить по индексу");
            System.out.println("6 - Найти элемент");
            System.out.println("0 - Выход");
            System.out.print("Выберите действие: ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: введите число!");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Введите элемент: ");
                    list.add(scanner.nextLine());
                    break;
                case 2:
                    try {
                        System.out.print("Введите индекс: ");
                        int idxAdd = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Введите элемент: ");
                        list.add(idxAdd, scanner.nextLine());
                    } catch (InputMismatchException e) {
                        System.out.println("Ошибка: индекс должен быть числом!");
                        scanner.nextLine();
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Ошибка: индекс вне диапазона!");
                    }
                    break;
                case 3:
                    System.out.println("Список: " + list);
                    break;
                case 4:
                    try {
                        System.out.print("Введите индекс: ");
                        int idxGet = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Элемент: " + list.get(idxGet));
                    } catch (InputMismatchException e) {
                        System.out.println("Ошибка: индекс должен быть числом!");
                        scanner.nextLine();
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Ошибка: индекс вне диапазона!");
                    }
                    break;
                case 5:
                    try {
                        System.out.print("Введите индекс: ");
                        int idxRemove = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Удален: " + list.remove(idxRemove));
                    } catch (InputMismatchException e) {
                        System.out.println("Ошибка: индекс должен быть числом!");
                        scanner.nextLine();
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Ошибка: индекс вне диапазона!");
                    }
                    break;
                case 6:
                    System.out.print("Введите элемент для поиска: ");
                    String searchVal = scanner.nextLine();
                    int foundIndex = list.indexOf(searchVal);
                    if (foundIndex != -1) {
                        System.out.println("Элемент найден на позиции: " + foundIndex);
                    } else {
                        System.out.println("Элемент не найден!");
                    }
                    break;
                case 0:
                    System.out.println("Выход...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Неверный выбор!");
            }
        }
    }
}
