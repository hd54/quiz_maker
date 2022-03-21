package ui;

import javax.swing.*;
import javax.swing.border.Border;

// borderless text field for jtext since objects of this type originally have borders
public class BorderlessTextField extends JTextField {
    @Override
    public void setBorder(Border border) {
        // no border for textfield
    }
}
