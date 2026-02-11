import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("Dataset\\reddit_dead_internet_analysis_2026.csv");
        Scanner scan = new Scanner(file);
        String data = "";
        while(scan.hasNextLine())
            data+=""+scan.nextLine();
        System.out.println(data);
        
    }
}
