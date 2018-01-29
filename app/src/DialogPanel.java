import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.Random;
import java.util.regex.*;
import java.lang.Integer;

// panel dialogowy

class DialogPanel extends JPanel
{
	public void Action(int vertical,int horizontal)
	{
		// Trafiamy na drzewo
		if(ForestPanel.objects[ForestPanel.curHeight+vertical][ForestPanel.curWidth+horizontal].equals("tree"))
		{
			if(MainFrame.statistics.q3 > 0)
			{
				shouldGenerate = false;
				dialog.append(" Czy chesz wyciac drzewo za pomoca topora? ");
				/*dialog.append("Wyci¹³em drzewo toporem. ");
				MainFrame.statistics.q3--;
				MainFrame.statistics.q1--;
				for(int i=0;i<ForestPanel.trees.size();i++)
				{
					if(ForestPanel.trees.get(i).height == ForestPanel.curHeight &&
					   ForestPanel.trees.get(i).width == ForestPanel.curWidth)
					{
						ForestPanel.trees.remove(i);
						break;
					}
				}*/
			}
			else
			{
				dialog.append("Nie moge isc w gore, bo tam rosnie drzewo a nie mam toporów. ");
				shouldGenerate = false;
			}
		}
		
		// Zbieramy eliksir zycia
		else if(ForestPanel.objects[ForestPanel.curHeight+vertical][ForestPanel.curWidth+horizontal].equals("elixir"))
		{
			dialog.append("Zebra³em eliksir ¿ycia. ");
			MainFrame.statistics.q2++;
			MainFrame.statistics.q1++;
		}
		
		// Zbieramy topor
		else if(ForestPanel.objects[ForestPanel.curHeight+vertical][ForestPanel.curWidth+horizontal].equals("ax"))
		{
			dialog.append("Zebra³em topór. ");
			MainFrame.statistics.q3++;
			MainFrame.statistics.q1++;
		}
		
		// Zbieramy ksiege grzybow
		else if(ForestPanel.objects[ForestPanel.curHeight+vertical][ForestPanel.curWidth+horizontal].equals("book"))
		{
			dialog.append("Zebra³em ksiêgê grzybów. ");
			MainFrame.statistics.q4++;
			MainFrame.statistics.q1++;
		}
		
		// Zbieramy szyszke
		else if(ForestPanel.objects[ForestPanel.curHeight+vertical][ForestPanel.curWidth+horizontal].equals("cone"))
		{
			dialog.append("Zebra³em szyszke. ");
			MainFrame.statistics.q5++;
			MainFrame.statistics.q1++;
		}
		
		else
		{
			
			// Zbieramy borowika :)
			if(ForestPanel.objects[ForestPanel.curHeight+vertical][ForestPanel.curWidth+horizontal].equals("boletus"))
			{
				int change = min(StatisticsPanel.HP+10,100)-MainFrame.statistics.HP;
				dialog.append("Znalazlem borowika. HP wzroslo mi o " + change + ". ");
				MainFrame.statistics.HP = min(StatisticsPanel.HP+10,100);
			}
			
			// Zbieramy muchomora :(
			else if(ForestPanel.objects[ForestPanel.curHeight+vertical][ForestPanel.curWidth+horizontal].equals("toadstool"))
			{
				if(MainFrame.statistics.q4 > 0)
				{
					dialog.append("Natrafilem ba muchomora, ale go wyrzucilem, bo w ksiedze figuruje jako grzyb trujacy. ");
					Random r = new Random();
					int check = r.nextInt(2);
					if(check == 0)
					{
						dialog.append("\n\tO nie! Przez przypadek zgubilem ksiege grzybow!\n");
						MainFrame.statistics.q4--;
						MainFrame.statistics.q1--;
					}
				}
				else
				{
					if(MainFrame.statistics.HP <= 50)
					{
						MainFrame.statistics.HP = 0;
						dialog.append("Koniec gry. Doznalem smiertelnego zatrucia. ");
					
						// Blokuje mozliwosc wstawiania txtu do pola wiadomosci
						insertButton.setEnabled(false);
					}
					else
					{
						dialog.append("Zebralem muchomora. HP spadlo mi o 50. ");
						MainFrame.statistics.HP -= 50;
					}
				}
			}
			
			ForestPanel.setForestImage(ForestPanel.curHeight,ForestPanel.curWidth);
		}
		ForestPanel.curHeight+=vertical;
		ForestPanel.curWidth+=horizontal;
		
		System.out.println(ForestPanel.curHeight);
		System.out.println(ForestPanel.curWidth);
	}
	
