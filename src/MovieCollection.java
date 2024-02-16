import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class MovieCollection {
    private ArrayList<Movie> movieCollection;
    private ArrayList<Movie> titles;
    private ArrayList<String> cast;
    private Scanner scanner;

    public MovieCollection() {
        movieCollection = new ArrayList<Movie>();
        titles = new ArrayList<Movie>();
        cast = new ArrayList<String>();
        scanner = new Scanner(System.in);
        mainMenu();
    }

    public void mainMenu() {
        System.out.println("Welcome to the movie collection!");
        String menuOption = "";
        importCSVData();

        while (!menuOption.equals("q")) {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (c)ast");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (menuOption.equals("t")) {
                searchTitles();
            } else if (menuOption.equals("c")) {
                searchCast();
            } else if (menuOption.equals("q")) {
                System.out.println("Goodbye!");
            } else {
                System.out.println("Invalid choice!");
            }
        }
    }

    private void searchTitles() {
        sortAlphabetically("t");
        System.out.print("Enter title search term: ");
        String titleST = scanner.nextLine();
        System.out.println();
        int count = 0;
        for (int i = 0; i < movieCollection.size(); i++) {
            if (movieCollection.get(i).getTitle().toLowerCase().indexOf(titleST.toLowerCase()) != -1) {
                count++;
                System.out.println(count + ". " + movieCollection.get(i).getTitle());
                titles.add(movieCollection.get(i));
            }
        }
        if (count == 0) {
            System.out.println("No movie titles match that search term!");
        } else {
            System.out.println("Which movie would you like to lean more about?");
            System.out.print("Enter number: ");
            int num = scanner.nextInt();
            scanner.nextLine();
            while (num < 1 || num > count) {
                System.out.print("Please enter valid option: ");
                num = scanner.nextInt();
                scanner.nextLine();
            }
            System.out.println();
            System.out.println("Title: " + titles.get(num - 1).getTitle());
            System.out.println("RunTime: " + titles.get(num - 1).getRuntime() + " minutes");
            System.out.println("Directed By: " + titles.get(num - 1).getDirector());
            System.out.println("Cast: " + titles.get(num - 1).getCast());
            System.out.println("Overview: " + titles.get(num - 1).getOverview());
            System.out.println("User Rating: " + titles.get(num - 1).getUserRating());
        }
        System.out.println();
        for (int i = 0; i < titles.size(); i++) {
            titles.remove(i);
        }
    }

    private void searchCast() {
        System.out.print("Enter a person to search for (first or last name): ");
        String name = scanner.nextLine();
        System.out.println();
        int count = 0;
        for (int i = 0; i < movieCollection.size(); i++) {
            if (movieCollection.get(i).getCast().toLowerCase().indexOf(name.toLowerCase()) != -1) {
                String[] splitCast = movieCollection.get(i).getCast().split("\\|");
                for (int j = 0; j < splitCast.length; j++) {
                    if (splitCast[j].toLowerCase().indexOf(name.toLowerCase()) != -1) {
                        cast.add(splitCast[j]);
                        for (int k = 0; k < cast.size(); k++) {
                            if (!cast.get(k).equals(splitCast[j])) {
                                count++;
                                System.out.println(count + ". " + splitCast[j]);
                            }
                        }
                    }
                }
            }
        }
        System.out.println(cast);
        if (count == 0) {
            System.out.println("No results to match your search");
        } else {
            System.out.println("Which would you like to see all movies for?");
            System.out.print("Enter number: ");
            int num = scanner.nextInt();
            scanner.nextLine();
            while (num < 1 || num > count) {
                System.out.print("Please enter valid option: ");
                num = scanner.nextInt();
                scanner.nextLine();
            }
            System.out.println();
        }
        System.out.println();
    }

    private void importCSVData() {
        try {
            File myFile = new File("src\\movies_data.csv");
            Scanner fileScanner = new Scanner(myFile);
            fileScanner.nextLine();
            while (fileScanner.hasNext()) {
                String data = fileScanner.nextLine();
                String[] splitData = data.split(",");
                String title = splitData[0];
                String cast = splitData[1];
                String director = splitData[2];
                String overview = splitData[3];
                String runtime = splitData[4];
                String userRating = splitData[5];
                Movie movie = new Movie(title, cast, director, overview, runtime, userRating);
                movieCollection.add(movie);
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void sortAlphabetically(String choice) {
        if (choice.equals("t")) {
            for (int i = 1; i < movieCollection.size(); i++) {
                Movie currentMovie = movieCollection.get(i);
                int currentIdx = i;
                while (currentIdx > 0 && currentMovie.getTitle().compareTo(movieCollection.get(currentIdx - 1).getTitle()) < 0) {
                    movieCollection.set(currentIdx, movieCollection.get(currentIdx - 1));
                    currentIdx--;
                }
                movieCollection.set(currentIdx, currentMovie);
            }
        } else if (choice.equals("c")) {
            for (int i = 1; i < movieCollection.size(); i++) {
                Movie currentMovie = movieCollection.get(i);
                int currentIdx = i;
                while (currentIdx > 0 && currentMovie.getCast().compareTo(movieCollection.get(currentIdx - 1).getCast()) < 0) {
                    movieCollection.set(currentIdx, movieCollection.get(currentIdx - 1));
                    currentIdx--;
                }
                movieCollection.set(currentIdx, currentMovie);
            }
        }
    }

}