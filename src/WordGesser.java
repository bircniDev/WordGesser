import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class WordGesser {
    private static final ArrayList<String> names = new ArrayList<>();
    private final String WordData = "src/words.txt";
    private static final Scanner scanner = new Scanner(System.in);
    private static final ArrayList<String> SortedNames = new ArrayList<>();
    private String actualWord;
    boolean[] played;
    int counter;
    int randomInt;
    int mode = 0;
    static int wordListSize;

    public static void main(String[] args) {
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

    // Implementing Fisher–Yates shuffle
    static void shuffleArray(char[] ar) {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
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

    public void GessWord() {
        print("Bitte gib die Lösung ein: ");
        String input = scanner.nextLine();
        if (Objects.equals(input, names.get(randomInt))) {
            counter += 1;
            print("Du hast das Word erraten!");
            GAME();
        } else GessWord();
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
        print("""
                Please type in which mode you want to play
                 1 : Easy
                 2 : Hard
                 3 : Ultra Hard""");
        Scanner s = new Scanner(System.in);
        mode = s.nextInt();
        switch (mode) {
            case 1 -> print("You selected easy mode");
            case 2 -> print("You selected hard mode");
            case 3 -> print("You selected ultra hard mode");
            default -> {
                print("You can't select this!");
                getMode();
            }
        }
    }

    public void GAME() {
        if (mode == 0) getMode();
        createGesserWord();
        if (played[randomInt]) {
            if (counter < SortedNames.size()) GAME();
            else {
                System.exit(22);
                print("Du hast alle Wörter erraten");
            }
        } else {
            played[randomInt] = true;
            print("Hier ist dein Wort");
            print(actualWord);
            GessWord();
        }
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

    public void AddWords() {
        for (String name : names) {
            char[] tempChar = name.toCharArray();
            shuffleArray(tempChar);
            //if (mode == 1) moveUpperCase(tempChar); // check for easy-mode
            SortedNames.add(String.valueOf(tempChar));
        }
    }
    public void loadNames() {
        BufferedReader reader;
        String zeile;

        try {
            reader = new BufferedReader(new FileReader(WordData));
            zeile = reader.readLine();

            ArrayList<String[]> values = new ArrayList<>();

            while (zeile != null) {
                values.add(zeile.split("\n"));
                zeile = reader.readLine();
            }
            for (String[] value : values) {
                names.add(Arrays.toString(value).replace("[", "").replace("]", ""));
            }

        } catch (IOException e) {
            System.err.println("Error2 :" + e);
        }
    }
}
