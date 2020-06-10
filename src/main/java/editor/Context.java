package editor;

import com.google.common.base.Preconditions;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Context {

  private final Map<String, MetadataFile> metadataByName;
  private final EditorFile file;

  private Context( final EditorFile file, final Map<String, MetadataFile> metadataByName ) {
    Preconditions.checkNotNull( file );
    Preconditions.checkNotNull( metadataByName );

    this.file = file;
    this.metadataByName = createMapWith( metadataByName );
  }

  public static Context scan( final Collection<Path> directories ) {
    final Map<String, MetadataFile> metadataByName =
      directories.stream()
        .flatMap( Context::walk )
        .filter( Context::filter )
        .map( MetadataFileParser::parse )
        .collect( Collectors.toMap( MetadataFile::getName, a -> a ) );

    return new Context( EditorFile.empty(), metadataByName );
  }

  public Context entryPoint( final String entryPoint ) {
    final EditorFile file = findMetadata( entryPoint )
      .orElseThrow( () -> new RuntimeException( String.format( "Entry point '%s' was not found", entryPoint ) ) )
      .readEditorFile();
    return new Context( file, metadataByName );
  }

  public Optional<MetadataFile> findMetadata( final String name ) {
    return Optional.ofNullable( metadataByName.get( name ) );
  }

  public boolean containsType( final Class<?> type ) {
    return file
      .stream()
      .map( Line::getClass )
      .anyMatch( type::isAssignableFrom );
  }

  private static boolean filter( final Path path ) {
    return Files.isRegularFile( path ) && path.getFileName().toString().endsWith( ".json" );
  }

  private static Stream<Path> walk( final Path path ) {
    try {
      return Files.walk( path );
    } catch ( final IOException e ) {
      final String message = String.format( "Failed to list files under %s", path );
      throw new RuntimeException( message, e );
    }
  }

  private static Map<String, MetadataFile> createMapWith( final Map<String, MetadataFile> metadataByName ) {
    final Map<String, MetadataFile> map = new TreeMap<>( String.CASE_INSENSITIVE_ORDER );
    map.putAll( metadataByName );
    return Collections.unmodifiableMap( map );
  }

  @Override
  public String toString() {
    return String.format( "Context{metadataByName=%s}", metadataByName );
  }

  public Context resolve() {
    final List<Line> resolved = file.stream().flatMap( line -> line.resolve( this ) ).collect( Collectors.toList() );
    return new Context( new EditorFile( resolved ), metadataByName );
  }

  public Stream<Line> stream() {
    return file.stream();
  }

  public void writeTo( final OutputStream output, final Charset charset ) {
    file.writeTo( output, charset );
  }
}
