package editor;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IncludeCommandLineTest {

  @Test
  public void should() {
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
}
