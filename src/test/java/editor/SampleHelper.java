package editor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SampleHelper {

  public static MetadataFile readMetadataFile( final String fileName ) {
    return MetadataFileParser.parse( Paths.get( "src/test/resources/samples/files", fileName ) );
  }

  public static EditorFile readEditorFile( final String fileName ) {
    try ( final InputStream inputStream = fileAsInputStream( fileName ) ) {
      return EditorFileParser.parse( inputStream, StandardCharsets.UTF_8 );
    } catch ( IOException e ) {
      throw new RuntimeException( "Failed to read file", e );
    }
  }

  public static InputStream fileAsInputStream( final String fileName ) {
    final InputStream inputStream = SampleHelper.class
      .getResourceAsStream( String.format( "/samples/files/%s", fileName ) );
    assertNotNull( inputStream, String.format( "Failed to open file %s", fileName ) );
    return inputStream;
  }

  private SampleHelper() {
  }
}