	public DialogPanel(int height,int width)
	{
		h = height;
		w = width;
		setLayout(new BorderLayout());			
		
		// ustawianie pola z informacja o pomocy oraz
		// okna dialogowego z opcja przewijania
		add(info, BorderLayout.NORTH);
		add(scrollPanel,BorderLayout.CENTER);
		info.setEditable(false);
		info.setText(" wpisz 'pomoc', aby uzyskac pomoc\trozmiar planszy: "
				   		+height+" x "+width);
		//info.text(color);
		dialog.setBackground(color);
		dialog.setForeground(textColor);
		dialog.setEnabled(false);
		
		// wstawianie pola wysylania wiadomosci wraz z przyciskiem 'wstaw'
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(2,1));
		message.setBackground(messageBColor);
		message.setForeground(messageFColor);
		panel.add(message);
		panel.add(insertButton);
		
		// Akcja dla przycisku 'wstaw'. Wykorzystuje informacje zawarta 
		// w tablicy 'fields' lasu zainicjowanej w klasie ForestPanel
		insertButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				shouldGenerate = true;
				
				dialog.append("[ user ]:\t"+message.getText()+"\n[ postac ]:\t");
				
				String gora1 = "góra", 		dol1 = "dó³", 			lewo1 = "lewo", 	prawo1 = "prawo",
					   gora2 = "góre", 		dol2 = "dól", 			lewo2 = "lewa", 	prawo2 = "prawa",
					   gora3 = "górê", 		dol3 = "do³", 			lewo3 = "lew¹", 	prawo3 = "praw¹",
					   gora4 = "gór¹", 		dol4 = "dol",			lewo4 = "wschod",	prawo4 = "zachod",
					   gora5 = "gora", 		dol5 = "dolu",			lewo5 = "wschód",	prawo5 = "zachód",
					   gora6 = "gore", 		dol6 = "do³u",
					   gora7 = "gorê", 		dol7 = "dolem",
					   gora8 = "gor¹", 		dol8 = "do³em",
					   gora9 = "gory",		dol9 = "poludnie",
					   gora10 = "góry",		dol10 = "po³udnie",
					   gora11 = "polnoc",
					   gora12 = "pólnoc",
					   gora13 = "po³noc",
					   gora14 = "pó³noc",
					   
					   pomoc1 = "pomoc",	eliksir1 = "eliksir",	wiek1 = "wiek",	 imie1 = "imie",
					   pomoc2 = "pomocy",	eliksir2 = "eliksiru",	wiek2 = "wieku", imie2 = "imiê",
					   pomoc3 = "pomoca",	eliksir3 = "eliksirem",	wiek3 = "lat",	 imie3 = "imieniem",
					   pomoc4 = "pomocny",											 imie4 = "nazywasz",
					   pomoc5 = "pomocna",											 imie5 = "zwa",
					   pomoc6 = "pomoc¹",											 imie6 = "zw¹",
					   pomoc7 = "pomocn¹",											 imie7 = "ciebiemowia",
					   pomoc8 = "pomoz",											 imie8 = "ciebiemowi¹",
					   pomoc9 = "pomóz",											 imie9 = "ciebiemówia",
					   pomoc10 = "pomo¿",											 imie10 = "ciebiemówi¹",
					   pomoc11 = "pomó¿",											 imie11 = "ciebie mowia",
							   														 imie12 = "ciebie mowi¹",
							   														 imie13 = "ciebie mówia",
							   														 imie14 = "ciebie mówi¹",
							   														 imie15 = "kimjestes",
							   														 imie16 = "kimjesteœ",
							   														 imie17 = "kim jestes",
							   														 imie18 = "kim jesteœ",
					   
