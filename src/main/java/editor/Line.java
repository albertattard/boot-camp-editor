package editor;

public class Line {

  private final String text;

  public Line( final String text ) {
    this.text = text;
  }

  public static Line parse( final String text ) {
    return new Line( text );
  }
}
