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
        ArrayList<Double> sentimentScores = new ArrayList<Double>();

        while (scanner.hasNextLine())
        {

            String line = scanner.nextLine();

            if (!line.equals(""))
            {

                String subreddit = "";
                String isBotFlag = "";
                String sentimentScore = "";


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

                        if(commaCount == 5) {
                            sentimentScore = currentField;
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

                boolean isBot = Boolean.parseBoolean(isBotFlag.toLowerCase());


                int index = subreddits.indexOf(subreddit);

                if (index == -1)
                {
                    subreddits.add(subreddit);
                    botCounts.add(0);
                    humanCounts.add(0);
                    sentimentScores.add(0.0);
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
                sentimentScores.set(index, sentimentScores.get(index) + Double.parseDouble(sentimentScore));
            }
        }

        scanner.close();

        System.out.println("=== Bot vs Human Percentage by Subreddit ===\n");

        for (int i = 0; i < subreddits.size(); i++)
        {

            int bots = botCounts.get(i);
            int humans = humanCounts.get(i);
            double sentimentScore = sentimentScores.get(i);
            int total = bots + humans;

            if (total > 0)
            {

                double botPercent = (bots * 100.0) / total;
                double humanPercent = (humans * 100.0) / total;
                double averageSentimentScore = sentimentScore * 100 / total;

                System.out.println("Subreddit: " + subreddits.get(i));
                System.out.println("Total Comments: " + total);
                System.out.println("Bots: " + String.format("%.2f", botPercent) + "% (" + bots + ")");
                System.out.println("Humans: " + String.format("%.2f", humanPercent) + "% (" + humans + ")");
                System.out.println("Average Sentiment Score per Comment: " + String.format("%.2f", averageSentimentScore));
                System.out.println("-----------------------------------");
            }
        }

        // R-squared calculation

        // Im not gonna write out the full equation but its the Pearson Correlation Coefficient

        // You need sum of x, y, x squared, y squared, and xy

        double sumX = 0, sumY = 0, sumX2 = 0, sumY2 = 0, sumXY = 0;
        for(int i = 0; i<botCounts.size(); i++) {
            double x = ((double)botCounts.get(i)/(botCounts.get(i) + humanCounts.get(i)));
            //System.out.println that amy be he dunmest corisol spyck);

            // amen
            sumX += x;
            sumX2 += x * x;
            sumY += sentimentScores.get(i);
            sumY2 += Math.pow(sentimentScores.get(i), 2);
            sumXY += x * sentimentScores.get(i);
        }

        // Lets hope this works

        double cc = (botCounts.size() * sumXY - sumX * sumY) / Math.sqrt((botCounts.size() * sumX2 - sumX * sumX) * (botCounts.size() * sumY2 - sumY * sumY));

        System.out.println("Correlation between bot proportion and sentiment score of various subreddits: " + (cc * cc));
    }
}
