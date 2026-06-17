package view;

import javax.swing.JButton;
import java.awt.Color;

/**
 * Utility class to apply custom styling to components,
 * ensuring they match the designed visual aesthetics at runtime.
 */
public class UIStyleUtil {
    
    /**
     * Styles a sidebar button to be flat and match the NetBeans designer.
     * 
     * @param button The button to style.
     * @param isSelected Whether this button is currently selected/active.
     */
    public static void styleSidebarButton(JButton button, boolean isSelected) {
        if (button == null) return;
        
        // Use basic button UI to bypass platform-specific gradients/borders (e.g. Nimbus/Metal)
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        
        if (isSelected) {
            button.setBackground(new Color(211, 228, 245));
            button.setForeground(new Color(37, 99, 235));
            button.setContentAreaFilled(true);
            button.setOpaque(true);
        } else {
            button.setBackground(new Color(255, 255, 255));
            button.setForeground(new Color(31, 41, 55));
            button.setContentAreaFilled(false);
            button.setOpaque(false);
        }
    }
}
