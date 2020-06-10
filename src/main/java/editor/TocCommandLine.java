package editor;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@EqualsAndHashCode( callSuper = true )
public class TocCommandLine extends TextLine implements NeedsToBeResolved {

  public TocCommandLine( final String text ) {
    super( text );
  }

  public static TocCommandLine of( final String text ) {
    return new TocCommandLine( text );
  }

  @Override
  public Stream<Line> resolve( final Context context ) {
    return Stream.empty();
  }
}
