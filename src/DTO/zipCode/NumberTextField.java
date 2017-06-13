package DTO.zipCode;

import javafx.scene.control.TextField;

public class NumberTextField extends TextField {

    public NumberTextField() {
    }

    public void replaceText(int i, int i1, String string) {
        if (string.matches("[0-9]") || string.isEmpty()) {
            super.replaceText(i, i1, string);
        }
    }

    public void replaceSelection(String replacement) {
        super.replaceSelection(replacement);
    }


}
