package editor;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.stream.Stream;

import static editor.Line.calculateIndentation;

@Getter
@EqualsAndHashCode( callSuper = true )
public class TocCommandLine extends CommandLine {

  public TocCommandLine( final int indentation ) {
    super( indentation );
  }

  public static TocCommandLine of( final String text ) {
    return new TocCommandLine( calculateIndentation( text ) );
  }

  @Override
  public Stream<Line> resolve( final Context context ) {

    if ( context.containsType( IncludeCommandLine.class ) ) {
      return Stream.of( this );
    }

    return Stream.concat(
      Stream.of( HeaderLine.of( "## TOC" ), new TextLine( "" ) ),
      context
        .stream()
        .filter( line -> line instanceof HeaderLine )
        .map( line -> (HeaderLine) line )
        .filter( header -> header.getLevel() > 1 )
        .map( header -> {
          final String padding = Line.padWith( ' ', ( header.getLevel() - 2 ) * 4 );

          final String caption = header.getCaption();
          final String link = header.toLink( "" ).getLink();
          final String text = String.format( "%s1. [%s](#%s)", padding, caption, link );

          return new TextLine( text );
        } )
    );
  }

  @Override
  public TocCommandLine indentBy( final int indentation ) {
    return new TocCommandLine( this.indentation + indentation );
  }
}
