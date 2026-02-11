import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main
{

    public static void main(String[] args) throws IOException
    {

        File file = new File("Dataset/reddit_dead_internet_analysis_2026.csv");
        Scanner scanner = new Scanner(file);


        if (scanner.hasNextLine())
        {
            scanner.nextLine();
        }

        ArrayList<String> subreddits = new ArrayList<String>();
        ArrayList<Integer> botCounts = new ArrayList<Integer>();
        ArrayList<Integer> humanCounts = new ArrayList<Integer>();

        while (scanner.hasNextLine())
        {

            String line = scanner.nextLine();

            if (!line.equals(""))
            {

                String subreddit = "";
                String isBotFlag = "";

                int commaCount = 0;
                String currentField = "";

                for (int i = 0; i < line.length(); i++)
                {

                    char ch = line.charAt(i);

                    if (ch == ',') {

                        if (commaCount == 1)
                        {
                            subreddit = currentField;
                        }

                        if (commaCount == 8)
                        {
                            isBotFlag = currentField;
                        }

                        commaCount++;
                        currentField = "";

                    }
                    else
                    {
                        currentField += ch;
                    }
                }


                if (commaCount == 8)
                {
                    isBotFlag = currentField;
                }

                boolean isBot = false;
                if (isBotFlag.equalsIgnoreCase("true"))
                {
                    isBot = true;
                }

                int index = subreddits.indexOf(subreddit);

                if (index == -1)
                {
                    subreddits.add(subreddit);
                    botCounts.add(0);
                    humanCounts.add(0);
                    index = subreddits.size() - 1;
                }

                if (isBot)
                {
                    botCounts.set(index, botCounts.get(index) + 1);
                }
                else
                {
                    humanCounts.set(index, humanCounts.get(index) + 1);
                }
            }
        }

        scanner.close();

        System.out.println("=== Bot vs Human Percentage by Subreddit ===\n");

        for (int i = 0; i < subreddits.size(); i++)
        {

            int bots = botCounts.get(i);
            int humans = humanCounts.get(i);
            int total = bots + humans;

            if (total > 0)
            {

                double botPercent = (bots * 100.0) / total;
                double humanPercent = (humans * 100.0) / total;

                System.out.println("Subreddit: " + subreddits.get(i));
                System.out.println("Total Comments: " + total);
                System.out.println("Bots: " + String.format("%.2f", botPercent) + "% (" + bots + ")");
                System.out.println("Humans: " + String.format("%.2f", humanPercent) + "% (" + humans + ")");
                System.out.println("-----------------------------------");
            }
        }
    }
}
