package editor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Scanner {

  public static Map<String, MetadataFile> scan( final Collection<Path> directories ) {
    return
      directories.stream()
        .flatMap( Scanner::walk )
        .filter( Scanner::filter )
        .map( MetadataFileParser::parse )
        .collect( Collectors.toMap( MetadataFile::getName, a -> a ) );
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
