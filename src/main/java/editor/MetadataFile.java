package editor;

import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Data
public class MetadataFile {

  private String name;
  private String editorFile;
  private Path metadataFile;

  public EditorFile readEditorFile() {
    try ( final InputStream inputStream = Files.newInputStream( getEditorFilePath() ) ) {
      return EditorFileParser.parse( inputStream, StandardCharsets.UTF_8 );
    } catch ( IOException e ) {
      throw new RuntimeException( "Failed to open and parse file", e );
    }
  }

  private Path getEditorFilePath() {
    return metadataFile.getParent().resolve( editorFile );
  }
}
