package editor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName( "Editor file" )
public class EditorFileTest {

  @Test
  @DisplayName( "should parse file" )
  public void shouldParseFile() {
    final EditorFile target = EditorFile.parse( getClass().getResourceAsStream( "/samples/basic.md" ) , StandardCharsets.UTF_8 );
    assertEquals( 3, target.numberOfLines() );
  }
}
