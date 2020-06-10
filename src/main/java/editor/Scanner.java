package editor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.stream.Collectors.collectingAndThen;

public class Scanner {

  public static Map<String, MetadataFile> scan( final Collection<Path> directories ) {
    checkNotNull( directories );
    checkArgument( !directories.isEmpty(), "No directories provided" );

    return
      directories.stream()
        .flatMap( Scanner::walk )
        .filter( Scanner::filter )
        .map( MetadataFileParser::parse )
        .collect( collectingAndThen( collectInTreeMap(), Collections::unmodifiableMap ) );
  }

  private static Collector<MetadataFile, ?, TreeMap<String, MetadataFile>> collectInTreeMap() {
    return Collectors.toMap(
      MetadataFile::getName,
      Function.identity(),
      handleCollisions(),
      createTreeMap()
    );
  }

  private static Supplier<TreeMap<String, MetadataFile>> createTreeMap() {
    return () -> new TreeMap<>( String.CASE_INSENSITIVE_ORDER );
  }

  private static BinaryOperator<MetadataFile> handleCollisions() {
    return ( a, b ) -> {
      throw new IllegalArgumentException( String.format( "Duplicate name found with name: %s", a.getName() ) );
    };
  }

  private static Stream<Path> walk( final Path path ) {
    try {
      return Files.walk( path );
    } catch ( final IOException e ) {
      final String message = String.format( "Failed to walk path: %s", path );
      throw new RuntimeException( message, e );
    }
  }

  private static boolean filter( final Path path ) {
    return Files.isRegularFile( path ) && path.getFileName().toString().endsWith( ".json" );
  }

  private Scanner() {
  }
}
