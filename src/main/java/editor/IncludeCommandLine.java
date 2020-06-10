package editor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.Stream;

import static editor.Line.calculateIndentation;

@Getter
@EqualsAndHashCode( callSuper = true )
public class IncludeCommandLine extends CommandLine {

  private final String include;
  private final int headerOffset;

  private IncludeCommandLine( final int indentation, final String include, final int headerOffset ) {
    super( indentation );
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
      .map( line -> line.indentBy( getIndentation() ) )
      .map( this::adjustHeader );
  }

  @Override
  public IncludeCommandLine indentBy( final int indentation ) {
    return new IncludeCommandLine( this.indentation + indentation, include, headerOffset );
  }

  private Line adjustHeader( final Line line ) {
    if ( headerOffset > 0 && line instanceof HeaderLine ) {
      final HeaderLine headerLine = (HeaderLine) line;
      return headerLine.toLowerLevelBy( headerOffset );
    }

    return line;
  }

  public static IncludeCommandLine of( final String text ) {
    final int indentation = calculateIndentation( text );

    final String parametersAsSingleString = StringUtils.substringBetween( text, "(", ")" );
    final String[] parameters = parametersAsSingleString.split( "\\s*,\\s*" );

    final String include = readParameter( parameters, 0, null );
    final int headerOffset = Integer.parseInt( readParameter( parameters, 1, "0" ) );

    return new IncludeCommandLine( indentation, include, headerOffset );
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
