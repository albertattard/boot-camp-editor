package editor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName( "Metadata parser" )
public class MetadataFileParserTest {

  @Test
  @DisplayName( "should parse metadata" )
  public void shouldParseMetadata() {
    final MetadataFile target = SampleHelper.readMetadataFile( "guest-file.json" );
    assertNotNull( target.getMetadataFile() );
    assertEquals( "guest-example", target.getName() );
    assertEquals( "guest-file.md", target.getEditorFile() );
  }

  @Test
  @DisplayName( "should use the metadata file name as the editor file when missing in metadata" )
  public void shouldUseTheMetadataFileName() {
    final MetadataFile target = SampleHelper.readMetadataFile( "host-file.json" );
    assertNotNull( target.getMetadataFile() );
    assertEquals( "host-example", target.getName() );
    assertEquals( "host-file.md", target.getEditorFile() );
  }

  @Test
  @DisplayName( "should return the editor file associated to the metadata" )
  public void shouldReturnEditorFile() {
    final MetadataFile target = SampleHelper.readMetadataFile( "guest-file.json" );
    final EditorFile file = target.readEditorFile();
    assertEquals( 7, file.numberOfLines() );
  }
}
