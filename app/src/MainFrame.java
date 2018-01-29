import javax.swing.*;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// glowna ramka na ktorej znajduja sie wszytskie panele

public class MainFrame extends JFrame
{
	// ustawianie rozmiaru ramki
	public MainFrame()
	{
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	// dodajemy panel lasu
	public void addForest()
	{
		forestPanel = new ForestPanel(height,width);
		add(forestPanel,BorderLayout.CENTER);
	}
	
	// dodajemy panel statystyczny
	public void addStatistics()
	{
		statistics = new StatisticsPanel(100,0,0,10,0,0);
		add(statistics,BorderLayout.WEST);
	}
	
	// dodajemy panel dialogowy
	public void addDialog()
	{
		dialogPanel = new DialogPanel(height,width);
		add(dialogPanel,BorderLayout.SOUTH);
	}
	
	// nowa gra
	public void createNewGameButton()
	{
		statistics.add(newGameButton, BorderLayout.NORTH);
		newGameButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				statistics.statistics.setText("\n                    Statystyki\t\n\n\n"+
						   " HP: "+statistics.HP+"/100\t\n\n"+
						   " Ekwipunek: \t      "+statistics.q1+"\n\n"+
						   " Eliksiry ¿ycia: \t      "+statistics.q2+"\n"+
						   " Topory: \t      "+statistics.q3+"\n"+
						   " Ksiêgi grzybów:      "+statistics.q4+"\n"+
						   " Szyszki: \t      "+statistics.q5);
				
				dialogPanel.insertButton.setEnabled(true);
				dialogPanel.dialog.setText("");
				for(int i=0;i<height;i++)for(int j=0;j<width;j++)
				{
					forestPanel.setForestImage(i, j);
					forestPanel.objects[i][j] = "green";
				}
				forestPanel.generateTrees(height, width);
				forestPanel.setPositionImage(0, 0);
				forestPanel.curHeight = 0;
				forestPanel.curWidth = 0;
				for(int i=0;i<forestPanel.trees.size();i++)
				{
					forestPanel.objects[forestPanel.trees.get(i).height][forestPanel.trees.get(i).width] = "tree";
				}
				statistics.HP = 100;
				statistics.q1 = 0;
				statistics.q2 = 0;
				statistics.q3 = 0;
				statistics.q4 = 0;
				statistics.q5 = 0;
				statistics.setData();
			}
		});
		newGameButton.setEnabled(true);
	}
	
	private ForestPanel forestPanel;
	private DialogPanel dialogPanel;
	public static StatisticsPanel statistics; 
	public static JButton newGameButton = new JButton("New round");
	public static int height = 8;
	public static int width = 10;
	public static final int DEFAULT_WIDTH = 1000;
	public static final int DEFAULT_HEIGHT = 700;
}
