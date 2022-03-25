
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.Collectors;

public class WordGesser
{
    private static final ArrayList<String> names = new ArrayList<>();
    private String WordData = "src/words.txt";
    private static final Scanner scanner = new Scanner(System.in);
    private ArrayList<String> SortedNames = new ArrayList<>();

    public static void main(String[] args) {
        WordGesser ns = new WordGesser();
        //ns.readNames();
        ns.loadNames();
        ns.sortNames();
        //ns.printNames();
        //System.out.println("cloned");
        //ns.createArray();
        ns.SortName();
        ns.printNames();
    }

    public void readNames() {
        names.clear();
        System.out.println("Gib Namen ein (leerzeile für Eingabeende): ");
        while (System.nanoTime() >= 0)
        {
            String name = scanner.nextLine();
            if(!name.isEmpty()) names.add(name);
            else break;
        }
    }

    public void sortNames(){
        Collections.sort(names);
    }

    public void printNames(){
        //System.out.println("Deine Namen wurden sortiert: ");
        int number = 1;
        for(Object name : SortedNames)
        {
            System.out.println(number+". "+name);
            number += 1;
        }
    }

    public void SortName(){
        for(Object name : names)
        {
            String temp = (String) name;
            char [] tempChar = temp.toCharArray();
            Arrays.sort(tempChar);
            SortedNames.add(String.valueOf(tempChar));
        }
    }

    public void loadNames() {
        BufferedReader reader;
        String zeile=null;


        try {
            reader = new BufferedReader(new FileReader(WordData));
            zeile = reader.readLine();

            ArrayList<String[]> values = new ArrayList<String[]>();

            while (zeile != null) {
                values.add(zeile.split("\n"));
                zeile = reader.readLine();
            }
            //System.out.println(values.size());
            //System.out.println(zeile);
            for (String[] value : values) {
                System.out.println((Arrays.toString(value)));
                names.add(Arrays.toString(value).replace("[", "").replace("]", " "));
                //String.substring(1,strLen-1)
            }

        } catch (IOException e) {
            System.err.println("Error2 :"+e);
        }
    }
}
