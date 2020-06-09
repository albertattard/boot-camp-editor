package editor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Context {

  private final Map<String, MetadataFile> metadataByName;

  private Context( final Map<String, MetadataFile> metadataByName ) {
    this.metadataByName = new TreeMap<>( String.CASE_INSENSITIVE_ORDER );
    this.metadataByName.putAll( metadataByName );
  }

  public static Context scan( final Collection<Path> directories ) {
    final Map<String, MetadataFile> metadataByName =
      directories.stream()
        .flatMap( Context::list )
        .filter( Context::filter )
        .map( MetadataFileParser::parse )
        .collect( Collectors.toMap( MetadataFile::getName, a -> a ) );

    return new Context( metadataByName );
  }

  public Optional<MetadataFile> findMetadata( final String name ) {
    return Optional.ofNullable( metadataByName.get( name ) );
  }

  private static boolean filter( final Path path ) {
    return path.getFileName().toString().endsWith( ".json" );
  }

  private static Stream<Path> list( final Path path ) {
    try {
      return Files.list( path );
    } catch ( final IOException e ) {
      final String message = String.format( "Failed to list files under %s", path );
      throw new RuntimeException( message, e );
    }
  }

  @Override
  public String toString() {
    return String.format( "Context{metadataByName=%s}", metadataByName );
  }
}
