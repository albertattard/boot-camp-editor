package editor;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Link {

  private final String caption;
  private final String link;

  @Override
  public String toString() {
    return String.format( "Link{caption=%s, link=%s}", caption, link );
  }
}
