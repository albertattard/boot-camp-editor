package editor;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public abstract class CommandLine implements Line, NeedsToBeResolved {

  protected final int indentation;

  protected CommandLine( final int indentation ) {
    this.indentation = indentation;
  }

  @Override
  public String toString() {
    return String.format( "CommandLine{indentation=%d}", indentation );
  }
}
