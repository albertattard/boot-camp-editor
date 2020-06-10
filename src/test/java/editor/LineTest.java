package editor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName( "Line" )
public class LineTest {

  @CsvFileSource( resources = "/samples/data/indentation.csv", numLinesToSkip = 1 )
  @DisplayName( "should calculate the indentation" )
  @ParameterizedTest( name = "should return {1} for the line {0}" )
  public void shouldCalculateIndentation( final String line, final int indentation ) {
    assertEquals( indentation, Line.calculateIndentation( line ) );
  }
}
