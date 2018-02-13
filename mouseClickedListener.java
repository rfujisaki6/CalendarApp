import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.io.*;

public class mouseClickedListener extends MouseAdapter {
	private MainPage page;
	private JFrame frame;
	private JTextField txt;
	private JTextField txt2;
	private JTextField txt3;
	private JTextField txt4; 
	private JLabel lbl;
	private JLabel lbl2;
	private JButton saveButton;
	private String eventName, eventDate, startTime, endTime;
	
	public mouseClickedListener() {}
	public mouseClickedListener(MainPage p)
	{
		page = p;
	}
	public mouseClickedListener(MainPage p, JFrame f, String str1, String str2, String str3, String str4)
	{
		page = p;
		frame = f;
		eventName = str1;
		eventDate = str2;
		startTime = str3;
		endTime = str4;
	}
	
	public void mousePressed(MouseEvent e) {
		if(e.getComponent().getName().equals("nextMonth"))
		{
			Calendar c =  page.getFirstDay();
			Calendar nextC = new GregorianCalendar(c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1, 1);
			page.setFirstDay(nextC);
			page.setDayPanel(nextC, false);
		}
		else if(e.getComponent().getName().equals("prevMonth"))
		{
			Calendar c =  page.getFirstDay();
			Calendar prevC = new GregorianCalendar(c.get(Calendar.YEAR), c.get(Calendar.MONTH)-1, 1);
			page.setFirstDay(prevC);
			page.setDayPanel(prevC, false);
		}
		else if(e.getComponent().getName().equals("quit"))
		{
			System.exit(0);
		}
		else if(e.getComponent().getName().matches("[0-9]*"+"/"+"[0-9]*"+"/"+"[0-9]*"))
		{
			page.getLastLabel().setBackground(null);
			JLabel l = (JLabel)e.getSource();
			l.setBackground(Color.LIGHT_GRAY);
			l.setOpaque(true);
			String[] dayInfo = l.getName().split("/", 0);
			Calendar c = new GregorianCalendar(Integer.parseInt(dayInfo[2]), Integer.parseInt(dayInfo[0])-1, Integer.parseInt(dayInfo[1]));
			page.setDayLabel((page.stringDay(c.get(Calendar.DAY_OF_WEEK))+" "+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH)));
			page.setLastLabel(l);
			page.getPicLbl().setIcon(new ImageIcon(page.scheduleDraw(page.getEvents(), Integer.parseInt(dayInfo[0])-1, Integer.parseInt(dayInfo[1]))));
		}
		else if(e.getComponent().getName().equals("create"))
		{
			createSchedule(page.getLastLabel());
		}
	}
	
	public void createSchedule(JLabel l) {
		frame = new JFrame();
		frame.setSize(500, 150);
		frame.setLayout(new FlowLayout());
		Calendar today = new GregorianCalendar();
		int hour = today.get(Calendar.HOUR_OF_DAY);
		int minute = today.get(Calendar.MINUTE);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(500, 150));
		
		txt = new JTextField("Untitled Event");
		txt.setPreferredSize(new Dimension(500, 40));
		
		txt2 = new JTextField(l.getName());
		txt2.setPreferredSize(new Dimension(100, 35));
		txt2.setHorizontalAlignment(JTextField.CENTER);
		txt2.setEditable(false);
		
		if(minute/15==0)
		{
			txt3 = new JTextField(hour+":00");
		}
		else
		{
			txt3 = new JTextField(hour+":"+(15*(minute/15)));
		}
		txt3.setPreferredSize(new Dimension(100, 35));
		txt3.setHorizontalAlignment(JTextField.CENTER);
		
		lbl = new JLabel("to");
		
		if(minute/15==0)
		{
			txt4 = new JTextField((hour+1)+":00");
		}
		else
		{
			txt4 = new JTextField((hour+1)+":"+(15*(minute/15)));
		}
		txt4.setPreferredSize(new Dimension(100, 35));
		txt4.setHorizontalAlignment(JTextField.CENTER);
		
		lbl2 = new JLabel();
		lbl2.setPreferredSize(new Dimension(20, 35));
		
		saveButton = new JButton("SAVE");
		saveButton.setName("save");
		saveButton.setPreferredSize(new Dimension(80, 35));
		saveButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						eventName = txt.getText();
						eventDate = txt2.getText();
						startTime = txt3.getText();
						endTime = txt4.getText();
						saveButtonClicked();
						String[] str = eventDate.split("/");
						page.getEvents().add(new Event(eventName, eventDate, startTime, endTime));
						page.getPicLbl().setIcon(new ImageIcon(page.scheduleDraw(page.getEvents(), Integer.parseInt(str[0])-1, Integer.parseInt(str[1]))));
					}
				});
		
		mainPanel.add(txt);
		mainPanel.add(txt2);
		mainPanel.add(txt3);
		mainPanel.add(lbl);
		mainPanel.add(txt4);
		mainPanel.add(lbl2);
		mainPanel.add(saveButton);
		frame.add(mainPanel);
		frame.setVisible(true);
	}
	
	public void saveButtonClicked() {
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("events.txt"), true));
			PrintWriter pw = new PrintWriter(bw);
			pw.println(eventDate+"___"+eventName+"___"+startTime+"___"+endTime);
			pw.close();
		}
		catch(IOException ioe)
		{
			System.out.println("TEST");
		}
		frame.setVisible(false);
	}
}
