package editor;

import java.util.List;
import java.util.Objects;

public class LineParsers {

  public static Line parse( final String line ) {
    Objects.requireNonNull( line );

    return PARSERS.stream()
      .filter( parser -> parser.match( line ) )
      .findFirst()
      .orElseThrow( () -> new IllegalArgumentException( String.format( "Cannot parse line %s", line ) ) )
      .parse( line );
  }

  private static final List<LineParser<?>> PARSERS = List.of(
    HeaderLineParser.instance(),
    IncludeCommandLineParser.instance(),
    TextLineParser.instance()
  );

  private LineParsers() {
  }
}
