package editor;

import java.util.regex.Pattern;

public class TocCommandLineParser implements LineParser<TocCommandLine> {

  @Override
  public TocCommandLine parse( final String line ) {
    return TocCommandLine.of( line );
  }

  @Override
  public boolean match( final String line ) {
    return PATTERN.matcher( line ).matches();
  }

  public static LineParser<TocCommandLine> instance() {
    return INSTANCE;
  }

  private static final Pattern PATTERN = Pattern.compile( "\\s*\\{toc}\\s*" );

  private static final TocCommandLineParser INSTANCE = new TocCommandLineParser();

  private TocCommandLineParser() {
  }
}
