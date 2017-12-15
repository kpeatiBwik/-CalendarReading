import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    private static SimpleDateFormat simpleFormatter = new SimpleDateFormat("dd.MM.yyyy");
    private static File file = new File("src\\main\\resources\\data.csv");
    //src\main\resources\
    private static final long day = 86400000;
    private static Scanner sc = new Scanner(System.in);
    private static Map<String, Double> stringMap = getContentFromFile(file);

    public static void main(String[] args) throws IOException {
        menu();
    }

    private static void menu() {
        String s = "";
        while (!s.equals("0")) {
            System.out.println("\n1. Просмотреть кол-во часов затраченых на чтение");
            System.out.println("2. Добавить время");
            System.out.println("");
            System.out.println("0. Выйти из программы\n");
            s = sc.nextLine();
            switch (s) {
                case "1":
                    System.out.println("\nВремя затраченное на чтение = " + calc(getContentFromFile(file)) + "\n");
                    break;

                case "2":
                    System.out.println("\nВведите кол-во часов чтения (double)\n");
                    String tmp = sc.nextLine();
                    setContentToFile(file, stringMap, Double.parseDouble(tmp));
                    break;

                case "3":
                    break;

                case "0":
                    System.out.println("\nВсего доброго!\n");
                    System.exit(0);
                    break;
            }
        }
    }

    private static double calc(Map<String, Double> map) {
        double result = 0;
        for (String key : map.keySet()) {
            Double value = map.get(key);
            result += value;
        }
        return result;
    }

    private static Map<String, Double> getContentFromFile(File file) {
        Map<String, Double> result = new HashMap<>();
        try {
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            String[] tmp;
            while ((line = bufferedReader.readLine()) != null) {
                tmp = line.split(" ");
                result.put(tmp[0], Double.parseDouble(tmp[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static void setContentToFile(File file, Map<String, Double> stringMap, double x) {
        if (stringMap.size() == 0) {
            stringMap.put(simpleFormatter.format(new Date(new Date().getTime())), -1.0);
        } else {
            for (int i = 0; i <= 42; i++) {
                if (!stringMap.containsKey(simpleFormatter.format(new Date(new Date().getTime() - day * i)))) {
                    stringMap.put(simpleFormatter.format(new Date(new Date().getTime() - day * i)), -1.0);
                } else break;
            }

            if (stringMap.get(simpleFormatter.format(new Date(new Date().getTime()))) != -1.0) {
                stringMap.put(simpleFormatter.format(new Date(new Date().getTime())),
                        stringMap.get(simpleFormatter.format(new Date(new Date().getTime()))) + x);
            } else stringMap.put(simpleFormatter.format(new Date(new Date().getTime())), x < 1.0 ? -1 + x : x);
        }

        try {
            FileWriter writer = new FileWriter(file, false);
            BufferedWriter bufferWriter = new BufferedWriter(writer);
            for (Map.Entry<String, Double> entry : stringMap.entrySet()) {
                bufferWriter.write(entry.getKey() + " " + entry.getValue() + "\n");
            }
            bufferWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}