					   interests1 = "interesujesz",		 samopoczucie1 = "czujesz",			pogoda1 = "pogoda",
					   interests2 = "zainteresowania",	 samopoczucie2 = "siemasz", 		pogoda2 = "pogode",
					   interests3 = "zainteresowaniami", samopoczucie3 = "siêmasz",			pogoda3 = "pogodê",
					   									 samopoczucie4 = "sie masz",		pogoda4 = "aura",
					   									 samopoczucie5 = "siê masz",		pogoda5 = "aure",
					   									 samopoczucie6 = "samopoczucie",	pogoda6 = "aurê",
					   									 samopoczucie7 = "jakleci",
					   									 samopoczucie8 = "jak leci",
					   									 samopoczucie9 = "coslychac",
					   									 samopoczucie10 = "cos³ychac",
					   									 samopoczucie11 = "coslychaæ",
					   									 samopoczucie12 = "cos³ychaæ",
					   									 samopoczucie13 = "co slychac",
					   									 samopoczucie14 = "co s³ychac",
					   									 samopoczucie15 = "co slychaæ",
					   									 samopoczucie16 = "co s³ychaæ";				   									 
				
				if(message.getText().toLowerCase().matches(".* nie .*"))
				{
					dialog.append("Dobrze, bedzie tak jak chcesz. ");
					shouldGenerate = false;
				}
				
				else if(message.getText().toLowerCase().contains(gora1)||
				   message.getText().toLowerCase().contains(gora2)||
				   message.getText().toLowerCase().contains(gora3)||
				   message.getText().toLowerCase().contains(gora4)||
				   message.getText().toLowerCase().contains(gora5)||
				   message.getText().toLowerCase().contains(gora6)||
				   message.getText().toLowerCase().contains(gora7)||
				   message.getText().toLowerCase().contains(gora8)||
				   message.getText().toLowerCase().contains(gora9)||
				   message.getText().toLowerCase().contains(gora10)||
				   message.getText().toLowerCase().contains(gora11)||
				   message.getText().toLowerCase().contains(gora12)||
				   message.getText().toLowerCase().contains(gora13)||
				   message.getText().toLowerCase().contains(gora14))
				{
					Pattern p = Pattern.compile("([0-9])+");
					Matcher m = p.matcher(message.getText().toLowerCase());
					
					if (m.find())
					{
					    //System.out.println(m.group(1));
					    int n = Integer.parseInt(m.group(1));
					    Action(-n,0);
					}
					else if(ForestPanel.curHeight > 0)
					{
						Action(-1,0);
					}
					else
					{
						dialog.append("Nie moge isc w gore. ");
						shouldGenerate = false;
					}
				}
				
