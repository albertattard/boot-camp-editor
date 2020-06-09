package editor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

public class EditorFileParser {

  public static EditorFile parse( final InputStream inputStream, final Charset charset ) {
    final BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream, charset ) );
    return new EditorFile(
      reader.lines()
        .map( LineParsers::parse )
        .collect( Collectors.toList() )
    );
  }

  private EditorFileParser() {
  }
}
