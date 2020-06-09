package editor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName( "Editor file parser" )
public class EditorFileParserTest {

  @Test
  @DisplayName( "should parse file" )
  public void shouldParseFile() {
    final EditorFile target =
      EditorFileParser.parse( getClass().getResourceAsStream( "/samples/basic.md" ), StandardCharsets.UTF_8 );
    assertEquals( 3, target.numberOfLines() );
  }

  @Test
  @DisplayName( "should return the headers" )
  public void shouldReturnHeaders() {
    final EditorFile target =
      EditorFileParser.parse( getClass().getResourceAsStream( "/samples/all-headers.md" ), StandardCharsets.UTF_8 );

    final List<HeaderLine> headers =
      HeaderLine.of( "# H1 header", "## H2 header", "### H3 header", "#### H4 header", "##### H5 header" );
    assertEquals( headers, target.headers() );
  }
}
