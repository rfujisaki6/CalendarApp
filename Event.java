

public class Event implements Comparable<Event> {
	private String eventName;
	private String eventDate;
	private String startTime;
	private String endTime;
	private String startHour;
	private String startMin;
	private String endHour;
	private String endMin;
	private String[] day;
	
	Event(String s1, String s2, String s3, String s4)
	{
		eventName = s1;
		eventDate = s2;
		startTime = s3;
		endTime = s4;
		day = eventDate.split("/", 0);
		String[] temp = startTime.split(":");
		startHour = temp[0];
		startMin = temp[1];
		temp = endTime.split(":");
		endHour = temp[0];
		endMin = temp[1];
	}
	
	public void setEventName() {  }
	public void setEventDate() {  }
	public void setStartTime() {  }
	public void setEndTime() {  }
	public String getEventName() { return eventName; }
	public String getEventDate() { return eventDate; }
	public String getStartTime() { return startTime; }
	public String getEndTime() { return endTime; }
	public String getYear() { return day[2]; }
	public String getMonth() { return day[0]; }
	public String getDate() { return day[1]; }
	public String getStartHour() { return startHour; }
	public String getStartMin() { return startMin; }
	public String getEndHour() { return endHour; }
	public String getEndMin() { return endMin; }
	public int getIntEndMin() { return Integer.parseInt(endMin); }
	public int getIntStartMin() { return Integer.parseInt(startMin); }
	public double getDoubleEndMin() { return Double.parseDouble(endMin); }
	public double getDoubleStartMin() { return Double.parseDouble(startMin); }
	public int getIntEndHour() { return Integer.parseInt(endHour); }
	public int getIntStartHour() { return Integer.parseInt(startHour); }
	public double getDoubleEndHour() { return Double.parseDouble(endHour); }
	public double getDoubleStartHour() { return Double.parseDouble(startHour); }

	public int compareTo(Event e) {
		
		if(this.getYear().equals(e.getYear()))
		{
			if(this.getMonth().equals(e.getMonth()))
			{
				if(this.getDate().equals(e.getDate()))
				{
					if(this.getStartHour().equals(e.getStartHour()))
					{
						return Integer.parseInt(this.getStartMin())-Integer.parseInt(e.getStartMin());
					}
					else { return Integer.parseInt(this.getStartHour())-Integer.parseInt(e.getStartHour());  }
				}
				else { return Integer.parseInt(this.getDate())-Integer.parseInt(e.getDate()); }
			}
			else { return Integer.parseInt(this.getMonth())-Integer.parseInt(e.getMonth());  }
		}
		else { return Integer.parseInt(this.getYear())-Integer.parseInt(e.getYear()); }
	}
}
