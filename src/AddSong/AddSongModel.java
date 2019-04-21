package AddSong;

import Model.ModelAbstract;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddSongModel extends ModelAbstract {

    private AddSongController controller;

    private Song song;
    private ArrayList<Album> userAlbums;


    /*
    public String year;
    public String imgPath;
    public String songPath;
    public String album;
    public String artist;
    public String genre;
    public String name;
*/

    public int getUser_id() {
        return getUser().getUser_id();
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public AddSongController getController() {
        return controller;
    }

    public AddSongModel() {
        System.out.println("[Constructor] AddSongModel()");
        song = new Song();
    }

    public void setUserAlbumsFromDb() {
        ResultSet rs = getDbc().getAlbumsOfArtist(ModelAbstract.getUser().getUser_id());
        userAlbums = new ArrayList<>();
        try {
            while (rs.next()) {
                Album album = new Album();
                album.setAlbum_id(rs.getInt("album_id"));
                album.setAlbum_name(rs.getString("album_name"));
                album.setYear(rs.getInt("year"));
                userAlbums.add(album);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public Metadata returnSongMetadata(String album, String genre) {
        Metadata metadata = null;
        if (!album.equals("")&&!genre.equals("")){
            ConcreteMetadataFactory factory = new ConcreteMetadataFactory();
            metadata = factory.create("WithAll");
        } else if (!album.equals("")&&!genre.equals("")){
            ConcreteMetadataFactory factory = new ConcreteMetadataFactory();
            metadata = factory.create("WithAlbumNGenre");
        } else if (!album.equals("")){
            ConcreteMetadataFactory factory = new ConcreteMetadataFactory();
            metadata = factory.create("WithAlbum");
        } else if (!genre.equals("")) {
            ConcreteMetadataFactory factory = new ConcreteMetadataFactory();
            metadata = factory.create("WithGenre");
        } else {
            ConcreteMetadataFactory factory = new ConcreteMetadataFactory();
            metadata = factory.create("WithNone");
        }
        if (metadata != null)
            metadata.attachDB(ModelAbstract.getDbc());
        return metadata;
    }

    public void setController(AddSongController controller) {
        System.out.println("setController()");
        this.controller = controller;
    }

    public ArrayList<Album> getUserAlbums() {
        return userAlbums;
    }
}
