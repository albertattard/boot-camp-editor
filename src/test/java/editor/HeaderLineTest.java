package editor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName( "Header line" )
public class HeaderLineTest {

  @Test
  @DisplayName( "should create header link" )
  public void shouldCreateHeaderLink() {
    final HeaderLine subject = new HeaderLine( "# A simple header" );
    final HeaderLink actual = subject.toLink( "caption" );

    final HeaderLink expected = new HeaderLink( "caption", "a-simple-header" );

    assertEquals( expected, actual );
  }
}
