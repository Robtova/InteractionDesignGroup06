package uk.ac.cam.relf2.idesign.components;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

public class ImageComponent extends GraphicComponent {
	
	private Image mImage;
	
	public ImageComponent(Image image) {
		this.mImage = image;
	}
	
	public void setImage(Image image) {
		if(image != null) mImage = image;
	}
	
	@Override
	public void paint(Graphics2D g) {
		if(mImage == null) return;
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.drawImage(mImage, 0, 0, this.getWidth(), this.getHeight(), null);
	}
}