				else if(message.getText().toLowerCase().contains(dol1)||
						message.getText().toLowerCase().contains(dol2)||
						message.getText().toLowerCase().contains(dol3)||
						message.getText().toLowerCase().contains(dol4)||
						message.getText().toLowerCase().contains(dol5)||
						message.getText().toLowerCase().contains(dol6)||
						message.getText().toLowerCase().contains(dol7)||
						message.getText().toLowerCase().contains(dol8)||
						message.getText().toLowerCase().contains(dol9)||
						message.getText().toLowerCase().contains(dol10))
				{
					if(ForestPanel.curHeight < h-1)
					{	
						if(ForestPanel.objects[ForestPanel.curHeight+1][ForestPanel.curWidth].equals("tree"))
						{
							if(MainFrame.statistics.q3 > 0)
							{
								dialog.append("Wyci¹³em drzewo toporem. ");
								MainFrame.statistics.q3--;
								MainFrame.statistics.q1--;
								ForestPanel.curHeight++;
								for(int i=0;i<ForestPanel.trees.size();i++)
								{
									if(ForestPanel.trees.get(i).height == ForestPanel.curHeight &&
									   ForestPanel.trees.get(i).width == ForestPanel.curWidth)
									{
										ForestPanel.trees.remove(i);
										break;
									}
								}
							}
							else
							{
								dialog.append("Nie mogê iœæ w dó³, bo tam roœnie drzewo a nie mam toporów. ");
								shouldGenerate = false;
							}
						}
						
						// Zbieramy eliksir zycia
						else if(ForestPanel.objects[ForestPanel.curHeight+1][ForestPanel.curWidth].equals("elixir"))
						{
							dialog.append("Zebra³em eliksir ¿ycia. ");
							MainFrame.statistics.q2++;
							MainFrame.statistics.q1++;
							ForestPanel.curHeight++;
						}
						
						// Zbieramy topor
						else if(ForestPanel.objects[ForestPanel.curHeight+1][ForestPanel.curWidth].equals("ax"))
						{
							dialog.append("Zebra³em topór. ");
							MainFrame.statistics.q3++;
							MainFrame.statistics.q1++;
							ForestPanel.curHeight++;
						}
						
						// Zbieramy ksiege grzybow
						else if(ForestPanel.objects[ForestPanel.curHeight+1][ForestPanel.curWidth].equals("book"))
						{
							dialog.append("Zebra³em ksiêgê grzybów. ");
							MainFrame.statistics.q4++;
							MainFrame.statistics.q1++;
							ForestPanel.curHeight++;
						}
						
						// Zbieramy szyszke
						else if(ForestPanel.objects[ForestPanel.curHeight+1][ForestPanel.curWidth].equals("cone"))
						{
							dialog.append("Zebra³em szyszke. ");
							MainFrame.statistics.q5++;
							MainFrame.statistics.q1++;
							ForestPanel.curHeight++;
						}
						
						else
						{
							if(ForestPanel.objects[ForestPanel.curHeight+1][ForestPanel.curWidth].equals("boletus"))
							{
								int change = min(StatisticsPanel.HP+10,100)-MainFrame.statistics.HP;
								dialog.append("Znalazlem borowika. HP wzroslo mi o " + change + ". ");
								MainFrame.statistics.HP = min(StatisticsPanel.HP+10,100);
							}
							else if(ForestPanel.objects[ForestPanel.curHeight+1][ForestPanel.curWidth].equals("toadstool"))
							{
								if(MainFrame.statistics.q4 > 0)
								{
									dialog.append("Natrafilem ba muchomora, ale go wyrzucilem, bo w ksiedze figuruje jako grzyb trujacy. ");
									Random r = new Random();
									int check = r.nextInt(2);
									if(check == 0)
									{
										dialog.append("\n\tO nie! Przez przypadek zgubilem ksiege grzybow!\n");
										MainFrame.statistics.q4--;
										MainFrame.statistics.q1--;
									}
								}
								else
								{
									if(MainFrame.statistics.HP <= 50)
									{
										MainFrame.statistics.HP = 0;
										dialog.append("Koniec gry. Doznalem smiertelnego zatrucia. ");
										insertButton.setEnabled(false);
									}
									else
									{
										dialog.append("Znalazlem muchomora. HP spadlo mi o 50. ");
										MainFrame.statistics.HP -= 50;
									}
								}
							}
							MainFrame.statistics.setData();
							ForestPanel.setForestImage(ForestPanel.curHeight,ForestPanel.curWidth);
							ForestPanel.curHeight++;
						}
					}
					else
					{
						dialog.append("Nie moge isc w dol. ");
						shouldGenerate = false;
					}
				}
				
