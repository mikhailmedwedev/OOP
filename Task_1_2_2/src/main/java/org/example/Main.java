package org.example;

/**
 * Главный класс.
 */
public class Main {

    /**
     * Точка входа.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        HashTable<String, Integer> table = new HashTable<>();

        table.put("A", 1);
        table.put("B", 2);
        table.put("C", 3);
        System.out.println("Таблица: " + table);
        System.out.println("Размер: " + table.size());

        System.out.println("Значение по ключу 'B': " + table.get("B"));
        table.put("B", 20);
        System.out.println("Обновленное значение 'B': " + table.get("B"));

        table.put(null, 999);
        System.out.println("Значение по null-ключу: " + table.get(null));

        System.out.println("\nЭлементы в таблице:");
        for (HashTable.Node<String, Integer> node : table) {
            System.out.println(node.getKey() + " = " + node.getValue());
        }

        table.remove("A");
        System.out.println("\nПосле удаления 'A': " + table);
        System.out.println("Новый размер: " + table.size());
    }
}