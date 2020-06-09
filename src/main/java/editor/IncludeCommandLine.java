package editor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.Stream;

@Getter
@EqualsAndHashCode( callSuper = true )
public class IncludeCommandLine extends TextLine {

  private final String include;

  private IncludeCommandLine( final String text, final String include ) {
    super( text );
    this.include = include;
  }

  @Override
  public Stream<Line> resolve( final Context context ) {
    return context.findMetadata( include )
      .orElseThrow( () -> new RuntimeException( String.format( "Missing dependency %s", include ) ) )
      .readEditorFile()
      .resolve( context )
      .stream()
      .map( line -> line.indentBy( getIndentation() ) );
  }

  private static String parseInclude( final String text ) {
    final String[] parts = text.split( "[\\(\\)]" );
    return StringUtils.substringBetween( parts[1], "\"" );
  }

  @Override
  public IncludeCommandLine copyFromIndentedText( final String indentedText ) {
    return new IncludeCommandLine( indentedText, include );
  }

  public static IncludeCommandLine of( final String text ) {
    final String include = parseInclude( text );
    return new IncludeCommandLine( text, include );
  }

  @Override
  public String toString() {
    return String.format( "IncludeCommandLine{text=%s, indentation=%d, include=%s}", text, indentation, include );
  }
}