				else if(message.getText().toLowerCase().contains(lewo1)||
						message.getText().toLowerCase().contains(lewo2)||
						message.getText().toLowerCase().contains(lewo3)||
						message.getText().toLowerCase().contains(lewo4)||
						message.getText().toLowerCase().contains(lewo5))	
				{
					if(ForestPanel.curWidth > 0)
					{
						if(ForestPanel.objects[ForestPanel.curHeight][ForestPanel.curWidth-1].equals("tree"))
						{
							if(MainFrame.statistics.q3 > 0)
							{
								dialog.append("Wyci¹³em drzewo toporem. ");
								MainFrame.statistics.q3--;
								MainFrame.statistics.q1--;
								ForestPanel.curWidth--;
								for(int i=0;i<ForestPanel.trees.size();i++)
								{
									if(ForestPanel.trees.get(i).height == ForestPanel.curHeight &&
									   ForestPanel.trees.get(i).width == ForestPanel.curWidth)
									{
										ForestPanel.trees.remove(i);
										break;
									}
								}
							}
							else
							{
								dialog.append("Nie moge isc w lewo, bo tam rosnie drzewo a nie mam toporów. ");
								shouldGenerate = false;
							}
						}
						
						// Zbieramy eliksir zycia
						else if(ForestPanel.objects[ForestPanel.curHeight][ForestPanel.curWidth-1].equals("elixir"))
						{
							dialog.append("Zebra³em eliksir ¿ycia. ");
							MainFrame.statistics.q2++;
							MainFrame.statistics.q1++;
							ForestPanel.curWidth--;
						}
						
						// Zbieramy topor
						else if(ForestPanel.objects[ForestPanel.curHeight][ForestPanel.curWidth-1].equals("ax"))
						{
							dialog.append("Zebra³em topór. ");
							MainFrame.statistics.q3++;
							MainFrame.statistics.q1++;
							ForestPanel.curWidth--;
						}
						
						// Zbieramy ksiege grzybow
						else if(ForestPanel.objects[ForestPanel.curHeight][ForestPanel.curWidth-1].equals("book"))
						{
							dialog.append("Zebra³em ksiêgê grzybów. ");
							MainFrame.statistics.q4++;
							MainFrame.statistics.q1++;
							ForestPanel.curWidth--;
						}
						
						// Zbieramy szyszke
						else if(ForestPanel.objects[ForestPanel.curHeight][ForestPanel.curWidth-1].equals("cone"))
						{
							dialog.append("Zebra³em szyszke. ");
							MainFrame.statistics.q5++;
							MainFrame.statistics.q1++;
							ForestPanel.curWidth--;
						}
						
						else
						{
							if(ForestPanel.objects[ForestPanel.curHeight][ForestPanel.curWidth-1].equals("boletus"))
							{
								int change = min(StatisticsPanel.HP+10,100)-MainFrame.statistics.HP;
								dialog.append("Znalazlem borowika. HP wzroslo mi o " + change + ". ");
								MainFrame.statistics.HP = min(StatisticsPanel.HP+10,100);
							}
							else if(ForestPanel.objects[ForestPanel.curHeight][ForestPanel.curWidth-1].equals("toadstool"))
							{
								if(MainFrame.statistics.q4 > 0)
								{
									dialog.append("Natrafilem ba muchomora, ale go wyrzucilem, bo w ksiedze figuruje jako grzyb trujacy. ");
									Random r = new Random();
									int check = r.nextInt(2);
									if(check == 0)
									{
										dialog.append("\n\tO nie! Przez przypadek zgubilem ksiege grzybow!\n");
										MainFrame.statistics.q4--;
										MainFrame.statistics.q1--;
									}
								}
								else
								{
									if(MainFrame.statistics.HP <= 50)
									{
										MainFrame.statistics.HP = 0;
										dialog.append("Koniec gry. Doznalem smiertelnego zatrucia. ");
										insertButton.setEnabled(false);
									}
									else
									{
										dialog.append("Znalazlem muchomora. HP spadlo mi o 50. ");
										MainFrame.statistics.HP -= 50;
									}
								}
							}
							MainFrame.statistics.setData();
							ForestPanel.setForestImage(ForestPanel.curHeight,ForestPanel.curWidth);
							ForestPanel.curWidth--;
						}
					}
					else
					{
						dialog.append("Nie moge isc w lewo. ");
						shouldGenerate = false;
					}
				}
				
