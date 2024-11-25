import java.io.*;
import java.util.StringTokenizer;

/*Задание:
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

public class Main {
    public static void main(String[] args) {
        String inputFilePath = "input.txt";
        String outputFilePath = "output.txt";

        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFilePath));
            BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath));

            String line;
            System.out.println("Содержимое входного файла:");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                String cleanedLine = removeSingleBracketsContent(line);
                bw.write(cleanedLine);
                bw.newLine();
            }
            br.close();
            bw.close();

            // Чтение содержимого выходного файла для вывода
            System.out.println("\nОбработка завершена. Результат сохранен в " + outputFilePath);
            System.out.println("Содержимое выходного файла:");
            BufferedReader outputReader = new BufferedReader(new FileReader(outputFilePath));
            while ((line = outputReader.readLine()) != null) {
                System.out.println(line);
            }
            outputReader.close();
        } catch (IOException e) {
            e.printStackTrace(); // Обработка исключений, если что-то пошло не так с вводом/выводом
        }
    }

    private static String removeSingleBracketsContent(String line) {
        StringBuffer result = new StringBuffer(); // Буфер для хранения очищенной строки
        boolean inSingleQuotes = false; // Флаг для отслеживания, находимся ли мы внутри одинарных скобок

        // Используем StringTokenizer для разделения строки на токены
        StringTokenizer tokenizer = new StringTokenizer(line, "()", true);
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();

            if (token.equals("(")) {
                // Начало секции в одинарных скобках
                inSingleQuotes = true;
            } else if (token.equals(")")) {
                // Конец секции в одинарных скобках
                inSingleQuotes = false;
            } else if (!inSingleQuotes) {
                // Добавляем токен в результат, только если не внутри одинарных скобок
                result.append(token);
            }
        }

        return result.toString();
    }
}