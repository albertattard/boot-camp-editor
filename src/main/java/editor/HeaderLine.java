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

  private final int level;
  private final String caption;

  private HeaderLine( final String text, final int level, final String caption ) {
    super( text );
    this.level = level;
    this.caption = caption;
  }

  public Link toLink( final String caption ) {
    Preconditions.checkNotNull( caption );

    final String link =
      this.caption.toLowerCase()
        /* TODO: not all cases are properly captured */
        .replaceAll( "[/'`]", "" )
        .replaceAll( "[^\\p{IsAlphabetic}\\p{IsDigit}]+", "-" )
        .replaceAll( "-+", "-" )
        .replaceAll( "-$", "" );

    return new Link( caption, link );
  }

  @Override
  protected HeaderLine copyFromIndentedText( final String indentedText ) {
    throw new UnsupportedOperationException( "Cannot indent headers" );
  }

  public HeaderLine toLowerLevelBy( final int difference ) {
    Preconditions.checkArgument( difference >= 0, "Difference must be at least 0" );

    if ( difference == 0 ) {
      return this;
    }

    final int newLevel = level + difference;
    final char[] headerHash = new char[newLevel];
    Arrays.fill( headerHash, '#' );
    final String text = String.format( "%s %s", new String( headerHash ), caption );
    return new HeaderLine( text, newLevel, caption );
  }

  public static HeaderLine of( final String text ) {
    final String[] parts = text.split( "\\s+", 2 );
    final int level = parts[0].length();
    final String caption = parts[1];

    return new HeaderLine( text, level, caption );
  }

  public static List<HeaderLine> of( final String... headers ) {
    return Arrays.stream( headers )
      .map( HeaderLine::of )
      .collect( Collectors.toList() );
  }

  @Override
  public String toString() {
    return String.format( "HeaderLine{level=%d, caption=%s}", level, caption );
  }
}
