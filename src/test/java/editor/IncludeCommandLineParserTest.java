package editor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName( "Include command line parser" )
public class IncludeCommandLineParserTest {

  @CsvFileSource( resources = "/samples/data/include_command.csv", numLinesToSkip = 1 )
  @ParameterizedTest( name = "should match command for line {0}" )
  public void shouldMatch( final String line ) {
    assertTrue( parser.match( line ), String.format( "The line %s is a valid include command", line ) );
  }

  private final LineParser<IncludeCommandLine> parser = IncludeCommandLineParser.instance();

}
