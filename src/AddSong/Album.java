package AddSong;

public class Album {

    private int album_id;
    private String album_name;
    private int year;

    public int getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(int album_id) {
        this.album_id = album_id;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    /*
private ArrayList<PlaylistBox> setAlbumPlaylists(ArrayList<PlaylistBox> boxes) {
        ResultSet rs = getDbc().getAlbumInfo();
        PlaylistBox box;
        try {
            while (rs.next()) {
                box = new PlaylistBox();
                box.setPlaylistId(rs.getInt("album_id"));
                box.setPlaylistName(rs.getString("album_name"));
                box.setImgPath(rs.getString("img_path"));
                boxes.add(box);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return boxes;
    }
 */
}
