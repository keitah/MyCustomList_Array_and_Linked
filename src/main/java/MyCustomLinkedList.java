import java.util.InputMismatchException;
import java.util.Scanner;

public class MyCustomLinkedList<E> {

    private Node<E> head;
    private Node<E> tail;
    private int size;

    private static class Node<E> {
        E data;
        Node<E> next;
        Node<E> prev;

        Node(E data) {
            this.data = data;
        }
    }

    // Добавление в конец
    public void add(E element) {
        Node<E> newNode = new Node<>(element);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    // Добавление в начало
    public void addFirst(E element) {
        Node<E> newNode = new Node<>(element);
        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    // Получение по индексу
    public E get(int index) {
        checkIndex(index);
        Node<E> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) current = current.next;
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) current = current.prev;
        }
        return current.data;
    }

    // Удаление по индексу
    public E remove(int index) {
        checkIndex(index);
        Node<E> toRemove;
        if (index == 0) {
            toRemove = head;
            head = head.next;
            if (head != null) head.prev = null;
            else tail = null;
        } else if (index == size - 1) {
            toRemove = tail;
            tail = tail.prev;
            if (tail != null) tail.next = null;
            else head = null;
        } else {
            toRemove = head;
            for (int i = 0; i < index; i++) toRemove = toRemove.next;
            toRemove.prev.next = toRemove.next;
            toRemove.next.prev = toRemove.prev;
        }
        size--;
        return toRemove.data;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> current = head;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) sb.append(", ");
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }

    // Запуск с безопасным вводом
    public static void main(String[] args) {
        MyCustomLinkedList<String> list = new MyCustomLinkedList<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1 - Добавить в конец");
            System.out.println("2 - Добавить в начало");
            System.out.println("3 - Показать список");
            System.out.println("4 - Получить по индексу");
            System.out.println("5 - Удалить по индексу");
            System.out.println("0 - Выход");
            System.out.print("Выберите действие: ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // очистка буфера
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
                    System.out.print("Введите элемент: ");
                    list.addFirst(scanner.nextLine());
                    break;
                case 3:
                    System.out.println("Список: " + list);
                    break;
                case 4:
                    System.out.print("Введите индекс: ");
                    try {
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
                    System.out.print("Введите индекс: ");
                    try {
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
