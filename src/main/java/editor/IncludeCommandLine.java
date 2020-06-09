package editor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Stream;

@Getter
@EqualsAndHashCode( callSuper = true )
public class IncludeCommandLine extends TextLine {

  private final String include;
  private final int headerOffset;

  private IncludeCommandLine( final String text, final String include, final int headerOffset ) {
    super( text );
    this.include = include;
    this.headerOffset = headerOffset;
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

  @Override
  public IncludeCommandLine copyFromIndentedText( final String indentedText ) {
    return new IncludeCommandLine( indentedText, include, headerOffset );
  }

  public static IncludeCommandLine of( final String text ) {
    final String parametersAsSingleString = StringUtils.substringBetween( text, "(", ")" );
    final String[] parameters = parametersAsSingleString.split( "\\s*,\\s*" );

    final String include = readParameter( parameters, 0, null );
    final int headerOffset = Integer.parseInt( readParameter( parameters, 1, "0" ) );

    return new IncludeCommandLine( text, include, headerOffset );
  }

  private static String readParameter( final String[] parameters, int index, String defaultValue ) {
    if ( parameters.length <= index ) {
      return defaultValue;
    }

    final String value = parameters[index];

    if ( value.startsWith( "\"" ) && value.endsWith( "\"" ) ) {
      return StringUtils.substringBetween( value, "\"" );
    }

    return value;
  }

  @Override
  public String toString() {
    return String.format( "IncludeCommandLine{indentation=%d, include=%s, headerOffset=%s}", indentation, include, headerOffset );
  }
}
