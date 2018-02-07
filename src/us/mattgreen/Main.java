package us.mattgreen;

import java.util.Scanner;

public class Main {

    private final static FileInput cardAccts = new FileInput("movie_cards.csv");
    private final static FileInput cardPurchases = new FileInput("movie_purchases.csv");
    private final static FileInput cardRatings = new FileInput("movie_rating.csv");
    private static String line = "";
    private static String ratingLine = "";

    public static void main(String[] args) {
        String line;
        String[] fields;
        int[] nums = new int[3];
        boolean first = true;
        boolean firstRating = true;
        System.out.format("%8s  %-18s %6s %6s %10s\n","Account","Name", "Movies", "Points", "Avg Rating");
        while ((line = cardAccts.fileReadLine()) != null) {
            fields = line.split(",");
            findPurchases(first, fields[0], nums);
            first = false;
            averageRatings(firstRating, fields[0], nums);
            System.out.format("00%6s  %-18s  %2d   %4d %6d\n",fields[0],fields[1], nums[0], nums[1], nums[2]);
        }
    }

    public static void findPurchases(boolean first, String acct, int[] nums) {
        nums[0] = 0;
        nums[1] = 0;

        String[] fields;
        boolean done = false;
        if (first) {
            line = cardPurchases.fileReadLine();
        }
        while ((line != null) && !(done)) {
            fields = line.split(",");
            if (fields[0].compareTo(acct) > 0) {
                done = true;
            }
            else if (fields[0].equals(acct)) {
                nums[0]++;
                nums[1] += Integer.parseInt(fields[0]);

                line = cardPurchases.fileReadLine();

            }

        }
    }

    public static void averageRatings(boolean firstRating, String acct, int[] nums) {
        int count = 0;
        nums[2] = 0;
        String[] fields;
        boolean done = false;
        if (firstRating) {
            line = cardRatings.fileReadLine();
        }
        while ((line != null) && !(done)) {
            fields = line.split(",");
            if (fields[0].compareTo(acct) > 0) {
                done = true;
            }
            else if (fields[0].equals(acct)) {
                count ++;
                nums[2]  += Integer.parseInt(fields[1]);
                line = cardRatings.fileReadLine();

            }
        }

        if(count > 0) {
            nums[2] /= count;
        }
    }
}