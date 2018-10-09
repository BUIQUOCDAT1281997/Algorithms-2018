package lesson1;

import kotlin.NotImplementedError;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     *
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС,
     * каждый на отдельной строке. Пример:
     *
     * 13:15:19
     * 07:26:57
     * 10:00:03
     * 19:56:14
     * 13:15:19
     * 00:40:31
     *
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС. Одинаковые моменты времени выводить друг за другом. Пример:
     *
     * 00:40:31
     * 07:26:57
     * 10:00:03
     * 13:15:19
     * 13:15:19
     * 19:56:14
     *
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
        int[] listTime = list.stream().mapToInt(Integer::intValue).toArray();
        Sorts.quickSort(listTime);
        for (int element : listTime) {
            dos.writeBytes(String.format("%02d:%02d:%02d", element / 3600, (element % 3600) / 60, element % 60) + "\n");
        }
        dos.close();
    }

    /**
     * Сортировка адресов
     *
     * Средняя
     *
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     *
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     *
     * Людей в городе может быть до миллиона.
     *
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     *
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortAddresses(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Сортировка температур
     *
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     *
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     *
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     *
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
        int[] array = list.stream().mapToInt(i -> i).toArray();
        Sorts.quickSort(array);
        File f = new File(outputName);
        FileWriter fw = new FileWriter(f);
        for (int e : array) {
            fw.write(String.valueOf(((double)e)/10)+"\n");
        }
        fw.close();
    }

    /**
     * Сортировка последовательности
     *
     * Средняя
     * (Задача взята с сайта acmp.ru)
     *
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     *
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     *
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     *
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
        int[] array = list.stream().mapToInt(i -> i).toArray();
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
     *
     * Простая
     *
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     *
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     *
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
     static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        for (int i=0; i<first.length; i++){
            second[i]=first[i];
        }
        Arrays.sort(second);
    }
}
