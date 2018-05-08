package uk.ac.cam.relf2.idesign.components;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class TextComponent extends GraphicComponent {
	private String mText = "";
	private int mSize = 30;
	
	public void setText(String txt, int size) {
		if(txt != null) mText = txt;
		this.setSize(300, 300);
		this.mSize = size;
	}
	
	@Override
	public void paint(Graphics2D g) {
		g.setFont(new Font("Ariel", Font.PLAIN, mSize));
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setColor(this.getBackgroundColor());
		g.drawString(mText, -g.getFontMetrics().stringWidth(mText) / 2, mSize / 2 - 2);
	}
}
