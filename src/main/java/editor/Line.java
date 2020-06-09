package editor;

import java.io.Writer;
import java.util.stream.Stream;

public interface Line {

  Stream<Line> resolve( Metadata metadata );

  void writeTo( Writer writer );
}
