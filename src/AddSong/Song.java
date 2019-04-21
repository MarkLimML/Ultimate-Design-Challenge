package AddSong;

import javafx.stage.FileChooser;

import java.sql.Date;


public class Song implements FileF
{
    private int songId;
    private int genreId;
    private int albumId;

    private String name;
    private String genre;
    private String artist;
    private String album;
    private int year;
    private String songPath;
    private String imgPath;
    private int user_id;
    private Date created;

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }



    @Override
    public FileF fileCreate()
    {
        FileF file = new Song();

        file.chooser.getExtensionFilters().remove(0,file.chooser.getExtensionFilters().size());
        file.chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Songs", "*.*"),
                new FileChooser.ExtensionFilter("WAV", "*.wav"),
                new FileChooser.ExtensionFilter("MP3", "*.mp3")
        );

        return file;
    }

    public int getUser_id(){ return user_id; }

    public void setUser_id(int user_id){ this.user_id = user_id; }

    public int getSongId()
    {
        return songId;
    }

    public void setSongId(int songId)
    {
        this.songId = songId;
    }

    public String getName()
    {
        System.out.println("getName()");
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getGenre()
    {
        return genre;
    }

    public void setGenre(String genre)
    {
        this.genre = genre;
    }

    public String getArtist()
    {
        return artist;
    }

    public void setArtist(String artist)
    {
        this.artist = artist;
    }

    public int getGenreId()
    {
        return genreId;
    }

    public void setGenreId(int genreId)
    {
        this.genreId = genreId;
    }

    public int getAlbumId()
    {
        return albumId;
    }

    public void setAlbumId(int albumId)
    {
        this.albumId = albumId;
    }

    public String getSongPath()
    {
        return songPath;
    }

    public void setSongPath(String songPath)
    {
        this.songPath = songPath;
    }

    public String getImgPath()
    {
        return imgPath;
    }

    public void setImgPath(String imgPath)
    {
        this.imgPath = imgPath;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
