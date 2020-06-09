package editor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EditorFile {

  private final List<Line> lines;

  public EditorFile( final List<Line> lines ) {
    this.lines = Collections.unmodifiableList( new ArrayList<>( lines ) );
  }

  public int numberOfLines() {
    return lines.size();
  }

  public List<HeaderLine> headers() {
    return lines.stream()
      .filter( line -> line instanceof HeaderLine )
      .map( line -> (HeaderLine) line )
      .collect( Collectors.toList() );
  }

  public EditorFile resolve( final Context context ) {
    final List<Line> resolved = lines
      .stream()
      .flatMap( line -> line.resolve( context ) )
      .collect( Collectors.toList() );
    return new EditorFile( resolved );
  }

  public void writeTo( final OutputStream output, final Charset charset ) {
    final BufferedWriter writer = new BufferedWriter( new OutputStreamWriter( output, charset ) );
    lines.forEach( line -> line.writeTo( writer ) );

    try {
      writer.flush();
    } catch ( final IOException e ) {
      throw new RuntimeException( "Failed to flush the writer", e );
    }
  }

  public Stream<Line> stream() {
    return lines.stream();
  }
}
