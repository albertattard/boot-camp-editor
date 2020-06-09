package editor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Metadata {

  private final Map<String, MetadataFile> metadataByName;

  public Metadata( final Map<String, MetadataFile> metadataByName ) {
    this.metadataByName = new TreeMap<>( String.CASE_INSENSITIVE_ORDER );
    this.metadataByName.putAll( metadataByName );
  }

  public static Metadata scan( final List<Path> directories ) {
    final var metadataByName = directories.stream()
      .flatMap( Metadata::list )
      .filter( Metadata::filter )
      .map( MetadataFileParser::parse )
      .collect( Collectors.toMap( MetadataFile::getName, a -> a ) );

    return new Metadata( metadataByName );
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

  public Optional<MetadataFile> find( final String name ) {
    return Optional.ofNullable( metadataByName.get( name ) );
  }

  @Override
  public String toString() {
    return String.format( "Metadata{metadataByName=%s}", metadataByName );
  }
}
