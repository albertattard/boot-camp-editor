package editor;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode
public class HeaderLine extends TextLine {

  protected final String caption;

  public HeaderLine( final String text ) {
    super( text );
    this.caption = parseCaption( text );
  }

  private static String parseCaption( final String text ) {
    final String[] parts = text.split( "\\s+", 2 );
    return parts[1];
  }

  public static List<HeaderLine> of( final String... headers ) {
    return Arrays.stream( headers )
      .map( HeaderLine::new )
      .collect( Collectors.toList() );
  }

  public HeaderLink toLink( final String caption ) {
    final String link =
      caption.toLowerCase()
        /* TODO: not all cases are properly captured */
        .replaceAll( "[/'`]", "" )
        .replaceAll( "[^\\p{IsAlphabetic}\\p{IsDigit}]+", "-" )
        .replaceAll( "-+", "-" )
        .replaceAll( "-$", "" );

    return new HeaderLink( caption, link );
  }

  @Override
  public String toString() {
    return String.format( "HeaderLine{text=%s, indentation=%d, caption=%s}", text, indentation, caption );
  }
}
