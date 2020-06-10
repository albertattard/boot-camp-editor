package editor;

import com.google.common.base.Preconditions;

import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Stream;

public class Context {

  private final Map<String, MetadataFile> metadataByName;
  private final EditorFile file;

  public static Context of( final String entryPoint, final Collection<Path> directories ) {
    final Map<String, MetadataFile> scanned = Scanner.scan( directories );
    final EditorFile file = entryPoint( entryPoint, scanned );
    return new Context( file, scanned );
  }

  public Optional<MetadataFile> findMetadata( final String name ) {
    return Optional.ofNullable( metadataByName.get( name ) );
  }

  public boolean containsType( final Class<?> type ) {
    return file.containsType( type );
  }

  private static Map<String, MetadataFile> createMapWith( final Map<String, MetadataFile> metadataByName ) {
    final Map<String, MetadataFile> map = new TreeMap<>( String.CASE_INSENSITIVE_ORDER );
    map.putAll( metadataByName );
    return Collections.unmodifiableMap( map );
  }

  public Context resolve() {
    return new Context( file.resolve( this ), metadataByName );
  }

  public Stream<Line> stream() {
    return file.stream();
  }

  public void writeTo( final OutputStream output, final Charset charset ) {
    file.writeTo( output, charset );
  }

  private Context( final EditorFile file, final Map<String, MetadataFile> metadataByName ) {
    Preconditions.checkNotNull( file );
    Preconditions.checkNotNull( metadataByName );

    this.file = file;
    this.metadataByName = createMapWith( metadataByName );
  }

  private static EditorFile entryPoint( final String entryPoint, final Map<String, MetadataFile> metadataByName ) {
    return Optional
      .ofNullable( metadataByName.get( entryPoint ) )
      .orElseThrow( () -> new IllegalArgumentException( String.format( "Entry point '%s' was not found", entryPoint ) ) )
      .readEditorFile();
  }

  @Override
  public String toString() {
    return String.format( "Context{metadataByName=%s}", metadataByName );
  }

}
