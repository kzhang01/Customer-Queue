import java.io.IOException;
import java.util.ArrayList;

/*
 * print to console and write answer in output file
 */
public class Main {
	static int T;
	final static int OPENING = 9*3600;
	final static int CLOSING = 17*3600;
	static Queue<Customer> line = new Queue<Customer>();
	
	public static void main(String[] args) throws IOException{
		Load.customersFile(args[0]);
		Load.queriesFile(args[1]);
	}
	public static int findWaitingTime(Customer a) {
		/*
		 * Iterates until we reach the customer whose waiting time we're calculating
		 */
		while(line.position.data != a) {
			line.next();
		}
		/*
		 * Handles case where customer is first in line and arrives before opening
		 */
		if(!line.hasPrev() && line.position.data.getTime() < 9*60*60 && line.position.data.getTime() > 5*60*60) {
			return OPENING - line.position.data.getTime();
		}
		/*
		 * If no one is in front, waiting time is zero
		 */
		if(!line.hasPrev() || line.position.prev.data.getDepartureTime() < line.position.data.getTime())
			return 0;
		/*
		 * If none of the previous cases apply, the following statement is returned
		 * Handles customers who are not first in line
		 */
		return line.position.prev.data.getDepartureTime() - line.position.data.getTime();
		
	}

	public static int numServed(Queue<Customer> a) {  
		int count = 0;
		while(Main.line.position.prev != null) {
			Main.line.position = Main.line.position.prev;
		}
		while(line.hasNext()) {
			if(line.position.data.getDepartureTime() < CLOSING + T) 
				count++;
			line.next();
		}
		return count;
	}
	public static ArrayList<Integer> findBreaks(Queue<Customer> a) {
		ArrayList<Integer> breaks = new ArrayList<Integer>();
		resetPosition();
		while(line.position.next != null) {
			if(line.position.data.getDepartureTime() < line.position.next.data.getTime())
				breaks.add(line.position.next.data.getTime() - line.position.data.getDepartureTime());
			line.position = line.position.next;
		}
		return breaks;
	}
	public static int longestBreak(Queue<Customer> a) {
		int longest = 0;
		for(int breaks : findBreaks(line)) {
			if(breaks > longest)
				longest = breaks;
		}
		return longest;
	}
	
	public static int totalIdle(Queue<Customer> a) {
		int totalIdle = 0;
		for(int breaks : findBreaks(line)) {
			totalIdle = breaks + totalIdle;
		}
		return totalIdle;
	}
	
	public static int maxPplInLine(Queue<Customer> a) {
		int max = 0;
		ArrayList<Integer> people = new ArrayList<Integer>();

		resetPosition();
		while(line.position.next != null) {
			if(line.position.data.getDepartureTime() < line.position.next.data.getTime())
				++max;
			else {
				people.add(max);
				max = 0;
			}
			line.position = line.position.next;
		}
		for(int peopleInLine: people) {
			if (peopleInLine > max)
				max = peopleInLine;
		}
		return max;
	}
	

	/*
	 * Converts the HH:MM:SS string provided in the customersfile into seconds stored as int
	 */
	static int convertToSeconds(String s) {
	    String[] hmsFormat = s.split(":");
	    int hour = Integer.parseInt(hmsFormat[0]);
	    int mins = Integer.parseInt(hmsFormat[1]);
	    int seconds = Integer.parseInt(hmsFormat[2]);
	    /*
	     * Converts time into 24 hour format
	     */
	    if(hour < 8)
	    	hour = hour + 12;
	    int hoursInSeconds = hour * 60 * 60;
	    int minsInSeconds = mins * 60;
	    return hoursInSeconds + minsInSeconds + seconds;
	}
	
	static String convertToFormatted(int time) {
		
		int hour = time / 3600;
		if(hour > 12)
			hour = hour - 12;
		int mins = (time % 3600) / 60;
		String mins1 = "" + mins;
		if(mins < 10) {
			mins1 = "0" + mins;
		}
		int seconds = ((time % 3600) % 60) % 60;
		String sec = "" + seconds;
		if(seconds < 10) {
			sec = "0" + seconds;
		}
		return hour + ":" + mins1 + ":" + sec;
	}
	static void resetPosition() {
		while(Main.line.position.prev != null) {
			Main.line.position = Main.line.position.prev;
		}
	}
}
