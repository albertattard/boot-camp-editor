package editor;

public interface LineParser<T extends Line> {

  T parse( String line );

  boolean match( String line );
}
