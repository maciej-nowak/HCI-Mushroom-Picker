import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Random;
import java.util.Vector;

// panel lasu

class Pair{public int height,width;}
class ForestPanel extends JPanel
{
	public ForestPanel(int height, int width)
	{
		setLayout(new BorderLayout());			
		
		// ustawianie wymiarow lasu: height x width
		
		panel = new JPanel();
		panel.setLayout(new GridLayout(height,width));
		
		// Deklaracja i inicjaizacja pól lasu
		for(int i=0;i<height;i++)
		{
			for(int j=0;j<width;j++)
			{
				fields[i][j] = new JButton();
				fields[i][j].setEnabled(true);
				setForestImage(i,j);
				panel.add(fields[i][j]);
				objects[i][j] = "green";
			}
		}
		generateTrees(height,width);
		setPositionImage(0,0);
		
		add(panel,BorderLayout.CENTER);
	}
	
	// metoda zmieniajaca aktualne pole lasu
	public static void setPositionImage(int i,int j)
	{
		try
		{
			player = ImageIO.read(new File("images\\postac.png"));
			fields[i][j].setIcon(new ImageIcon(player));
			objects[i][j] = "player";
		}
		catch (IOException ex) 
		{
			ex.printStackTrace();
		}
	}
	
	// metoda zmieniajaca ostatnio odwiedzone pole
	public static void setForestImage(int i,int j)
	{
		try
		{
			greenImage = ImageIO.read(new File("images\\forest.png"));
			fields[i][j].setIcon(new ImageIcon(greenImage));
			objects[i][j] = "green";
		}
		catch (IOException ex) 
		{
			ex.printStackTrace();
		}
	}
	
	// generowanie drzew
	public static void generateTrees(int height,int width)
	{
		Random r = new Random();
		int curHeight,curWidth;
		try
		{
			tree = ImageIO.read(new File("images\\tree1.png"));
		}
		catch (IOException ex) 
		{
			ex.printStackTrace();
		}
		trees.clear();
		for(int i=0;i<height*width/treesDensity;i++)
		{
			curHeight = r.nextInt(height);
			curWidth = r.nextInt(width);
			if(curHeight != 0 || curWidth != 0)
			{
				fields[curHeight][curWidth].setIcon(new ImageIcon(tree));
				objects[curHeight][curWidth] = "tree";
				Pair temp = new Pair();
				temp.height = curHeight;
				temp.width = curWidth;
				trees.add(temp);
			}
		}
	}
	
	// generowanie losowe obiektow w lesie
	public static void generateRandom(int height,int width)
	{	
		try
		{
			greenImage = ImageIO.read(new File("images\\forest.png"));
			boletus = ImageIO.read(new File("images\\grzyb4.png"));
			toadstool = ImageIO.read(new File("images\\grzyb3.png"));
			elixir = ImageIO.read(new File("images\\potion2.png"));
			ax = ImageIO.read(new File("images\\topor.png"));
			book = ImageIO.read(new File("images\\book2.png"));
			cone = ImageIO.read(new File("images\\cone.png"));
			tree = ImageIO.read(new File("images\\tree1.png"));
		}
		catch (IOException ex) 
		{
			ex.printStackTrace();
		}
		
		for(int i=0;i<height;i++)for(int j=0;j<width;j++)
		{
			fields[i][j].setIcon(new ImageIcon(greenImage));
			objects[i][j] = "green";
		}
		
		Random r = new Random();
		int boletusHeight,boletusWidth,toadstoolHeight,toadstoolWidth,
			elixirHeight,elixirWidth,axHeight,axWidth,bookHeight,bookWidth,coneHeight,coneWidth;
		
		for(int i=0;i<height*width/mushroomsDensity;i++)
		{
			boletusHeight = r.nextInt(height);
			boletusWidth = r.nextInt(width);
			toadstoolHeight = r.nextInt(height);
			toadstoolWidth = r.nextInt(width);
			fields[boletusHeight][boletusWidth].setIcon(new ImageIcon(boletus));
			fields[toadstoolHeight][toadstoolWidth].setIcon(new ImageIcon(toadstool));
			objects[boletusHeight][boletusWidth] = "boletus";
			objects[toadstoolHeight][toadstoolWidth] = "toadstool";
		}
		
		for(int i=0;i<height*width/itemsDensity;i++)
		{
			elixirHeight = r.nextInt(height);
			elixirWidth = r.nextInt(width);
			axHeight = r.nextInt(height);
			axWidth = r.nextInt(width);
			bookHeight = r.nextInt(height);
			bookWidth = r.nextInt(width);
			coneHeight = r.nextInt(height);
			coneWidth = r.nextInt(width);
			fields[elixirHeight][elixirWidth].setIcon(new ImageIcon(elixir));
			fields[axHeight][axWidth].setIcon(new ImageIcon(ax));
			fields[bookHeight][bookWidth].setIcon(new ImageIcon(book));
			fields[coneHeight][coneWidth].setIcon(new ImageIcon(cone));
			objects[elixirHeight][elixirWidth] = "elixir";
			objects[axHeight][axWidth] = "ax";
			objects[bookHeight][bookWidth] = "book";
			objects[coneHeight][coneWidth] = "cone";
		}
		
		for(int i=0;i<trees.size();i++)
		{
			fields[trees.get(i).height][trees.get(i).width].setIcon(new ImageIcon(tree));
			objects[trees.get(i).height][trees.get(i).width] = "tree";
		}
	}
	
	private JPanel panel;
	public static Vector <Pair> trees = new Vector <Pair> ();
	private static int treesDensity = 7;
	private static int mushroomsDensity = 10;
	private static int itemsDensity = 20;
	public static JButton [][] fields = new JButton[50][50];
	public static String [][] objects = new String[50][50];
	public static int curHeight = 0;
	public static int curWidth = 0;
	public static Image greenImage,player,boletus,toadstool,elixir,ax,book,cone,tree;
}
