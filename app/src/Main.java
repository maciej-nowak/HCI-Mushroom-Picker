
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// main

public class Main
{
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				setNewGame(mainFrame.height,mainFrame.width);
				mainFrame.createNewGameButton();
			}
		});
	}
	
	// ustawiamy nowa gre - domyslne wartosci na poczatek dla
	// odpowiednich obiektow zawartych w glownej ramce
	public static void setNewGame(int height,int width)
	{
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setTitle("Smart Mushroom Picker");
		mainFrame.addForest();
		mainFrame.addStatistics();
		mainFrame.addDialog();
		mainFrame.setLocation(200, 10);
		mainFrame.setVisible(true);
	}
	
	private static MainFrame mainFrame=new MainFrame();
}