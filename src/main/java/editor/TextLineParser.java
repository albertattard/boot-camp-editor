package editor;

public class TextLineParser implements LineParser<TextLine> {

  public TextLine parse( final String text ) {
    return new TextLine( text );
  }

  public boolean match( final String text ) {
    return true;
  }

  public static LineParser<TextLine> instance() {
    return INSTANCE;
  }

  private static final LineParser<TextLine> INSTANCE = new TextLineParser();

  private TextLineParser() {
  }
}
