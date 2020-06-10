package editor;

import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class Context {

  private final Map<String, MetadataFile> metadataByName;
  private final EditorFile file;

  public static Context of( final String entryPoint, final Collection<Path> directories ) {
    checkNotNull( entryPoint );
    checkNotNull( directories );
    checkArgument( !directories.isEmpty(), "No directories provided" );

    final Map<String, MetadataFile> scanned = Scanner.scan( directories );
    checkArgument( !scanned.isEmpty(), "No metadata files found within the provided directories" );

    final EditorFile file = entryPoint( entryPoint, scanned );

    return new Context( file, scanned );
  }

  public Optional<MetadataFile> findMetadata( final String name ) {
    checkNotNull( name );
    return Optional.ofNullable( metadataByName.get( name ) );
  }

  public boolean containsType( final Class<?> type ) {
    checkNotNull( type );
    return file.containsType( type );
  }

  public Stream<Line> stream() {
    return file.stream();
  }

  public void writeTo( final OutputStream output, final Charset charset ) {
    checkNotNull( output );
    checkNotNull( charset );

    file.writeTo( output, charset );
  }

  public Context resolve() {
    /* Start from this context, keep resolving until all done */
    return resolve( this );
  }

  private static Context resolve( Context context ) {
    /*
     * Keep iterating until all lines that need to be resolved are resolved.
     * Several iterations may be required, as some commands depend on others
     * to be resolved first.  Context is immutable and a new one needs to be
     * created with every iteration.  I do not like this, but I do not have
     * a solution for this yet.
     */
    while ( context.containsType( NeedsToBeResolved.class ) ) {
      /* Resolve the file, this should produce a new file with new contents */
      final EditorFile resolved = context.file.resolve( context );

      /* Verify that the file was changed, as otherwise we will loop forever */
      if ( context.hasSameContents( resolved ) ) {
        throw new IllegalStateException( "Expected the file to be updated, but was not" );
      }

      /* Create a new content and repeat */
      context = context.withFile( resolved );
    }

    return context;
  }

  private boolean hasSameContents( final EditorFile other ) {
    return this.file.equals( other );
  }

  private Context withFile( final EditorFile file ) {
    return new Context( file, metadataByName );
  }

  private static EditorFile entryPoint( final String entryPoint, final Map<String, MetadataFile> metadataByName ) {
    return Optional
      .ofNullable( metadataByName.get( entryPoint ) )
      .orElseThrow( () -> new IllegalArgumentException( String.format( "Entry point '%s' was not found", entryPoint ) ) )
      .readEditorFile();
  }

  private Context( final EditorFile file, final Map<String, MetadataFile> metadataByName ) {
    this.file = file;
    this.metadataByName = metadataByName;
  }

  @Override
  public String toString() {
    return String.format( "Context{file=%s, metadataByName=%s}", file, metadataByName );
  }
}
