package editor;

import java.io.Writer;
import java.util.stream.Stream;

public interface Line {

  Stream<Line> resolve( final Context context );

  void writeTo( final Writer writer );

  Line indentBy( final int indentation ) throws UnsupportedOperationException;

  static int calculateIndentation( final String text ) {
    for ( int i = 0, size = text.length(); i < size; i++ ) {
      if ( !Character.isWhitespace( text.charAt( i ) ) ) {
        return i;
      }
    }

    return text.length();
  }
}
