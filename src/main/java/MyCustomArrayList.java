import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class MyCustomArrayList<T> implements Iterable<T> {
    private T[] data;
    private int size;

    @SuppressWarnings("unchecked")
    public MyCustomArrayList(int capacity) {
        data = (T[]) new Object[capacity];
        size = 0;
    }

    public void add(T element) {
        if (size == data.length) {
            resize();
        }
        data[size++] = element;
    }

    public void add(int index, T element) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        if (size == data.length) {
            resize();
        }
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = element;
        size++;
    }

    public T get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        return data[index];
    }

    public void remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(data, index + 1, data, index, numMoved);
        }
        data[--size] = null;
    }

    public int indexOf(T element) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(element)) return i;
        }
        return -1;
    }

    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        T[] newData = (T[]) new Object[data.length * 2];
        System.arraycopy(data, 0, newData, 0, data.length);
        data = newData;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int cursor = 0;
            private int lastRet = -1;

            @Override
            public boolean hasNext() {
                return cursor < size;
            }

            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                lastRet = cursor;
                return data[cursor++];
            }

            @Override
            public void remove() {
                if (lastRet < 0) throw new IllegalStateException();
                int numMoved = size - lastRet - 1;
                if (numMoved > 0) {
                    System.arraycopy(data, lastRet + 1, data, lastRet, numMoved);
                }
                data[--size] = null;
                cursor = lastRet;
                lastRet = -1;
            }
        };
    }

    // 🔹 Меню
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MyCustomArrayList<String> list = new MyCustomArrayList<>(2);

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