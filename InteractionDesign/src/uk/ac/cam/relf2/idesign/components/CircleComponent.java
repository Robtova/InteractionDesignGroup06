package uk.ac.cam.relf2.idesign.components;

import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class CircleComponent extends GraphicComponent {

	@Override
	public void paint(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(this.getBackgroundColor());
		g.fillOval(0, 0, getWidth(), getHeight());
	}
	
}
