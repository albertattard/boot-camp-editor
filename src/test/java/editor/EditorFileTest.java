package editor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName( "Editor file" )
public class EditorFileTest {

  @Test
  @DisplayName( "should parse file" )
  public void shouldParseFile() {
    final EditorFile target = EditorFile.parse( getClass().getResourceAsStream( "/samples/basic.md" ), StandardCharsets.UTF_8 );
    assertEquals( 3, target.numberOfLines() );
  }

  @Test
  @DisplayName( "should return the headers" )
  public void shouldReturnHeaders() {
    final EditorFile target =
      EditorFile.parse( getClass().getResourceAsStream( "/samples/all-headers.md" ), StandardCharsets.UTF_8 );

    final List<HeaderLine> headers =
      Stream.of( "# H1 header", "## H2 header", "### H3 header", "#### H4 header", "##### H5 header" ).map(
        HeaderLine::new ).collect(
        Collectors.toList() );
    assertEquals( headers, target.headers() );
  }
}
