package editor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName( "Header line" )
public class HeaderLineTest {

  @Test
  @DisplayName( "should return the header caption" )
  public void shouldReturnHeaderCaption() {
    final HeaderLine subject = HeaderLine.of( "# A simple header" );
    assertEquals( "A simple header", subject.getCaption() );
    assertEquals( 1, subject.getLevel() );
  }

  @Test
  @DisplayName( "should create header link" )
  public void shouldCreateHeaderLink() {
    final HeaderLine subject = HeaderLine.of( "# A simple header" );
    final HeaderLink actual = subject.toLink( "caption" );

    final HeaderLink expected = new HeaderLink( "caption", "a-simple-header" );
    assertEquals( expected, actual );
  }

  @Test
  @DisplayName( "should create a new header with lower level" )
  public void shouldCreateLowerHeader() {
    final HeaderLine subject = HeaderLine.of( "# A simple header" );
    final HeaderLine actual = subject.toLowerLevelBy( 2 );

    assertEquals( HeaderLine.of( "### A simple header" ), actual );
  }
}
