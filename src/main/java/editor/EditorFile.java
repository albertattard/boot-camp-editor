package editor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EditorFile {

  public static EditorFile parse( final InputStream inputStream, final Charset charset ) {
    final BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream, charset ) );
    return new EditorFile( reader.lines().map( Line::parse ).collect( Collectors.toList() ) );
  }

  private final List<Line> lines;

  public EditorFile( final List<Line> lines ) {
    this.lines = new ArrayList<>( lines );
  }

  public int numberOfLines() {
    return lines.size();
  }
}
