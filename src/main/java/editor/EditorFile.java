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

  public EditorFile( final List<? extends Line> lines ) {
    this.lines = Collections.unmodifiableList( new ArrayList<>( lines ) );
  }

  public EditorFile resolve( final Context context ) {
    final List<Line> resolved = stream()
      .flatMap( line -> line.resolve( context ) )
      .collect( Collectors.toList() );

    /* Do not create a new editor file if the contents have not changed */
    if ( resolved.equals( lines ) ) {
      return this;
    }

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

  public boolean containsType( final Class<?> type ) {
    return stream()
      .map( Line::getClass )
      .anyMatch( type::isAssignableFrom );
  }

  public static EditorFile empty() {
    return EMPTY;
  }

  public Stream<Line> stream() {
    return lines.stream();
  }

  private static final EditorFile EMPTY = new EditorFile( Collections.emptyList() );
}
