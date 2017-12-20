import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Main {
    private static File file = new File("src\\main\\resources\\data.csv");
    private static Scanner sc = new Scanner(System.in);
    private static Map<LocalDate, Double> stringMap = getContentFromFile(file);

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

    private static double calc(Map<LocalDate, Double> map) {
        double result = 0;
        for (LocalDate key : map.keySet()) {
            Double value = map.get(key);
            result += value;
        }
        return result;
    }

    private static Map<LocalDate, Double> getContentFromFile(File file) {
        Map<LocalDate, Double> result = new HashMap<>();
        try {
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            String[] tmp;
            while ((line = bufferedReader.readLine()) != null) {
                tmp = line.split(" ");
                result.put(LocalDate.parse(tmp[0]), Double.parseDouble(tmp[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static void setContentToFile(File file, Map<LocalDate, Double> stringMap, double x) {
        LocalDate LOCAL_DATE = LocalDate.now();
        if (stringMap.size() == 0) {
            stringMap.put(LOCAL_DATE, -1.0);
        } else {
            for (int i = 0; i <= 42; i++) {
                if (!stringMap.containsKey(LOCAL_DATE.minusDays(i))) {
                    stringMap.put(LOCAL_DATE.minusDays(i), -1.0);
                } else break;
            }
            if (stringMap.get(LOCAL_DATE) != -1.0) {
                stringMap.put(LOCAL_DATE, stringMap.get(LOCAL_DATE) + x);
            } else stringMap.put(LOCAL_DATE, x <= 1.0 ? -1 + x : x);
        }

        try {
            FileWriter writer = new FileWriter(file, false);
            BufferedWriter bufferWriter = new BufferedWriter(writer);
            for (Map.Entry<LocalDate, Double> entry : stringMap.entrySet()) {
                bufferWriter.write(entry.getKey() + " " + entry.getValue() + "\n");
            }
            bufferWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}