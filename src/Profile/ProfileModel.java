package Profile;

import Model.ModelAbstract;
import Model.User;
import dashboard.PlaylistBox;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProfileModel extends ModelAbstract {
    public ArrayList<PlaylistBox> getListOfPlaylist(String playlistType) {
        ArrayList<PlaylistBox> boxes = new ArrayList<>();
        this.setUserPlaylists(boxes);
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

    public void setCurrentProfile(User user) {
        setUser(user);
    }
}
