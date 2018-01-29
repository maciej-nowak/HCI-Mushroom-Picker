import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

// panel statystyczny

class StatisticsPanel extends JPanel
{
	public StatisticsPanel(int a,int b,int c,int d,int e,int f)
	{
		HP = a;
		q1 = b;
		q2 = c;
		q3 = d;
		q4 = e;
		q5 = f;
		setLayout(new BorderLayout());		
		add(statistics, BorderLayout.CENTER);
		statistics.setEditable(false);
		statistics.setText("\n                    Statystyki\t\n\n\n"+
						   " HP: "+HP+"/100\t\n\n"+
						   " Ekwipunek: \t      "+q1+"\n\n"+
						   " Eliksiry ¿ycia: \t      "+q2+"\n"+
						   " Topory: \t      "+q3+"\n"+
						   " Ksiêgi grzybów:      "+q4+"\n"+
						   " Szyszki: \t      "+q5);
	}
	
	public static void setData()
	{
		statistics.setText("\n                    Statystyki\t\n\n\n"+
				   " HP: "+HP+"/100\t\n\n"+
				   " Ekwipunek: \t      "+q1+"\n\n"+
				   " Eliksiry ¿ycia: \t      "+q2+"\n"+
				   " Topory: \t      "+q3+"\n"+
				   " Ksiêgi grzybów:      "+q4+"\n"+
				   " Szyszki: \t      "+q5);
	}
	
	public static JTextArea statistics = new JTextArea();
	public static int HP;
	public static int q1;
	public static int q2;
	public static int q3;
	public static int q4;
	public static int q5;

}
