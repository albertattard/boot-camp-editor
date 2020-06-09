package editor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName( "Include command line parser" )
public class IncludeCommandLineParserTest {

  @CsvFileSource( resources = "/samples/data/include_command.csv", numLinesToSkip = 1 )
  @ParameterizedTest( name = "should create command for line {0} with include {1} and indentation of {2}" )
  public void shouldCreateCommand( final String line, final String include, final int indentation ) {
    assertTrue( parser.match( line ), String.format( "The line %s is a valid include command", line ) );

    final IncludeCommandLine commandLine = parser.parse( line );
    assertEquals( include, commandLine.getInclude() );
    assertEquals( indentation, commandLine.getIndentation() );
  }

  private final LineParser<IncludeCommandLine> parser = IncludeCommandLineParser.instance();

}
