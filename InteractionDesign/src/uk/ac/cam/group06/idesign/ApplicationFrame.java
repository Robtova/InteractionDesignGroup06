package uk.ac.cam.group06.idesign;

import java.awt.Color;

import javax.swing.JFrame;

import uk.ac.cam.relf2.idesign.components.CircleComponent;
import uk.ac.cam.relf2.idesign.components.GraphicComponent;
import uk.ac.cam.relf2.idesign.components.GraphicPanel;
import uk.ac.cam.relf2.idesign.components.PanelListener;

public class ApplicationFrame extends JFrame implements PanelListener {
	
	public ApplicationFrame(int width, int height) {
		this.setSize(width, height);
		
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		this.setBackground(new Color(0,0,0));
		
		GraphicPanel panel = new GraphicPanel(this);
		panel.setBackgroundColor(new Color(249, 249, 249));
		panel.addToFrame(this);
	}

	@Override
	public void initialise(GraphicPanel panel) {
		GraphicComponent homeScreen = initialiseHomeScreen();
		panel.addComponent(homeScreen);
		
		GraphicComponent breakdownScreen = new BreakdownScreen(homeScreen, panel.getInput());
		breakdownScreen.setPosition(0, 92, false);
		panel.addComponent(breakdownScreen);
	}
	
	private GraphicComponent initialiseHomeScreen() {
		GraphicComponent screen = new GraphicComponent();
		screen.setBackgroundColor(new Color(250, 250, 250));
		screen.setSize(100, 100, false);
		
		WarningRing ring = new WarningRing();
		ring.setSize(450, 450);
		ring.setPosition(75, 175);
		screen.addComponent(ring);
		
		WeatherIcon icon = new WeatherIcon();
		icon.setSize(55, 55, false);
		icon.setPosition(22.5f, 22.5f, false);
		icon.setBackgroundColor(new Color(0xD3FFFD));
		//icon.setBackgroundColor(new Color(0x002D5B));
		ring.addComponent(icon);

		MoreOptionsButton dots = new MoreOptionsButton();
		dots.setPosition(460, 20);
		dots.setSize(100, 100);
		screen.addComponent(dots);
		
		icon.setIcon("02d");
		
		return screen;
	}
	
	public static void main(String[] args) {
		ApplicationFrame frame = new ApplicationFrame(600, 900);
	}
}
