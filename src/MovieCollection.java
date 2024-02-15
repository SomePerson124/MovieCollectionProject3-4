import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class MovieCollection {
    private ArrayList<Movie> movieCollection;
    private Scanner scanner;

    public MovieCollection() {
        movieCollection = new ArrayList<Movie>();
        scanner = new Scanner(System.in);
        importCSVData();
        sortMoviesAlphabetically();
        System.out.println(movieCollection);
    }

    private void start() {
        mainMenu();
    }

    public void mainMenu() {
        System.out.println("Welcome to the movie collection!");
        String menuOption = "";
        importCSVData();
        sortMoviesAlphabetically();

        while (!menuOption.equals("q")) {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (c)ast");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (menuOption.equals("t")) {
            } else if (menuOption.equals("c")) {
            } else if (menuOption.equals("q")) {
                System.out.println("Goodbye!");
            } else {
                System.out.println("Invalid choice!");
            }
        }

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

    private void sortMoviesAlphabetically() {
        for (int i = 1; i < movieCollection.size(); i++) {
            Movie currentMovie = movieCollection.get(i);
            int currentIdx = i;
            while (currentIdx > 0 && currentMovie.getTitle().compareTo(movieCollection.get(currentIdx - 1).getTitle()) < 0) {
                movieCollection.set(currentIdx, movieCollection.get(currentIdx - 1));
                currentIdx--;
            }
            movieCollection.set(currentIdx, currentMovie);
        }
    }

}