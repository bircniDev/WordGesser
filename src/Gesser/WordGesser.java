package Gesser;

import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class WordGesser {
    private static final ArrayList<String> names = new ArrayList<>();
    static String SystemLanguage;
    private static final Scanner scanner = new Scanner(System.in);
    private static final ArrayList<String> SortedNames = new ArrayList<>();
    private String actualWord;
    boolean[] played;
    int counter;
    static int trys;
    int randomInt;
    int mode = 0;
    static Locale SystemLocale;
    static ResourceBundle messages;
    private static String WordData;
    static int wordListSize;

    public static void main(String[] args) {
        trys = 0;
        SystemLanguage = Locale.getDefault().getLanguage();
        SystemLocale = new Locale(SystemLanguage);
        messages = ResourceBundle.getBundle("Gesser.languages.LangResource", SystemLocale);
        WordData = messages.getString("resource");
        WordGesser ns = new WordGesser();
        ns.loadNames();
        ns.AddWords();
        wordListSize = SortedNames.size();
        ns.createPlayedArray(wordListSize);
        ns.GAME();
    }

    public void print(String a) {
        System.out.println(a);
    }

    static void shuffleArray(char[] ar) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            char a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    public static char[] moveUpperCase(char[] c) {
        int upper = 0;
        for (int i = 0; i < c.length; i++) {
            if (checkCharUpperCase(c[i])) upper = i;
        }
        char temp = c[upper];
        c[upper] = c[0];
        c[0] = temp;
        return c;
    }

    private static void printState(String t, String path) {
        //FileOutputStream output = new FileOutputStream("resources/ShuffelWords.txt");
        File file = new File(path);
        try {
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write(t);
            writer.newLine();
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("Datei ist geschrieben!");
    }

    public static char[] deleteUpperCase(char[] c) {
        for (int i = 0; i < c.length; i++) {
            c[i] = Character.toLowerCase(c[i]);
        }
        return c;
    }

    public void createPlayedArray(int size) {
        played = new boolean[size];
        for (int i = 0; i < size; i++) played[i] = false;
    }

    static boolean checkCharUpperCase(char ch) {
        return ch >= 'A' && ch <= 'Z';
    }

    public void getMode() {
        print(messages.getString("mode"));
        Scanner s = new Scanner(System.in);
        mode = s.nextInt();
        switch (mode) {
            case 1 -> print(messages.getString("easyMode"));
            case 2 -> print(messages.getString("hardMode"));
            case 3 -> print(messages.getString("ultrahardMode"));
            default -> {
                print(messages.getString("NoSelect"));
                getMode();
            }
        }
    }

    public void GessWord() {
        print(messages.getString("solutionQuestion"));
        String input = scanner.nextLine();
        trys += 1;
        if (Objects.equals(input, names.get(randomInt))) {
            counter += 1;
            print(messages.getString("solutionText"));
            GAME();
        } else GessWord();
    }

    public void createGesserWord() {
        Random random = new Random();
        randomInt = random.nextInt(wordListSize);
        actualWord = SortedNames.get(randomInt);
        if (mode == 1) {
            char[] tempChar = actualWord.toCharArray();
            actualWord = String.valueOf(moveUpperCase(tempChar));
        }
        if (mode == 3) {
            char[] tempChar = actualWord.toCharArray();
            actualWord = String.valueOf(deleteUpperCase(tempChar));
        }
    }

    public void GAME() {
        if (mode == 0) getMode();
        createGesserWord();
        if (played[randomInt]) {
            if (counter < SortedNames.size()) GAME();
            else {
                print(messages.getString("Trys") + trys + messages.getString("Trys2"));
                System.exit(187);
                print(messages.getString("finish"));
            }
        } else {
            played[randomInt] = true;
            print(messages.getString("wordReq"));
            print(actualWord);
            GessWord();
        }
    }

    public void AddWords() {
        for (String name : names) {
            char[] tempChar = name.toCharArray();
            shuffleArray(tempChar);
            String newName = String.valueOf(tempChar);
            while (newName.equalsIgnoreCase(name)) {
                shuffleArray(tempChar);
                newName = String.valueOf(tempChar);
            }
            SortedNames.add(newName);
            //printState(newName, "GameResource/ShuffleWords.txt");
        }
    }

    public void loadNames() {
        String zeile;
        InputStream is = getClass().getResourceAsStream(WordData);
        assert is != null;
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        try {
            zeile = br.readLine();

            ArrayList<String[]> values = new ArrayList<>();

            while (zeile != null) {
                values.add(zeile.split("\n"));
                zeile = br.readLine();
            }
            for (String[] value : values) {
                names.add(Arrays.toString(value).replace("[", "").replace("]", ""));
            }

        } catch (IOException e) {
            System.err.println("Error2 :" + e);
        }
    }
}