				else if(message.getText().toLowerCase().contains(prawo1)||
						message.getText().toLowerCase().contains(prawo2)||
						message.getText().toLowerCase().contains(prawo3)||
						message.getText().toLowerCase().contains(prawo4)||
						message.getText().toLowerCase().contains(prawo5))
				{
					if(ForestPanel.curWidth < w-1)
					{
						if(ForestPanel.objects[ForestPanel.curHeight][ForestPanel.curWidth+1].equals("tree"))
						{
							if(MainFrame.statistics.q3 > 0)
							{
								dialog.append("Wyci¹³em drzewo toporem. ");
								MainFrame.statistics.q3--;
								MainFrame.statistics.q1--;
								ForestPanel.curWidth++;
								for(int i=0;i<ForestPanel.trees.size();i++)
								{
									if(ForestPanel.trees.get(i).height == ForestPanel.curHeight &&
									   ForestPanel.trees.get(i).width == ForestPanel.curWidth)
									{
										ForestPanel.trees.remove(i);
										break;
									}
								}
							}
							else
							{
								dialog.append("Nie moge isc w prawo, bo tam rosnie drzewo a nie mam toporów. ");
								shouldGenerate = false;
							}
						}
						
						// Zbieramy eliksir zycia
						else if(ForestPanel.objects[ForestPanel.curHeight][ForestPanel.curWidth+1].equals("elixir"))
						{
							dialog.append("Zebra³em eliksir ¿ycia. ");
							MainFrame.statistics.q2++;
							MainFrame.statistics.q1++;
							ForestPanel.curWidth++;
						}
						
						// Zbieramy topor
						else if(ForestPanel.objects[ForestPanel.curHeight][ForestPanel.curWidth+1].equals("ax"))
						{
							dialog.append("Zebra³em topór. ");
							MainFrame.statistics.q3++;
							MainFrame.statistics.q1++;
							ForestPanel.curWidth++;
						}
						
						// Zbieramy ksiege grzybow
						else if(ForestPanel.objects[ForestPanel.curHeight][ForestPanel.curWidth+1].equals("book"))
						{
							dialog.append("Zebra³em ksiêgê grzybów. ");
							MainFrame.statistics.q4++;
							MainFrame.statistics.q1++;
							ForestPanel.curWidth++;
						}
						
						// Zbieramy szyszke
						else if(ForestPanel.objects[ForestPanel.curHeight][ForestPanel.curWidth+1].equals("cone"))
						{
							dialog.append("Zebra³em szyszke. ");
							MainFrame.statistics.q5++;
							MainFrame.statistics.q1++;
							ForestPanel.curWidth++;
						}
						
						else
						{
							if(ForestPanel.objects[ForestPanel.curHeight][ForestPanel.curWidth+1].equals("boletus"))
							{
								int change = min(StatisticsPanel.HP+10,100)-MainFrame.statistics.HP;
								dialog.append("Znalazlem borowika. HP wzroslo mi o " + change + ". ");
								MainFrame.statistics.HP = min(StatisticsPanel.HP+10,100);
							}
							else if(ForestPanel.objects[ForestPanel.curHeight][ForestPanel.curWidth+1].equals("toadstool"))
							{
								if(MainFrame.statistics.q4 > 0)
								{
									dialog.append("Natrafilem ba muchomora, ale go wyrzucilem, bo w ksiedze figuruje jako grzyb trujacy. ");
									Random r = new Random();
									int check = r.nextInt(2);
									if(check == 0)
									{
										dialog.append("\n\tO nie! Przez przypadek zgubilem ksiege grzybow!\n");
										MainFrame.statistics.q4--;
										MainFrame.statistics.q1--;
									}
								}
								else
								{
									if(MainFrame.statistics.HP <= 50)
									{
										MainFrame.statistics.HP = 0;
										dialog.append("Koniec gry. Doznalem smiertelnego zatrucia. ");
										insertButton.setEnabled(false);
									}
									else
									{
										dialog.append("Znalazlem muchomora. HP spadlo mi o 50. ");
										MainFrame.statistics.HP -= 50;
									}
								}
							}
							MainFrame.statistics.setData();
							ForestPanel.setForestImage(ForestPanel.curHeight,ForestPanel.curWidth);
							ForestPanel.curWidth++;
						}
					}
					else
					{
						dialog.append("Nie moge isc w prawo. ");
						shouldGenerate = false;
					}
				}
				
