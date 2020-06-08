package editor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HeaderLine extends Line {

  public HeaderLine( final String text ) {
    super( text );
  }

  public static List<HeaderLine> of( final String... headers ) {
    return Arrays.stream( headers )
      .map( HeaderLine::new )
      .collect( Collectors.toList() );
  }

  public HeaderLink toLink( final String caption ) {
    final String[] parts = text.split( "\\s+", 2 );

    final String link =
      /* TODO: not all cases are properly captured */
      parts[1].toLowerCase()
        .replaceAll( "[/'`]", "" )
        .replaceAll( "[^\\p{IsAlphabetic}\\p{IsDigit}]+", "-" )
        .replaceAll( "-+", "-" )
        .replaceAll( "-$", "" );

    return new HeaderLink( caption, link );
  }
}
