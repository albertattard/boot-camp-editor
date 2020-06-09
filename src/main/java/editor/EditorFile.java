package editor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EditorFile {

  private final List<Line> lines;

  public EditorFile( final List<Line> lines ) {
    this.lines = new ArrayList<>( lines );
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
}
