import java.io.*;
import java.util.StringTokenizer;

/*Первоначальное Задание:
В текстовом файле input.txt в записаны строки, в которых есть скобки.
Удалить в каждой строке текст в одинарных скобках и эти скобки.
Если есть вложенность, текст во вложенных скобках  не изменять.
Использовать  String,  StringBuffer и StringTokenizer.

Программа:
Программа использует String,  StringBuffer и StringTokenizer.
Открывает файл input.txt и считывает его содержимое построчно с помощью BufferedReader.
 Для каждой строки вызывает метод removeSingleBracketsContent, который:
Разделяет строку на токены с помощью StringTokenizer, используя символы ( и ) как разделители.
Удаляет текст, находящийся внутри одинарных скобок, включая сами скобки, сохраняя остальной текст.
Записывает очищенные строки в файл output.txt с помощью BufferedWriter.
После завершения обработки выводит содержимое как входного, так и выходного файлов на консоль.
В случае возникновения ошибок при чтении или записи файлов программа выводит соответствующее сообщение об ошибке.
 */

/*
Обновлённое задание: 
В текстовом файле input.txt содержатся строки, в которых могут присутствовать одинарные скобки. Вам необходимо:

Удалить текст, находящийся внутри одинарных скобок, включая сами скобки.
Если есть вложенные скобки, текст во вложенных скобках не должен изменяться.
Программа должна использовать:
String для работы с текстом.
StringBuffer для создания очищенных строк.
StringTokenizer для разбиения строк на токены.
*/
public class Main {
    public static void main(String[] args) {
        BufferedReader br = null;
        BufferedWriter bw = null;

        try {
            String inputFilePath = args.length > 0 ? args[0] : "input.txt"; // Путь к входному файлу
            File inputFile = new File(inputFilePath);
            File outputFile = new File("output.txt"); // Путь к выходному файлу

            // Проверяем, существует ли файл input.txt
            if (!inputFile.exists()) {
                System.out.println("Файл " + inputFile.getName() + " не найден.");
                return;
            }

            br = new BufferedReader(new FileReader(inputFile)); // Создаем BufferedReader для чтения файла
            bw = new BufferedWriter(new FileWriter(outputFile)); // Создаем BufferedWriter для записи в файл

            String line;
            // Читаем файл построчно
            while ((line = br.readLine()) != null) {
                String cleanedLine = removeSingleBracketsContent(line); // Удаляем содержимое одинарных скобок
                bw.write(cleanedLine); // Записываем очищенную строку в выходной файл
                bw.newLine(); // Переход на новую строку
            }

            // Выводим содержимое входного и выходного файлов на консоль
            System.out.println("Содержимое файла " + inputFile.getName() + ":");
            printFileContent(inputFile);
            System.out.println("\nСодержимое файла output.txt:");
            printFileContent(outputFile);
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage()); // Обработка исключений при чтении/записи файла
        } finally {
            // Закрываем BufferedReader и BufferedWriter, если они были открыты
            try {
                if (br != null) {
                    br.close();
                }
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                System.out.println("Ошибка при закрытии файлов: " + e.getMessage());
            }
        }
    }

    // Метод для удаления содержимого одинарных скобок
    public static String removeSingleBracketsContent(String line) {
        StringBuffer result = new StringBuffer();
        StringTokenizer tokenizer = new StringTokenizer(line, "()"); // Разделяем строку по скобкам
        boolean insideSingleBracket = false; // Флаг для отслеживания состояния скобок

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim(); // Получаем следующий токен и убираем пробелы
            if (insideSingleBracket) {
                // Если мы внутри одинарных скобок, продолжаем игнорировать токены
                if (token.contains(")")) {
                    insideSingleBracket = false; // Закрывающая скобка найдена
                }
                continue;
            }
            if (token.contains("(")) {
                insideSingleBracket = true; // Открывающая скобка найдена
                result.append(token.substring(0, token.indexOf("(")).trim()); // Добавляем текст до открывающей скобки
            } else {
                result.append(token); // Добавляем текущий токен
            }
        }

        return result.toString().replaceAll(" +", " ").trim(); // Убираем лишние пробелы и возвращаем результат
    }

    // Метод для вывода содержимого файла на консоль
    public static void printFileContent(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line); // Выводим строку на консоль
        }
        br.close();
    }
}
