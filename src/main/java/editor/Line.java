package editor;

import java.io.Writer;
import java.util.stream.Stream;

public interface Line {

  Stream<Line> resolve( final Context context );

  void writeTo( final Writer writer );
}
