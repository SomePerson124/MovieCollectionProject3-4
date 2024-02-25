import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class MovieCollection {
    private ArrayList<Movie> movieCollection;
    ArrayList<String> allActors;
    ArrayList<String> alreadyMentioned;
    ArrayList<String> sortedActors;
    private Scanner scanner;

    public MovieCollection() {
        movieCollection = new ArrayList<Movie>();
        allActors = new ArrayList<String>();
        alreadyMentioned = new ArrayList<String>();
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
        sortTitles();
        System.out.print("Enter title search term: ");
        String titleST = scanner.nextLine();
        int count = 0;
        ArrayList<Movie> titles = new ArrayList<Movie>();
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
            System.out.println("Which movie would you like to learn more about?");
            System.out.print("Enter number: ");
            int num = scanner.nextInt();
            scanner.nextLine();
            movieInfo(num, count, titles);
        }
        System.out.println();
    }

    private void searchCast() {
        System.out.print("Enter a person to search for (first or last name): ");
        String name = scanner.nextLine();
        int count = 0;
        getAllActors();
        sortActors();
        sortedActors = new ArrayList<String>();
        for (int i = 0; i < allActors.size(); i++) {
            if (allActors.get(i).toLowerCase().indexOf(name.toLowerCase()) != -1) {
                count++;
                System.out.println(count + ". " + allActors.get(i));
                sortedActors.add(allActors.get(i));
            }
        }
        if (count == 0) {
            System.out.println("No results match your search");
        }
        System.out.println();
        System.out.println("Which would you like to see all movies for?");
        System.out.print("Enter number: ");
        int number = scanner.nextInt();
        int movieCount = 0;
        scanner.nextLine();
        sortTitles();
        String actor = sortedActors.get(number - 1);
        ArrayList<Movie> titles = new ArrayList<Movie>();
        for (int i = 0; i < movieCollection.size(); i++) {
            if (movieCollection.get(i).getCast().indexOf(actor) != -1) {
                movieCount++;
                System.out.println(movieCount + ". " + movieCollection.get(i).getTitle());
                titles.add(movieCollection.get(i));
            }
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        int num = scanner.nextInt();
        scanner.nextLine();
        movieInfo(num, movieCount, titles);
        System.out.println();
    }

    private void movieInfo(int num, int count, ArrayList<Movie> titles) {
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

    private void importCSVData() {
        try {
            File myFile = new File("src/movies_data.csv");
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

    private void sortTitles() {
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

    public void sortActors() {
        for (int i = 1; i < allActors.size(); i++) {
            String currentActor = allActors.get(i);
            int currentIdx = i;
            while (currentIdx > 0 && currentActor.compareTo(allActors.get(currentIdx - 1)) < 0) {
                allActors.set(currentIdx, allActors.get(currentIdx - 1));
                currentIdx--;
            }
            allActors.set(currentIdx, currentActor);
        }
    }

    private void getAllActors() {
        for (int i = 0; i < movieCollection.size(); i++) {
            String[] splitData = movieCollection.get(i).getCast().split("\\|");
            for (int j = 0; j < splitData.length; j++) {
                if (checkIfAdd(splitData[j])) {
                    allActors.add(splitData[j]);
                }
                alreadyMentioned.add(splitData[j]);
            }
        }
    }

    private boolean checkIfAdd(String name) {
        int count = 0;
        for (int i = 0; i < alreadyMentioned.size(); i++) {
            if (alreadyMentioned.get(i).equals(name)) {
                count++;
            }
        }
        if (count > 0) {
            return false;
        }
        return true;
    }

}