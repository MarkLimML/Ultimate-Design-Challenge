package makePlaylist;

public class ConcretePlaylistFactory implements PlaylistFactory {
	public Playlist getPlaylist(String playlistType)
    {
        Playlist playlist = null;
        switch (playlistType) {
            case "AlbumPlaylist":
                playlist = new AlbumPlaylist();
                break;
            case "UserPlaylist":
                playlist = new UserPlaylist();
                break;
            case "GenrePlaylist":
                playlist = new GenrePlaylist();
                break;
            case "ArtistPlaylist":
                playlist = new ArtistPlaylist();
                break;
            case "AllUserSongs":
                playlist = new AllUserSongs();
                break;
            case "YearPlaylist":
                playlist = new YearPlaylist();
                break;
            case "MostPlayed":
                playlist = new MostPlayed();
                break;
        }
        return playlist;
    }

}
