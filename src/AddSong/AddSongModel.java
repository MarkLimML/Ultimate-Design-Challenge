package AddSong;

import Model.ModelAbstract;

import java.util.ArrayList;

public class AddSongModel extends ModelAbstract {

    private AddSongController controller;

    private Song song;
    private int user_id;

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

    public Metadata returnSongMetadata(String album, String artist, String genre) {
        Metadata metadata = null;
        if (!album.equals("")&&!artist.equals("")&&!genre.equals("")){
            ConcreteMetadataFactory factory = new ConcreteMetadataFactory();
            metadata = factory.create("WithAll");
        }  else if (!album.equals("")&&!artist.equals("")){
            ConcreteMetadataFactory factory = new ConcreteMetadataFactory();
            metadata = factory.create("WithAlbumNArtist");
        } else if (!album.equals("")&&!genre.equals("")){
            ConcreteMetadataFactory factory = new ConcreteMetadataFactory();
            metadata = factory.create("WithAlbumNGenre");
        } else if (!artist.equals("")&&!genre.equals("")){
            ConcreteMetadataFactory factory = new ConcreteMetadataFactory();
            metadata = factory.create("WithArtistNGenre");
        } else if (!album.equals("")){
            ConcreteMetadataFactory factory = new ConcreteMetadataFactory();
            metadata = factory.create("WithAlbum");
        } else if (!artist.equals("")){
            ConcreteMetadataFactory factory = new ConcreteMetadataFactory();
            metadata = factory.create("WithArtist");
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


}
