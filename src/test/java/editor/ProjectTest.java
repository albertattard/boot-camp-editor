package editor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName( "Project" )
public class ProjectTest {

  @Test
  @DisplayName( "should scan one directory and find all metadata files" )
  public void shouldParseMetadataFiles() throws Exception {
    final ByteArrayOutputStream output = new ByteArrayOutputStream();

    final Path samples = Paths.get( "src/test/resources/samples" );

    new Project()
      .addDirectory( samples.resolve( "files" ) )
      .setOutput( output, StandardCharsets.UTF_8 )
      .setEntryPoint( "host-example" )
      .build();

    final List<String> expected =
      Files.readAllLines( samples.resolve( "builds/host-file.md" ), StandardCharsets.UTF_8 );

    final String text = new String( output.toByteArray(), StandardCharsets.UTF_8 );
    System.out.println( "--------------------" );
    System.out.println( text );
    System.out.println( "--------------------" );

    final List<String> actual =
      Arrays.asList( text.split( "\\n" ) );

    assertEquals( expected.size(), actual.size() );
    for ( int i = 0, lineNumber = 1, size = expected.size(); i < size; i++, lineNumber++ ) {
      assertEquals( expected.get( i ), actual.get( i ),
        String.format( "[%s] expected '%s' but found '%s'", lineNumber, expected.get( i ), actual.get( i ) ) );
    }
  }
}