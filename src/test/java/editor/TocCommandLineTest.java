package editor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName( "ToC command line" )
public class TocCommandLineTest {

  @Test
  @DisplayName( "should include the proper dependency from the context" )
  public void shouldIncludeDependency() {
    final List<Line> source = List.of(
      HeaderLine.of( "# Heading 1" ),
      HeaderLine.of( "## Heading 2" ),
      new TextLine( "Some line..." ),
      HeaderLine.of( "### Heading 3" ),
      new TextLine( "Some line..." )
    );

    final Context context = mock( Context.class );

    when( context.containsType( eq( IncludeCommandLine.class ) ) ).thenReturn( false );
    when( context.stream() ).thenReturn( source.stream() );

    final TocCommandLine subject = TocCommandLine.of( "{toc}" );
    final List<Line> actual = subject.resolve( context ).collect( Collectors.toList() );

    final List<Line> expected = List.of(
      HeaderLine.of( "## TOC" ),
      new TextLine( "" ),
      new TextLine( "1. [Heading 2](#heading-2)" ),
      new TextLine( "    1. [Heading 3](#heading-3)" )
    );

    assertEquals( expected, actual );

    verify( context, times( 1 ) ).containsType( IncludeCommandLine.class );
    verify( context, times( 1 ) ).stream();
  }
}
