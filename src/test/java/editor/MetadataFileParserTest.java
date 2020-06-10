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
    final MetadataFile subject = SampleHelper.readMetadataFile( "guest-file.json" );
    assertNotNull( subject.getMetadataFile() );
    assertEquals( "guest-example", subject.getName() );
    assertEquals( "guest-file.md", subject.getEditorFile() );
  }

  @Test
  @DisplayName( "should use the metadata file name as the editor file when missing in metadata" )
  public void shouldUseTheMetadataFileName() {
    final MetadataFile subject = SampleHelper.readMetadataFile( "host-file.json" );
    assertNotNull( subject.getMetadataFile() );
    assertEquals( "host-example", subject.getName() );
    assertEquals( "host-file.md", subject.getEditorFile() );
  }

  @Test
  @DisplayName( "should return the editor file associated to the metadata" )
  public void shouldReturnEditorFile() {
    final MetadataFile subject = SampleHelper.readMetadataFile( "guest-file.json" );
    final EditorFile file = subject.readEditorFile();
    assertEquals( 7, file.stream().count() );
  }
}
