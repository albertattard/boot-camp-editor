package editor;

import java.util.List;
import java.util.Objects;

public class LineParsers {

  public static Line parse( final String line ) {
    Objects.requireNonNull( line );

    return parsers.stream()
      .filter( parser -> parser.match( line ) )
      .findFirst()
      .orElseThrow( () -> new IllegalArgumentException( "Cannot parse line " + line ) )
      .parse( line );
  }

  private static final List<LineParser<?>> parsers = List.of(
    HeaderLineParser.instance(),
    IncludeCommandLineParser.instance(),
    TextLineParser.instance()
  );

  private LineParsers() {
  }
}
