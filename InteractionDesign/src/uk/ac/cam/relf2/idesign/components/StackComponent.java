package uk.ac.cam.relf2.idesign.components;

import java.awt.Graphics2D;
import java.util.Iterator;

public class StackComponent extends GraphicComponent {
	
	@Override
	public void repaint(Graphics2D g1) {
		if(!getVisible()) return;
		
		Graphics2D g = (Graphics2D) g1.create();
		g.translate(this.getX(), this.getY());
		
		int height = 0;

		this.paint(g);
		Iterator<GraphicComponent> iterator = mComponents.iterator();
		GraphicComponent comp;
		while(iterator.hasNext()) {
			comp = iterator.next();
			comp.repaint(g);
			g.translate(0, comp.getHeight());
			height += comp.getHeight();
		}
		this.setSize(getWidth(), height);
		g.dispose();
	}
}
