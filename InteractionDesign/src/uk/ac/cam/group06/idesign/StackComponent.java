package uk.ac.cam.relf2.idesign.components;

import java.awt.Graphics2D;
import java.util.Iterator;

public class StackComponent extends GraphicComponent {
	
	/**
	 * Automatically sets positions of children so that they stack on top of each other. Each child should have the default border and origin.
	 */
	public StackComponent() {
	}
	
	@Override
	public void paint(Graphics2D g) {
		int height = 0;
		
		Iterator<GraphicComponent> iterator = mComponents.iterator();
		GraphicComponent comp;
		while(iterator.hasNext()) {
			comp = iterator.next();
			comp.setY(height, true);
			height += comp.getHeight();
		}
		this.setHeight(height, true);
	}
}
