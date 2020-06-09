package editor;

import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Project {

  private String entryPoint;
  private List<Path> directories = new ArrayList<>();
  private OutputStream output;
  private Charset charset;

  public Project addDirectory( final Path directory ) {
    this.directories.add( directory );
    return this;
  }

  public Project setEntryPoint( final String entryPoint ) {
    this.entryPoint = entryPoint;
    return this;
  }

  public Project setOutput( final OutputStream output, final Charset charset ) {
    this.output = output;
    this.charset = charset;
    return this;
  }

  public void build() {
    final var metadata = Metadata.scan( directories );
    final MetadataFile entryPointMetadata = metadata.find( entryPoint )
      .orElseThrow( () -> new RuntimeException( String.format( "Entry point '%s' was not found", entryPoint ) ) );

    entryPointMetadata
      .readEditorFile()
      .resolve( metadata )
      .writeTo( output, charset );
  }
}
