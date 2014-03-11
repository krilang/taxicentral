package no.ntnu.item.ttm4115.termassignment.clientframe;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.FocusManager;
import javax.swing.JTextField;

public class PlaceholderTextField extends JTextField {
	private static final long serialVersionUID = 1L;
	
	private String placeholder;

	public PlaceholderTextField(String placeholder) {
		this.placeholder = placeholder;
	}
	
	@Override
	protected void paintComponent(java.awt.Graphics g) {
		super.paintComponent(g);

		if (getText().isEmpty()
				&& !(FocusManager.getCurrentKeyboardFocusManager()
						.getFocusOwner() == this)) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setColor(Color.GRAY);
			g2.drawString(placeholder, 5, 15);
			g2.dispose();
		}
	}

}
