package playlist;

public class Song {
    private String title;
    private int length;
    private Artist artist;

    public Song(String title, int length, Artist artist) {
        this.title = title;
        this.length = length;
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public int getLength() {
        return length;
    }

    public Artist getArtist() {
        return artist;
    }
}