				// Pomoc
				else if(message.getText().toLowerCase().contains(pomoc1)||
				   message.getText().toLowerCase().contains(pomoc2)||
				   message.getText().toLowerCase().contains(pomoc3)||
				   message.getText().toLowerCase().contains(pomoc4)||
				   message.getText().toLowerCase().contains(pomoc5)||
				   message.getText().toLowerCase().contains(pomoc6)||
				   message.getText().toLowerCase().contains(pomoc7)||
				   message.getText().toLowerCase().contains(pomoc8)||
				   message.getText().toLowerCase().contains(pomoc9)||
				   message.getText().toLowerCase().contains(pomoc10)||
				   message.getText().toLowerCase().contains(pomoc11))
				{
					dialog.append("Twoim celem jest uzbieranie jak najwiêcej przedmiotów.\n" +
								  "	Uwazaj na muchomory, zjedzenie jednego powoduje utratê 50 HP.\n" +
								  "	Zbieraj borowiki, to HP wzroœnie mi o 10.\n" +
								  "	Runda koñczy sie w momencie utraty HP b¹dŸ wciœniecia przycisku 'Nowa runda'.\n" +
								  "\tWydawaj polecenia dotyczace jednej konkretnej rzeczy.");
					
					shouldGenerate = false;
				}
				
				else if(message.getText().toLowerCase().contains(eliksir1)||
						message.getText().toLowerCase().contains(eliksir2)||
						message.getText().toLowerCase().contains(eliksir3))
				{
					if(MainFrame.statistics.q2 > 0)
					{
						int change = min(MainFrame.statistics.HP+50,100)-MainFrame.statistics.HP;
						dialog.append("Wypilem eliksir zycia. HP wzroslo o " + change + ". ");
						MainFrame.statistics.HP = min(MainFrame.statistics.HP+50,100);
						MainFrame.statistics.q2--;
						MainFrame.statistics.q1--;
					}
					else
					{
						dialog.append("Nie mam eliksirów ¿ycia w ekwipunku :(. ");
					}
					shouldGenerate = false;
				}
				
				else if(message.getText().toLowerCase().contains(wiek1)||
						message.getText().toLowerCase().contains(wiek2)||
						message.getText().toLowerCase().contains(wiek3))
				{
					dialog.append("Mam 40 lat. ");
					shouldGenerate = false;
				}
				
				else if(message.getText().toLowerCase().contains(imie1)||
						message.getText().toLowerCase().contains(imie2)||
						message.getText().toLowerCase().contains(imie3)||
						message.getText().toLowerCase().contains(imie4)||
						message.getText().toLowerCase().contains(imie5)||
						message.getText().toLowerCase().contains(imie6)||
						message.getText().toLowerCase().contains(imie7)||
						message.getText().toLowerCase().contains(imie8)||
						message.getText().toLowerCase().contains(imie9)||
						message.getText().toLowerCase().contains(imie10)||
						message.getText().toLowerCase().contains(imie11)||
						message.getText().toLowerCase().contains(imie12)||
						message.getText().toLowerCase().contains(imie13)||
						message.getText().toLowerCase().contains(imie14)||
						message.getText().toLowerCase().contains(imie15)||
						message.getText().toLowerCase().contains(imie16)||
						message.getText().toLowerCase().contains(imie17)||
						message.getText().toLowerCase().contains(imie18))
				{
					dialog.append("Nazywam sie Alfred :). ");
					shouldGenerate = false;
				}
				
