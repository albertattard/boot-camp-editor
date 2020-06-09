package editor;

import java.util.regex.Pattern;

public class CommandLineParser implements LineParser<CommandLine> {

  @Override public CommandLine parse( final String line ) {
    return new CommandLine( line );
  }

  @Override public boolean match( final String line ) {
    return PATTERN.matcher( line ).matches();
  }

  private static final Pattern PATTERN = Pattern.compile( "\\s*\\w+.*" );
}
