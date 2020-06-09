package editor;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TextLine implements Line {

  protected final String text;

}
