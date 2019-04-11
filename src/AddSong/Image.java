package AddSong;

import javafx.stage.FileChooser;

public class Image implements FileF
{
    @Override
    public FileF fileCreate()
    {
        FileF file = new Song();

        file.chooser.getExtensionFilters().remove(0,file.chooser.getExtensionFilters().size());
        file.chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        return file;
    }
}
