package editor;

public interface LineParser<T extends Line> {

  boolean match( final String line );

  T parse( final String line );
}
