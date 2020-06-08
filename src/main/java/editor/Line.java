package editor;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Line {

  protected final String text;

  public static Line parse( final String text ) {
    if ( text.startsWith( "#" ) ) {
      return new HeaderLine( text );
    }

    return new Line( text );
  }
}
