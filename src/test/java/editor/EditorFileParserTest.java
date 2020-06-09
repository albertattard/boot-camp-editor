package editor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static editor.SampleHelper.readEditorFile;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName( "Editor file parser" )
public class EditorFileParserTest {

  @Test
  @DisplayName( "should parse file" )
  public void shouldParseFile() {
    final EditorFile target = readEditorFile( "basic.md" );
    assertEquals( 3, target.numberOfLines() );
  }

  @Test
  @DisplayName( "should return the headers" )
  public void shouldReturnHeaders() {
    final EditorFile target = readEditorFile( "all-headers.md" );

    final List<HeaderLine> headers =
      HeaderLine.of( "# H1 header", "## H2 header", "### H3 header", "#### H4 header", "##### H5 header" );
    assertEquals( headers, target.headers() );
  }
}
