package uk.ac.cam.relf2.idesign.components;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

public class ImageComponent extends GraphicComponent {
	
	private Image mImage;
	private boolean mKeepAspect = false;
	private double mAspect;
	
	public ImageComponent(Image image) {
		this.mImage = image;
	}
	
	/**
	 * Set the image to be drawn.
	 * 
	 * @param image - the image to be drawn
	 */
	public void setImage(Image image) {
		if(image != null) {
			mImage = image;
			mAspect = mImage.getHeight(null) / (double) mImage.getWidth(null);
		}
	}
	
	/**
	 * Whether the aspect ration of the image is kept when rendering.
	 * 
	 * @param keep - whether to keep the images aspect
	 */
	public void keepAspect(boolean keep) {
		mKeepAspect = keep;
	}

	@Override
	public int getWidth() {
		if(mKeepAspect)
			return (int) (getHeight() * mAspect);
		else 
			return super.getWidth();
	}

	@Override
	public void paint(Graphics2D g) {
		if(mImage == null) return;

		if(getBackgroundColor() == CLEAR) g.drawImage(mImage, 0, 0, this.getWidth(), this.getHeight(), null);
		else g.drawImage(mImage, 0, 0, this.getWidth(), this.getHeight(), getBackgroundColor(), null);
	}
}
