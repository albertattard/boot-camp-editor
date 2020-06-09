package editor;

import lombok.EqualsAndHashCode;

import java.util.stream.Stream;

@EqualsAndHashCode
public class IncludeCommandLine extends TextLine {

  private final String include;

  public IncludeCommandLine( final String text, final String include ) {
    super( text );
    this.include = include;
  }

  @Override
  public Stream<Line> resolve( final Metadata metadata ) {
    System.out.println( "Child" );
    return metadata.find( include )
      .orElseThrow( () -> new RuntimeException( String.format( "Missing dependency %s", include ) ) )
      .readEditorFile()
      .resolve( metadata )
      .stream();
  }

  public String getInclude() {
    return include;
  }

  @Override
  public String toString() {
    return String.format( "IncludeCommandLine{text=%s, indentation=%d, include=%s}", text, indentation, include );
  }
}
