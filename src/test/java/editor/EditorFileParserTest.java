package editor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static editor.SampleHelper.readEditorFile;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName( "Editor file parser" )
public class EditorFileParserTest {

  @Test
  @DisplayName( "should parse file" )
  public void shouldParseFile() {
    final EditorFile subject = readEditorFile( "basic.md" );
    assertEquals( 3, subject.stream().count() );
  }
}