				else if(message.getText().toLowerCase().contains(interests1)||
						message.getText().toLowerCase().contains(interests2)||
						message.getText().toLowerCase().contains(interests3))
				{
					dialog.append("Interesuje sie inteligentnym zbieraniem grzybow. ");
					shouldGenerate = false;
				}
				
				else if(message.getText().toLowerCase().contains(samopoczucie1)||
						message.getText().toLowerCase().contains(samopoczucie2)||
						message.getText().toLowerCase().contains(samopoczucie3)||
						message.getText().toLowerCase().contains(samopoczucie4)||
						message.getText().toLowerCase().contains(samopoczucie5)||
						message.getText().toLowerCase().contains(samopoczucie6)||
						message.getText().toLowerCase().contains(samopoczucie7)||
						message.getText().toLowerCase().contains(samopoczucie8)||
						message.getText().toLowerCase().contains(samopoczucie9)||
						message.getText().toLowerCase().contains(samopoczucie10)||
						message.getText().toLowerCase().contains(samopoczucie11)||
						message.getText().toLowerCase().contains(samopoczucie12)||
						message.getText().toLowerCase().contains(samopoczucie13)||
						message.getText().toLowerCase().contains(samopoczucie14)||
						message.getText().toLowerCase().contains(samopoczucie15)||
						message.getText().toLowerCase().contains(samopoczucie16))
				{
					Random r = new Random();
					int check = r.nextInt(3);
					if(check == 0)
					{
						dialog.append("Czuje sie wysmienicie! ");
					}
					else if(check == 1)
					{
						dialog.append("Ujdzie :) ");
					}
					else
					{
						dialog.append("Jestem juz zmeeczoony tym chodzeniem po lesie :( ");
					}
					shouldGenerate = false;
				}
				
				else if(message.getText().toLowerCase().contains(pogoda1)||
						message.getText().toLowerCase().contains(pogoda2)||
						message.getText().toLowerCase().contains(pogoda3)||
						message.getText().toLowerCase().contains(pogoda4)||
						message.getText().toLowerCase().contains(pogoda5)||
						message.getText().toLowerCase().contains(pogoda6))
				{
					Random r = new Random();
					int check = r.nextInt(3);
					if(check == 0)
					{
						dialog.append("Pogoda jest wspaniala, sloneczko swieci! ");
					}
					else if(check == 1)
					{
						dialog.append("Do zniesienia... ");
					}
					else
					{
						dialog.append("Beznadziejna, ciagle pada :( ");
					}
					shouldGenerate = false;
				}
				
				else if(message.getText().equals("tak"))
				{
					dialog.append(" Scialem drzewo. ");
				}
				
				else if(message.getText().equals(""))
				{
					dialog.append("Nie wiem o co Ci chodzi, ale napisz coœ :). ");
					shouldGenerate = false;
				}
				
				else 
				{
					dialog.append("OK, nie zetne. ");
					shouldGenerate = false;
				}
				
				if(shouldGenerate)
				{
					dialog.append("Przeszedlem na pozycje "+"("+ForestPanel.curHeight+","+ForestPanel.curWidth+")");
					ForestPanel.generateRandom(h,w);
					ForestPanel.setPositionImage(ForestPanel.curHeight,ForestPanel.curWidth);
				}
				
				MainFrame.statistics.setData();
				dialog.append("\n");
				message.setText("");
			}
		});
		
	}
	
	private int min(int x,int y)
	{
		if(x<y)return x;
		return y;
	}
	
	private boolean shouldGenerate;
	private JPanel panel = new JPanel();
	private JTextArea info = new JTextArea();
	private JTextField message = new JTextField();
	private Color color = new Color(45,0,35);
	private Color textColor = new Color(255,255,255);
	private Color messageFColor = new Color(255,255,255);
	private Color messageBColor = new Color(0,0,0);
	private JScrollPane scrollPanel = new JScrollPane(dialog);
	private int h;
	private int w;
	public static JTextArea dialog = new JTextArea(8,1);
	public static JButton insertButton = new JButton("Wstaw");
}
