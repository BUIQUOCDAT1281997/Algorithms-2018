package lesson1;

import kotlin.NotImplementedError;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     * <p>
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС,
     * каждый на отдельной строке. Пример:
     * <p>
     * 13:15:19
     * 07:26:57
     * 10:00:03
     * 19:56:14
     * 13:15:19
     * 00:40:31
     * <p>
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС. Одинаковые моменты времени выводить друг за другом. Пример:
     * <p>
     * 00:40:31
     * 07:26:57
     * 10:00:03
     * 13:15:19
     * 13:15:19
     * 19:56:14
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortTimes(String inputName, String outputName) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(new File(inputName)));
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(outputName));
        String time = br.readLine();
        List<Integer> list = new ArrayList<>();
        while (time != null) {
            if (!time.matches("(([01]\\d)|(2[0-4])):[0-5]\\d:[0-5]\\d")) throw new Exception("");
            list.add(Integer.parseInt(time.substring(0, 2)) * 3600 +
                    Integer.parseInt(time.substring(3, 5)) * 60 +
                    Integer.parseInt(time.substring(6, 8))
            );
            time = br.readLine();
        }
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) array[i] = list.get(i);
        Sorts.quickSort(array);
        for (int element : array) {
            dos.writeBytes(String.format("%02d:%02d:%02d", element / 3600, (element % 3600) / 60, element % 60) + "\n");
        }
        dos.close();
    }

    /**
     * Сортировка адресов
     * <p>
     * Средняя
     * <p>
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     * <p>
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     * <p>
     * Людей в городе может быть до миллиона.
     * <p>
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     * <p>
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortAddresses(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Сортировка температур
     * <p>
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     * <p>
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     * <p>
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     * <p>
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
    static public void sortTemperatures(String inputName, String outputName) throws IOException {
        File file = new File(inputName);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        List<Integer> list = new ArrayList<>();
        String tem = br.readLine();
        while (tem != null) {
            list.add((int) (Double.parseDouble(tem) * 10));
            tem = br.readLine();
        }
        int[] array = new int[list.size()];
       for (int i=0;i<list.size();i++) array[i]=list.get(i);
        Sorts.quickSort(array);
        File f = new File(outputName);
        FileWriter fw = new FileWriter(f);
        for (int e : array) {
            fw.write(String.valueOf(((double) e) / 10) + "\n");
        }
        fw.close();
    }

    /**
     * Сортировка последовательности
     * <p>
     * Средняя
     * (Задача взята с сайта acmp.ru)
     * <p>
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     * <p>
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     * <p>
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     * <p>
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    static public void sortSequence(String inputName, String outputName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File(inputName)));
        List<Integer> list = new ArrayList<>();
        String str = br.readLine();
        while (str != null) {
            list.add(Integer.parseInt(str));
            str = br.readLine();
        }
        int[] array = new int[list.size()];
        for (int j = 0; j < list.size(); j++) array[j] = list.get(j);
        Sorts.quickSort(array);
        int maxQuantity = 1;
        int countQuantity = 1;
        int value = array[0];
        for (int i = 1; i < array.length; i++) {
            if ((array[i] != array[i - 1]) || (i == array.length - 1)) {
                if (i == array.length - 1) countQuantity++;
                if (countQuantity > maxQuantity || (countQuantity == maxQuantity && value > array[i - 1])) {
                    value = array[i - 1];
                    maxQuantity = countQuantity;
                }
                countQuantity = 0;
            }
            countQuantity++;
        }
        FileWriter fw = new FileWriter(new File(outputName));
        for (int element : list) {
            if (element != value) fw.write(String.valueOf(element) + "\n");
        }
        for (int j = 1; j < maxQuantity + 1; j++) fw.write(String.valueOf(value) + "\n");
        fw.close();
    }

    /**
     * Соединить два отсортированных массива в один
     * <p>
     * Простая
     * <p>
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     * <p>
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     * <p>
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        List<T> list = new ArrayList<>();
        int i = 0;
        int j = first.length;
        while (i < first.length || j < second.length) {
            if (i>=first.length){
                list.add(second[j]);
                j++;
                continue;
            }if (j>=second.length){
                list.add(first[i]);
                i++;
                continue;
            }
            if (first[i].compareTo(second[j]) < 0) {
                list.add(first[i]);
                i++;
            } else {
                list.add(second[j]);
                j++;
            }
        }
        for (int e = 0; e < list.size(); e++) second[e] = list.get(e);
    }
}
