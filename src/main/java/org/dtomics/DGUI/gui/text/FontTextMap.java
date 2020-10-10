package org.dtomics.DGUI.gui.text;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.dtomics.DGUI.gui.text.font.Font;

import java.util.ArrayList;

@Getter
@RequiredArgsConstructor
public class FontTextMap {

    @NonNull
    private final Font font;
    private ArrayList<D_TextBox> textBoxes;


    public void addText(final D_TextBox textBox) {
        if (textBoxes == null) {
            textBoxes = new ArrayList<>();
        }
        textBoxes.add(textBox);
    }

    public void removeText(final D_TextBox textBox) {
        if (textBoxes == null) {
            return;
        }
        textBoxes.remove(textBox);
    }

}
