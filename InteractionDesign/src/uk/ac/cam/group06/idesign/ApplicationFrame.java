package uk.ac.cam.group06.idesign;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;

import uk.ac.cam.group06.api.API;
import uk.ac.cam.group06.api.ISOCode;
import uk.ac.cam.relf2.idesign.components.CircleComponent;
import uk.ac.cam.relf2.idesign.components.GraphicComponent;
import uk.ac.cam.relf2.idesign.components.GraphicPanel;
import uk.ac.cam.relf2.idesign.components.ImageComponent;
import uk.ac.cam.relf2.idesign.components.Input;
import uk.ac.cam.relf2.idesign.components.PanelListener;

public class ApplicationFrame extends JFrame implements PanelListener {
	
	ArrayList<ISOCode> isoCodeList = API.getISOCodeList();
	
	private static Input globalInput;
	private static HomeScreen homeScreen;
	private static BreakdownScreen breakdownScreen;

	private static GraphicPanel mPanel;
	
	private static String mCity = "Cambridge";
	private static String mCountryCode = "gb";
	
	public ApplicationFrame(int width, int height) {
		this.setSize(width, height);
		
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
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

	public static void addComponent(GraphicComponent child) {
		mPanel.addComponent(child);
	}
	
	public static void removeComponent(GraphicComponent child) {
		mPanel.removeComponent(child);
	}
	
	public static String getCountryCode() {
		return mCountryCode;
	}
	
	public static String getCity() {
		return mCity;
	}
	
	public static void setCityAndCountry(String city, String country) {
		mCity = city;
		mCountryCode = country;
		homeScreen.reloadData();
		breakdownScreen.reloadData();
	}
	
	@Override
	public void initialise(GraphicPanel panel) {
		globalInput = panel.getInput();
		
		homeScreen = new HomeScreen();
		mPanel.addComponent(homeScreen);
		
		breakdownScreen = new BreakdownScreen();
		mPanel.addComponent(breakdownScreen);
		
		setVisible(true);
	}

	public static void main(String[] args) {
		ApplicationFrame frame = new ApplicationFrame(600, 900);
	}
}
