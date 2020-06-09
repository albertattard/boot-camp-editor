package editor;

public class HeaderLineParser implements LineParser<HeaderLine> {

  @Override
  public HeaderLine parse( final String line ) {
    return new HeaderLine( line );
  }

  @Override
  public boolean match( final String line ) {
    return line.startsWith( "#" );
  }

  public static LineParser<HeaderLine> instance() {
    return INSTANCE;
  }

  private static final LineParser<HeaderLine> INSTANCE = new HeaderLineParser();

  private HeaderLineParser() {
  }
}
