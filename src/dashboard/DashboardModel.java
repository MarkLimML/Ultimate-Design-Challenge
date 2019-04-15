package dashboard;

import AddSong.Song;
import Model.ModelAbstract;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DashboardModel extends ModelAbstract {
    public ArrayList<PlaylistBox> getListOfPlaylist(String playlistType) {
        ArrayList<PlaylistBox> boxes = new ArrayList<>();
        switch (playlistType) {
            case "AlbumPlaylist":
                this.setAlbumPlaylists(boxes);
                break;
            case "UserPlaylist":
                this.setUserPlaylists(boxes);
                break;
            case "GenrePlaylist":
                this.setGenrePlaylists(boxes);
                break;
//            case "ArtistPlaylist":
//                this.setArtistPlaylists(boxes);
//                break;
            case "YearPlaylist":
                this.setYearPlaylist(boxes);
                break;
        }
        return boxes;
    }

    private ArrayList<PlaylistBox> setYearPlaylist(ArrayList<PlaylistBox> boxes) {
        ResultSet rs = getDbc().getYearInfo();
        PlaylistBox box;
        try {
            while (rs.next()) {
                box = new PlaylistBox();
                box.setPlaylistId(rs.getInt("year"));
                box.setPlaylistName(Integer.toString(rs.getInt("year")));
                boxes.add(box);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return boxes;
    }

    private ArrayList<PlaylistBox> setGenrePlaylists(ArrayList<PlaylistBox> boxes) {
        ResultSet rs = getDbc().getGenreInfo();
        PlaylistBox box;
        try {
            while (rs.next()) {
                box = new PlaylistBox();
                box.setPlaylistId(rs.getInt("genre_id"));
                box.setPlaylistName(rs.getString("genre_name"));
                boxes.add(box);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return boxes;
    }

    private ArrayList<PlaylistBox> setUserPlaylists(ArrayList<PlaylistBox> boxes) {
        ResultSet rs = getDbc().getPlaylistInfo(getUser().getUser_id());
        PlaylistBox box;
        try {
            while (rs.next()) {
                box = new PlaylistBox();
                box.setPlaylistId(rs.getInt("playlist_id"));
                box.setPlaylistName(rs.getString("title"));
                boxes.add(box);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return boxes;
    }

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

//    private ArrayList<PlaylistBox> setArtistPlaylists(ArrayList<PlaylistBox> boxes) {
//        ResultSet rs = getDbc().getArtistInfo();
//        PlaylistBox box;
//        try {
//            while (rs.next()) {
//                box = new PlaylistBox();
//                box.setPlaylistId(rs.getInt("artist_id"));
//                box.setPlaylistName(rs.getString("artist_name"));
//                boxes.add(box);
//            }
//        } catch (SQLException se) {
//            se.printStackTrace();
//        }
//        return boxes;
//    }

    public boolean isArtist () {
        return getUser().isArtist();
    }
}
