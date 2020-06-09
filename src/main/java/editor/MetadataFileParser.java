package editor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class MetadataFileParser {

  public static MetadataFile parse( final Path file ) {
    Preconditions.checkNotNull( file );
    Preconditions.checkArgument( Files.isRegularFile( file ), "The path %s is not a file", file.toAbsolutePath() );

    try {
      final ObjectMapper mapper = new ObjectMapper();
      mapper.setPropertyNamingStrategy( PropertyNamingStrategy.KEBAB_CASE );
      final MetadataFile metadata = mapper.readValue( Files.readString( file, StandardCharsets.UTF_8 ), MetadataFile.class );

      Preconditions.checkNotNull( metadata, "Failed to parse object from file %s", file );

      if ( metadata.getEditorFile() == null ) {
        metadata.setEditorFile( toEditorFile( file ) );
      }

      metadata.setMetadataFile( file );

      return metadata;
    } catch ( IOException e ) {
      throw new RuntimeException( String.format( "Failed to parse file %s", file ), e );
    }
  }

  private static String toEditorFile( final Path file ) {
    final String fileNameWithExtension = file.getFileName().toString();
    final String fileNameWithoutExtension = StringUtils.substringBeforeLast( fileNameWithExtension, "." );
    return String.format( "%s.md", fileNameWithoutExtension );
  }

  private MetadataFileParser() {
  }
}
