package uk.ac.cam.group06.idesign;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.ac.cam.group06.api.store.DataStore;
import uk.ac.cam.relf2.idesign.components.ComponentListener;
import uk.ac.cam.relf2.idesign.components.DropDownComponent;
import uk.ac.cam.relf2.idesign.components.GraphicComponent;
import uk.ac.cam.relf2.idesign.components.ImageComponent;
import uk.ac.cam.relf2.idesign.components.TextComponent;
import uk.ac.cam.relf2.idesign.components.TextFieldComponent;
import uk.ac.cam.relf2.idesign.components.Utils;

public class SearchScreen extends GraphicComponent implements ComponentListener {

	private DropDownComponent<String> mCountry;
	private GraphicComponent mPanel;
	private ImageComponent mLoading;
	private TextFieldComponent mTextField;
	
	private static Image X_IMAGE = Utils.loadImage("/x.png");
	
	private static final Image LOADING = Utils.loadImage("/loading.png");
	private static final Image SEARCH_SYMBOL = Utils.loadImage("/search_symbol.png");
	
	private static List<String> mCities;
	
	public SearchScreen() {
		setBackgroundColor(new Color(220, 220, 220, 180));
		setSize(100, 100, false);
		
		mPanel = new GraphicComponent() {
			@Override
			public void paint(Graphics2D g) {
				g.setColor(getBackgroundColor());
				int curve = 50;
				g.fillOval(0, 0, curve, curve);
				g.fillOval(0, getHeight() - curve, curve, curve);
				g.fillOval(getWidth() - curve, getHeight() - curve, curve, curve);
				g.fillOval(getWidth() - curve, 0, curve, curve);
				
				g.fillRect(curve / 2, 0, getWidth() - curve, getHeight());
				g.fillRect(0, curve / 2, getWidth(), getHeight() - curve);
			}
		};
		mPanel.setBackgroundColor(new Color(249, 249, 249));
		mPanel.setOrigin(MIDDLE_CENTRE);
		mPanel.setPosition(50, 50, false);
		mPanel.setSize(420, 200);

		TextComponent text = new TextComponent();
		text.setBackgroundColor(new Color(180, 180, 180));
		text.setText("Choose city to get");
		text.setPosition(50, false, 50, true);
		mPanel.addComponent(text);
		
		TextComponent text2 = new TextComponent();
		text2.setBackgroundColor(new Color(180, 180, 180));
		text2.setText("weather information.");
		text2.setPosition(50, false, 80, true);
		mPanel.addComponent(text2);
		
		ImageComponent x = new ImageComponent(X_IMAGE);
		x.setOrigin(TOP_RIGHT);
		x.setBorder(SCREEN_RIGHT, SCREEN_TOP);
		x.setPosition(-20, 20, true);
		x.setSize(32, 32);
		x.setComponentListener(this);
		mPanel.addComponent(x);
		
		mLoading = new ImageComponent(LOADING) {
			private int mSpin = 0;
			
			@Override
			public void update() {
				mSpin++;
				
				if(mSpin >= 20) {
					setRotation(getRotation() - (Math.PI / 4));
					mSpin = 0;
				}
			}
		};
		mLoading.setOrigin(MIDDLE_CENTRE);
		mLoading.setPosition(50, 50, false);
		mLoading.setSize(30, 30, false);
		mLoading.keepAspect(true);
		
		mCountry = new DropDownComponent<String>(getCities());
		mCountry.setSize(28, false, 30, true);
		mCountry.setPosition(57, 70, false);
		mCountry.setChosenItem(ApplicationFrame.getCity());
		mPanel.addComponent(mCountry);
		
		mTextField = new TextFieldComponent() {
			public void onEnter(String text) {
				search();
			}
		};
		mTextField.setSize(48, false, 30, true);
		mTextField.setPosition(7, 70, false);
		mPanel.addComponent(mTextField);
		
		ImageComponent search = new ImageComponent(SEARCH_SYMBOL);
		search.setPosition(87, 70, false);
		search.setSize(30, 30);
		search.setComponentListener(new ComponentListener() {
			@Override
			public void onClicked(int x, int y) {
				search();
			}
		});
		mPanel.addComponent(search);
		
	}
	
	public static List<String> getCities() {
		if(mCities != null) return mCities;
		
		mCities = new ArrayList<String>();
		
		BufferedReader read = null;
		try {
			read = new BufferedReader(new FileReader(new File("res/cities.txt")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return mCities;
		}

		try {
			String city = read.readLine();
			
			while(city != null) {
				mCities.add(city);
				city = read.readLine();
			}
			
			read.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return mCities;
	}
	
	public void search() {
//		ApplicationFrame.setCityAndCountry(mTextField.getText(), mCountry.getChosenItem());
//	
//		final GraphicComponent screen = this;
//		
//		this.removeComponent(mPanel);
//		this.addComponent(mLoading);
//
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				DataStore.getCurrentInformation(ApplicationFrame.getCity(), ApplicationFrame.getCountryCode());
//				DataStore.getFiveDayForecast(ApplicationFrame.getCity(), ApplicationFrame.getCountryCode());
//
//				ApplicationFrame.removeComponent(screen);
//			}
//		}).start();
		
		System.out.println("search");
	}

	@Override
	public void onClicked(int x, int y) {
		this.removeComponent(mPanel);
		this.addComponent(mLoading);
		
		ApplicationFrame.removeComponent(this);
	}
	
	@Override
	protected void addedToComponent(GraphicComponent parent) {
		super.addedToComponent(parent);
		ApplicationFrame.getHomeScreen().setClickable(false);
		ApplicationFrame.getBreakdownScreen().setClickable(false);
		
		this.addComponent(mPanel);
		this.removeComponent(mLoading);
	}

	@Override
	protected void removedFromComponent(GraphicComponent parent) {
		super.removedFromComponent(parent);
		ApplicationFrame.getHomeScreen().setClickable(true);
		ApplicationFrame.getBreakdownScreen().setClickable(true);
	}
}
