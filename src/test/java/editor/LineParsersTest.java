package editor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName( "Line parsers" )
public class LineParsersTest {

  @Test
  @DisplayName( "should parse the line as an include command line" )
  public void shouldParseToIncludeCommand() {
    final String text = "{include(\"some-file\")}";
    final Line target = LineParsers.parse( text );
    assertTrue( target instanceof IncludeCommandLine,
      String.format( "The line '%s' should be treated as an include command", text ) );
  }
}
