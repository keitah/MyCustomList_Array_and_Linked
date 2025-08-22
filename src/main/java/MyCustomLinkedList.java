import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class MyCustomLinkedList<T> implements Iterable<T> {

    private static class Node<T> {
        T value;
        Node<T> next;
        Node<T> prev;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        size++;
    }

    public void add(int index, T value) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        Node<T> newNode = new Node<>(value);
        if (index == size) { // вставка в конец
            add(value);
            return;
        }
        Node<T> current = getNode(index);
        Node<T> prev = current.prev;

        newNode.next = current;
        newNode.prev = prev;
        current.prev = newNode;

        if (prev == null) {
            head = newNode;
        } else {
            prev.next = newNode;
        }
        size++;
    }

    public T get(int index) {
        return getNode(index).value;
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) current = current.next;
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) current = current.prev;
        }
        return current;
    }

    public void remove(int index) {
        Node<T> target = getNode(index);
        Node<T> prev = target.prev;
        Node<T> next = target.next;

        if (prev == null) head = next;
        else prev.next = next;

        if (next == null) tail = prev;
        else next.prev = prev;

        size--;
    }

    public int indexOf(T value) {
        Node<T> current = head;
        int i = 0;
        while (current != null) {
            if (current.value.equals(value)) return i;
            current = current.next;
            i++;
        }
        return -1;
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = head;
            private Node<T> lastRet = null;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                lastRet = current;
                current = current.next;
                return lastRet.value;
            }

            @Override
            public void remove() {
                if (lastRet == null) throw new IllegalStateException();

                Node<T> prev = lastRet.prev;
                Node<T> next = lastRet.next;

                if (prev == null) head = next;
                else prev.next = next;

                if (next == null) tail = prev;
                else next.prev = prev;

                size--;
                lastRet = null;
            }
        };
    }

    // 🔹 Меню
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MyCustomLinkedList<String> list = new MyCustomLinkedList<>();

        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1 - Добавить в конец");
            System.out.println("2 - Добавить по индексу");
            System.out.println("3 - Показать список");
            System.out.println("4 - Получить по индексу");
            System.out.println("5 - Удалить по индексу");
            System.out.println("6 - Найти элемент");
            System.out.println("7 - Показать с помощью итератора");
            System.out.println("8 - Удалить элемент через итератор");
            System.out.println("0 - Выход");
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1" -> {
                        System.out.print("Введите элемент: ");
                        String el = scanner.nextLine();
                        list.add(el);
                    }
                    case "2" -> {
                        System.out.print("Введите индекс: ");
                        int idx = Integer.parseInt(scanner.nextLine());
                        System.out.print("Введите элемент: ");
                        String el = scanner.nextLine();
                        list.add(idx, el);
                    }
                    case "3" -> {
                        System.out.println("Список:");
                        for (String s : list) {
                            System.out.println(s);
                        }
                    }
                    case "4" -> {
                        System.out.print("Введите индекс: ");
                        int idx = Integer.parseInt(scanner.nextLine());
                        System.out.println("Элемент: " + list.get(idx));
                    }
                    case "5" -> {
                        System.out.print("Введите индекс: ");
                        int idx = Integer.parseInt(scanner.nextLine());
                        list.remove(idx);
                    }
                    case "6" -> {
                        System.out.print("Введите элемент: ");
                        String el = scanner.nextLine();
                        int pos = list.indexOf(el);
                        if (pos >= 0) {
                            System.out.println("Элемент найден по индексу: " + pos);
                        } else {
                            System.out.println("Элемент не найден");
                        }
                    }
                    case "7" -> {
                        System.out.println("Обход итератором:");
                        Iterator<String> it = list.iterator();
                        while (it.hasNext()) {
                            System.out.println(it.next());
                        }
                    }
                    case "8" -> {
                        System.out.print("Введите элемент для удаления через итератор: ");
                        String target = scanner.nextLine();
                        Iterator<String> it = list.iterator();
                        boolean removed = false;
                        while (it.hasNext()) {
                            if (it.next().equals(target)) {
                                it.remove();
                                removed = true;
                                break;
                            }
                        }
                        if (removed) {
                            System.out.println("Удалено.");
                        } else {
                            System.out.println("Элемент не найден.");
                        }
                    }
                    case "0" -> {
                        System.out.println("Выход...");
                        return;
                    }
                    default -> System.out.println("Неверный выбор!");
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Ошибка: индекс вне диапазона!");
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: ожидалось число!");
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }
}