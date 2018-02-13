import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.io.*;

public class MainPage {
	private JFrame mainFrame;
	private JPanel lPanel;
	private JPanel rPanel;
	private JPanel calendarPanel;
	private JPanel agendaPanel;
	private String[] months = {"S", "M", "T", "W", "T", "F", "S"};
	private JButton prevMonth;
	private JButton nextMonth;
	private JButton quit;
	private JPanel dayPanel;
	private Calendar firstDay;
	private JLabel month;
	private JLabel dayLabel;
	private JLabel lastLabel = new JLabel();
	private ArrayList<Event> events = new ArrayList<Event>();
	private JLabel picLbl;
	private JScrollPane scroll;
	
	public MainPage(ArrayList<Event> e) {
		mainFrame = new JFrame();
		mainFrame.setSize(800, 400);
		events.addAll(e);
		
		lPanel = new JPanel();
		lPanel.setPreferredSize(new Dimension(500, 50));
		prevMonth = new JButton("<");
		prevMonth.setName("prevMonth");
		nextMonth = new JButton(">");
		nextMonth.setName("nextMonth");
		lPanel.setLayout(new FlowLayout());
		lPanel.add(prevMonth);
		lPanel.add(nextMonth);
		
		rPanel = new JPanel();
		quit = new JButton("Quit");
		quit.setName("quit");
		rPanel.setPreferredSize(new Dimension(200, 50));
		rPanel.setLayout(new FlowLayout());
		rPanel.add(quit);
		
		calendarPanel = new JPanel();
		calendarPanel.setPreferredSize(new Dimension(200, 300));
		calendarPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		JButton create = new JButton("CREATE");
		create.setName("create");
		create.addMouseListener(new mouseClickedListener(this));
		create.setPreferredSize(new Dimension(100, 50));
		calendarPanel.add(create);
		Calendar c = new GregorianCalendar();
		month = new JLabel("  "+stringMonth(c.get(Calendar.MONTH))+" "+c.get(Calendar.YEAR));
		month.setPreferredSize(new Dimension(200, 30));
		calendarPanel.add(month);
		
		dayPanel = new JPanel();
		GridLayout gLay = new GridLayout(7, 7);
		gLay.setHgap(10);
		gLay.setVgap(3);
		dayPanel.setLayout(gLay);
		firstDay = new GregorianCalendar(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1);
		setDayPanel(firstDay, true);
		calendarPanel.add(dayPanel);
		
		agendaPanel = new JPanel();
		agendaPanel.setPreferredSize(new Dimension(500, 300));
		agendaPanel.setLayout(new FlowLayout());
		
		JPanel jp1 = new JPanel();
		jp1.setPreferredSize(new Dimension(500, 30));
		dayLabel = new JLabel(stringDay(c.get(Calendar.DAY_OF_WEEK))+" "+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH));
		jp1.add(dayLabel);
		agendaPanel.add(jp1);
		picLbl = new JLabel(new ImageIcon(scheduleDraw(events, c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))));
		scroll = new JScrollPane(picLbl);
		scroll.setPreferredSize(new Dimension(500, 200));
		agendaPanel.add(scroll);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(750, 400));
		mainPanel.setLayout(new FlowLayout());
		mainPanel.add(lPanel);
		mainPanel.add(rPanel);
		mainPanel.add(calendarPanel);
		mainPanel.add(agendaPanel);
		
		mainFrame.setLayout(new FlowLayout());
		mainFrame.add(mainPanel);
		mainFrame.setVisible(true);
	}
	
	public String stringMonth(int i)
	{
		String months[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		return months[i];
	}
	
	public String stringDay(int i)
	{
		String days[] = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		return days[i-1];
	}

	public JFrame getMainFrame() { return mainFrame; }
	public JPanel getAgendaPanel() { return agendaPanel; }
	public JButton getNextMonth(){ return nextMonth; }
	public JButton getPrevMonth(){ return prevMonth; }
	public JButton getQuit() { return quit; }
	public JPanel getDayPanel() { return dayPanel; }
	public Calendar getFirstDay() { return firstDay; }
	public void setFirstDay(Calendar c) { firstDay = c; }
	public JLabel getLastLabel() { return lastLabel; }
	public void setLastLabel(JLabel l) { lastLabel = l; }
	public void setDayLabel(String s) { dayLabel.setText(s); }
	public void setEvents(ArrayList<Event> e) { events.addAll(e); }
	public ArrayList<Event> getEvents() { return events; }
	public JScrollPane getScroll() { return scroll; }
	public JLabel getPicLbl() { return picLbl; }
	
	public void setDayPanel(Calendar c, boolean first) {
		dayPanel.removeAll();
		int dayCount = 0;
		month.setText("  "+stringMonth(c.get(Calendar.MONTH))+" "+c.get(Calendar.YEAR));
		
		for(int i = 0; i < 7; i++)
		{
			JLabel dayLabel = new JLabel(months[i]);
			dayLabel.setHorizontalAlignment(JLabel.CENTER);
			dayPanel.add(dayLabel);
		}
		
		for(int i = 0; i < c.get(Calendar.DAY_OF_WEEK)-1; i++)
		{
			 JLabel dayLabel = new JLabel();
			 dayLabel.setHorizontalAlignment(JLabel.CENTER);
			 dayPanel.add(dayLabel);
			 dayCount++;
		}
		for(int i = 1; i <= c.getActualMaximum(Calendar.DATE); i++)
		{
			JLabel dayLabel = new JLabel(i+"");
			Calendar today = new GregorianCalendar();
			if(i == today.get(Calendar.DATE) && today.get(Calendar.MONTH) == c.get(Calendar.MONTH) && first) 
			{ 
				dayLabel.setOpaque(true);
				dayLabel.setBackground(Color.LIGHT_GRAY); 
				setLastLabel(dayLabel);
			}
			dayLabel.setName((c.get(Calendar.MONTH)+1)+"/"+i+"/"+c.get(Calendar.YEAR));
			dayLabel.setHorizontalAlignment(JLabel.CENTER);
			dayLabel.addMouseListener(new mouseClickedListener(this));
			dayPanel.add(dayLabel);
			dayCount++;
		}
		for(int i = dayCount; i < 42; i++)
		{
			JLabel dayLabel = new JLabel();
			dayLabel.setHorizontalAlignment(JLabel.CENTER);
			dayPanel.add(dayLabel);	
		}
		dayPanel.revalidate();
	}
	
	public Image loadImage() {
		try {
			BufferedImage img = ImageIO.read(new File("calendar.png"));
			return img;
		}
		catch(IOException ioe) {
			System.out.println("TEST");
			return null;
		}
	}
	
	public Image scheduleDraw(ArrayList<Event> ev, int month, int day) {
		ArrayList<Event> drawEvent = new ArrayList<Event>();
		ArrayList<Position> positions = new ArrayList<Position>();
		ev.sort(new sortEvent());
		int index = 0, first = 0;
		Image i = loadImage();
		for(Event e : ev)
		{
			if(Integer.parseInt(e.getDate())==day && Integer.parseInt(e.getMonth())==month+1)
			{
				drawEvent.add(e);
			}
		}
		
		for(Event e : drawEvent)
		{
			Graphics g = i.getGraphics();
			g.setColor(Color.LIGHT_GRAY);
			index = 0;
			double endMin = e.getDoubleEndMin();
			double startMin = e.getDoubleStartMin();
			int endHour = e.getIntEndHour();
			int startHour = e.getIntStartHour();
			double temp = startMin/60;
			double temp2 = endMin/60;
			int yStart = (42*startHour)+(int)(42*temp);
			int yEnd = yStart + (42*(endHour-startHour)+(int)(42*(temp2-temp)));
			int row = 0;
			
			if(first != 0)
			{
				for(Position p : positions)
				{
					if(yStart < p.getEnd())
					{
						if(row == p.getRow())
						{
							row++;
						}
					}
				}
			}
			
			g.fillRect(70+(150*row)+(row*3), (42*startHour)+(int)(42*temp), 150, (42*(endHour-startHour)+(int)(42*(temp2-temp))));
			g.setColor(Color.BLACK);
			g.drawRect(70+(150*row)+(row*3), (42*startHour)+(int)(42*temp), 150, (42*(endHour-startHour)+(int)(42*(temp2-temp))));
			g.setColor(Color.BLACK);
			if(e.getEventName().length() > 25)
			{
				for(int x = 0; x < e.getEventName().length()/23; x++)
				{
					g.drawString(e.getEventName().substring(x*23, (x+1)*23), 74+(150*row)+(row*3), 15+(42*startHour)+(int)(42*temp)+15*index);
					index++;
				}
				g.drawString(e.getEventName().substring(index*23, e.getEventName().length()), 74+(150*row)+(row*3), 15+(42*startHour)+(int)(42*temp)+15*index);
				index++;
			}
			else 
			{ 
				g.drawString(e.getEventName(), 74+(150*row)+(row*3), 15+(42*startHour)+(int)(42*temp));
				index++;
			}
			g.drawString(e.getStartTime()+" ~ "+e.getEndTime(), 74+(150*row)+(row*3), 15+(42*startHour)+(int)(42*temp)+15*index);
			g.dispose();
			positions.add(new Position(yStart, yEnd, row));
			first++;
		}
		return i;
	}
}
