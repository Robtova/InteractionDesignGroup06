package uk.ac.cam.relf2.idesign.components;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class TextComponent extends GraphicComponent {
	private String mText = "";
	private Font mFont = new Font("Ariel", Font.PLAIN, 30);
	
	public final static int CENTRE = 1, LEFT = 0, RIGHT = 2;
	private int mAlign = CENTRE;
	
	/**
	 * Set the text to be drawn and the size of the font to be used.
	 * 
	 * @param txt - string to be drawn
	 * @param size - size of the font
	 */
	public void setText(String txt, int size) {
		if(txt != null) mText = txt;
		this.setSize(300, 300);
		mFont = new Font("Ariel", Font.PLAIN, size);
	}
	
	/**
	 * Set the text to be drawn.
	 * 
	 * @param txt - string to be drawn
	 */
	public void setText(String txt) {
		if(txt != null) mText = txt;
		this.setSize(300, 300);
	}
	
	/**
	 * Set the font to be used in drawing the text.
	 * 
	 * @param font - new font
	 */
	public void setFont(Font font) {
		mFont = font;
	}
	
	/**
	 * Aligns the text.
	 * Use static values CENTRE, LEFT, RIGHT.
	 * 
	 * @param align - where to align
	 */
	public void setAlign(int align) {
		this.mAlign = align;
	}
	
	@Override
	public void paint(Graphics2D g) {
		g.setFont(mFont);
		
		g.setColor(this.getBackgroundColor());
		g.drawString(mText, g.getFontMetrics().stringWidth(mText) * (-0.5f + (mAlign-1) * 0.5f), mFont.getSize() / 2 - 2);
	}
}
