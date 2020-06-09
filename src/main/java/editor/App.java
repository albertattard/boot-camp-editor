package editor;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class App {

  public static void main( final String[] args ) throws Exception {
    final String entryPoint =
      first( args, "-e", e -> e ).orElseThrow( () -> new IllegalArgumentException( "Missing entry point" ) );

    try ( final OutputStream outputStream = outputStream( args ) ) {
      final Project project = new Project();
      directories( args ).forEach( project::addDirectory );
      project.setOutput( outputStream, StandardCharsets.UTF_8 );
      project.setEntryPoint( entryPoint );
      project.build();
    }
  }

  private static List<Path> directories( final String[] args ) {
    final List<Path> directories = list( args, "-d", Paths::get );
    return directories.isEmpty() ? List.of( Paths.get( "." ) ) : directories;
  }

  private static OutputStream outputStream( final String[] args ) {
    return newOutputStream(
      first( args, "-o", Paths::get )
        .orElse( Paths.get( "output.md" ) )
    );
  }

  private static OutputStream newOutputStream( final Path file ) {
    try {
      Files.createDirectories( file.toAbsolutePath().getParent() );
      return Files.newOutputStream( file, StandardOpenOption.CREATE );
    } catch ( IOException e ) {
      throw new RuntimeException( "Failed to open a new output stream", e );
    }
  }

  private static <T> Optional<T> first( final String[] args, final String flag, final Function<String, T> converter ) {
    for ( int i = 0, limit = args.length - 1; i < limit; i++ ) {
      final String arg = args[i];
      if ( flag.equals( arg ) ) {
        return Optional.of( converter.apply( args[i + 1] ) );
      }
    }
    return Optional.empty();
  }

  private static <T> List<T> list( final String[] args, final String flag, final Function<String, T> converter ) {
    final List<T> parameters = new ArrayList<>();
    for ( int i = 0, limit = args.length - 1; i < limit; i++ ) {
      final String arg = args[i];
      if ( flag.equals( arg ) ) {
        parameters.add( converter.apply( args[i + 1] ) );
      }
    }
    return parameters;
  }
}
