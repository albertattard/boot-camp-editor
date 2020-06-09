package editor;

import com.google.common.base.Preconditions;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode( callSuper = true )
public class HeaderLine extends TextLine {

  private final String caption;

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
    Preconditions.checkNotNull( caption );

    final String link =
      this.caption.toLowerCase()
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
