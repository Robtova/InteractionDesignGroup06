package uk.ac.cam.group06.idesign;

import java.awt.Color;

import javax.swing.JFrame;

import uk.ac.cam.relf2.idesign.components.CircleComponent;
import uk.ac.cam.relf2.idesign.components.GraphicComponent;
import uk.ac.cam.relf2.idesign.components.GraphicPanel;
import uk.ac.cam.relf2.idesign.components.ImageComponent;
import uk.ac.cam.relf2.idesign.components.Input;
import uk.ac.cam.relf2.idesign.components.PanelListener;

public class ApplicationFrame extends JFrame implements PanelListener {
	
	private static Input globalInput;
	private static GraphicComponent homeScreen;
	private static GraphicComponent settingsScreen;
	private static GraphicComponent breakdownScreen;

	private static GraphicPanel mPanel;
	
	public ApplicationFrame(int width, int height) {
		this.setSize(width, height);
		
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setVisible(true);
		this.setBackground(new Color(0,0,0));
		
		mPanel = new GraphicPanel(this);
		mPanel.setBackgroundColor(new Color(249, 249, 249));
		mPanel.addToFrame(this);
	}
	
	public static Input getInput() {
		return globalInput;
	}
	
	public static GraphicComponent getHomeScreen() {
		return homeScreen;
	}
	
	public static GraphicComponent getBreakdownScreen() {
		return breakdownScreen;
	}
	
	public static GraphicComponent getSettingsScreen() {
		return settingsScreen;
	}

	@Override
	public void initialise(GraphicPanel panel) {
		globalInput = panel.getInput();
		
		homeScreen = new HomeScreen();
		mPanel.addComponent(homeScreen);
		
		breakdownScreen = new BreakdownScreen();
		mPanel.addComponent(breakdownScreen);
		
//		SettingsScreen settingsScreen = new SettingsScreen();
//      panel.addComponent(settingsScreen);
	}

	public static void main(String[] args) {
		ApplicationFrame frame = new ApplicationFrame(600, 900);
	}
}
