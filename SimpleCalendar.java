import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleCalendar {
	public static void main(String[] args)
	{
		ArrayList<Event> events = new ArrayList<Event>();
		setEventList(events);
		events.sort(new sortEvent());
		MainPage test = new MainPage(events);
		test.getNextMonth().addMouseListener(new mouseClickedListener(test));
		test.getPrevMonth().addMouseListener(new mouseClickedListener(test));
		test.getQuit().addMouseListener(new mouseClickedListener(test));
	}
	
	public static void setEventList(ArrayList<Event> e) 
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(new File("events.txt")));
			String str = br.readLine();
			while(str != null) {
				String[] strs = str.split("___");
				e.add(new Event(strs[1], strs[0], strs[2], strs[3]));
				str = br.readLine();
			}
			br.close();
		}
		catch(IOException ioe)
		{
			System.out.println("Error");
		}
	}
}
