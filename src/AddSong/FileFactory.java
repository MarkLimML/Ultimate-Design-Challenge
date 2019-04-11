package AddSong;

public class FileFactory implements AbstractFactory
{
    @Override
    public FileF fileCreate(String type)
    {
        FileF file = null;
        switch (type)
        {
            case "image":
                file = new Image();
                break;
            case "song":
                file = new Song();
                break;
        }
        return file;
    }
}
