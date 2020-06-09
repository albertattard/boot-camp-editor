package editor;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class IncludeCommandLineParser implements LineParser<IncludeCommandLine> {

  @Override
  public IncludeCommandLine parse( final String line ) {
    final String[] parts = line.split( "[\\(\\)]" );
    final String include = StringUtils.substringBetween( parts[1], "\"" );
    return new IncludeCommandLine( line, include );
  }

  @Override
  public boolean match( final String line ) {
    return PATTERN.matcher( line ).matches();
  }

  public static LineParser<IncludeCommandLine> instance() {
    return INSTANCE;
  }

  private static final Pattern PATTERN = Pattern.compile( "\\s*\\{include\\(\"[\\w-]+\"\\)\\}\\s*" );

  private static final IncludeCommandLineParser INSTANCE = new IncludeCommandLineParser();

  private IncludeCommandLineParser() {
  }
}
