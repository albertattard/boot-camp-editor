package editor;

import com.google.common.base.Preconditions;

import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Set;

public class Project {

  private String entryPoint;
  private Set<Path> directories = new LinkedHashSet<>();
  private OutputStream output;
  private Charset charset;

  public Project addDirectory( final Path directory ) {
    Preconditions.checkNotNull( directory );
    Preconditions.checkArgument( Files.isDirectory( directory ), "The path %s is not a directory", directory );
    Preconditions.checkArgument( !directories.contains( directory ), "The path %s was already added" );

    this.directories.add( directory );
    return this;
  }

  public Project setEntryPoint( final String entryPoint ) {
    Preconditions.checkNotNull( entryPoint );

    this.entryPoint = entryPoint;
    return this;
  }

  public Project setOutput( final OutputStream output, final Charset charset ) {
    Preconditions.checkNotNull( output );
    Preconditions.checkNotNull( charset );

    this.output = output;
    this.charset = charset;
    return this;
  }

  public void build() {
    Preconditions.checkNotNull( entryPoint, "Entry point is not set" );
    Preconditions.checkNotNull( output, "Output is not set" );
    Preconditions.checkNotNull( charset, "Charset is not set" );
    Preconditions.checkArgument( !directories.isEmpty(), "No directories were added to scan" );

    Context.of( entryPoint, directories )
      .resolve()
      .writeTo( output, charset );
  }
}
