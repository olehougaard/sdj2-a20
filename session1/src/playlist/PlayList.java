package playlist;

import java.util.ArrayList;

public class PlayList {
    private String title;
    private ArrayList<Song> songs;

    public PlayList(String title) {
        this.title = title;
        songs = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public int getNumberOfSongs() {
        return songs.size();
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public Song getSong(int i) {
        return songs.get(i);
    }

    public int getTotalLength() {
        int total = 0;
        for(int i = 0; i < songs.size(); i++) {
            Song song = songs.get(i);
            int length = song.getLength();
            total += length;
        }
        return total;
    }
}
