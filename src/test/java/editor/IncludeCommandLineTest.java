package editor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName( "Include command line" )
public class IncludeCommandLineTest {

  @CsvFileSource( resources = "/samples/data/include_command.csv", numLinesToSkip = 1 )
  @ParameterizedTest( name = "should create command for line {0} with include {1} and headerOffset of {2}" )
  public void shouldCreateCommand( final String line, final String include, final int headerOffset ) {
    final IncludeCommandLine subject = IncludeCommandLine.of( line );
    assertEquals( include, subject.getInclude() );
    assertEquals( headerOffset, subject.getHeaderOffset() );
  }

  @Test
  @DisplayName( "should include the proper dependency from the context" )
  public void shouldIncludeDependency() {
    final List<TextLine> source = List.of(
      new TextLine( "Some line 1" ),
      new TextLine( "Some line 2" )
    );

    final Context context = mock( Context.class );
    final MetadataFile metadataFile = mock( MetadataFile.class );

    when( context.findMetadata( eq( "something" ) ) ).thenReturn( Optional.of( metadataFile ) );
    when( metadataFile.readEditorFile() ).thenReturn( new EditorFile( source ) );

    final IncludeCommandLine subject = IncludeCommandLine.of( "  {include(\"something\")}" );
    assertEquals( 2, subject.getIndentation() );
    final List<Line> actual = subject.resolve( context )
      .collect( Collectors.toList() );

    final List<TextLine> expected =
      source
        .stream()
        .map( line -> new TextLine( String.format( "  %s", line.text ) ) )
        .collect( Collectors.toList() );

    assertEquals( expected, actual );

    verify( context, times( 1 ) ).findMetadata( "something" );
    verify( metadataFile, times( 1 ) ).readEditorFile();
  }

  @Test
  @DisplayName( "" )
  public void should() {

  }

}
