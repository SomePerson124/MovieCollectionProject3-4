public class Movie {

    private String title;
    private String cast;
    private String director;
    private String overview;
    private String runtime;
    private String userRating;

    public Movie(String title, String cast, String director, String overview, String runtime, String userRating) {
        this.title = title;
        this.cast = cast;
        this.director = director;
        this.overview = overview;
        this.runtime = runtime;
        this.userRating = userRating;
    }

    public String getTitle() {
        return title;
    }

    public String getCast() {
        return cast;
    }

    public String getDirector() {
        return director;
    }

    public String getOverview() {
        return overview;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getUserRating() {
        return userRating;
    }

}