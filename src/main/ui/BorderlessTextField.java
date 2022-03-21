package ui;

import javax.swing.*;
import javax.swing.border.Border;

public class BorderlessTextField extends JTextField {
    @Override
    public void setBorder(Border border) {
        // no border for textfield
    }
}
