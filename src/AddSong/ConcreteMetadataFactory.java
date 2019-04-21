package AddSong;

import java.util.ArrayList;

public class ConcreteMetadataFactory implements MetadataFactory
{
        public Metadata create(String Type)
        {
            Metadata metadata = null;
            switch (Type) {
                case "WithAll":
                    metadata = new WithAll();
                    break;
//                case "WithAlbumNArtist":
//                    metadata = new WithAlbumNArtist();
//                    break;
                case "WithAlbumNGenre":
                    metadata = new WithAlbumNGenre();
                    break;
//                case "WithArtistNGenre":
//                    metadata = new WithArtistNGenre();
//                    break;
                case "WithAlbum":
                    metadata = new WithAlbum();
                    break;
//                case "WithArtist":
//                    metadata = new WithArtist();
//                    break;
                case "WithGenre":
                    metadata = new WithGenre();
                    break;
                case "WithNone":
                    metadata = new WithNone();
                    break;
            }
            return metadata;
        }

    }

    /*
        hasAlbum = truths.get(0)
        hasArtist = truths.get(1)
        hasGenre = truths.get(2)
     */
  /*  @Override
    public Metadata create(String Type)
    {
        Metadata metadata = null;

        if(!truths.get(0) && !truths.get(1) && !truths.get(2))
            metadata = new WithNone();
        else if(truths.get(3))
            metadata = new WithAlbum();
        else if(truths.get(3) && truths.get(5))
            metadata = new WithAlbumNArtist();
        else if(truths.get(3) && truths.get(6))
            metadata = new WithAlbumNGenre();
        else if(truths.get(5))
            metadata = new WithArtist();
        else if(truths.get(5) && truths.get(6))
            metadata = new WithArtistNGenre();
        else if(truths.get(6))
            metadata = new WithGenre();
        else if(truths.get(3) && truths.get(5) && truths.get(6))
            metadata = new WithAll();

        return metadata;
    }*/

