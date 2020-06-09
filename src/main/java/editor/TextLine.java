package editor;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.IOException;
import java.io.Writer;
import java.util.stream.Stream;

@Getter
@EqualsAndHashCode
public class TextLine implements Line {

  protected final String text;
  protected final int indentation;

  public TextLine( final String text ) {
    this.text = text;
    this.indentation = calculateIndentation( text );
  }

  @Override
  public Stream<Line> resolve( final Context context ) {
    return Stream.of( this );
  }

  @Override
  public void writeTo( final Writer writer ) {
    try {
      writer.write( text );
      writer.write( "\n" );
    } catch ( final IOException e ) {
      throw new RuntimeException( "Failed to write line", e );
    }
  }

  private static int calculateIndentation( final String text ) {
    for ( int i = 0, size = text.length(); i < size; i++ ) {
      if ( !Character.isWhitespace( text.charAt( i ) ) ) {
        return i;
      }
    }

    return text.length();
  }

  @Override
  public String toString() {
    return String.format( "TextLine{text=%s, indentation=%d}", text, indentation );
  }
}
