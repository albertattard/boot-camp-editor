package editor;

import java.util.regex.Pattern;

public class IncludeCommandLineParser implements LineParser<IncludeCommandLine> {

  @Override
  public IncludeCommandLine parse( final String line ) {
    return IncludeCommandLine.of( line );
  }

  @Override
  public boolean match( final String line ) {
    return PATTERN.matcher( line ).matches();
  }

  public static LineParser<IncludeCommandLine> instance() {
    return INSTANCE;
  }

  private static final Pattern PATTERN = Pattern.compile( "\\s*\\{include\\(\".+\"(|\\s*,\\s*\\d+)\\)}\\s*" );

  private static final IncludeCommandLineParser INSTANCE = new IncludeCommandLineParser();

  private IncludeCommandLineParser() {
  }
}
