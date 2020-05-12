
public class Customer {
	private int id;
	private int time;
	private int waiting;
	private int pplInFront;
	private int departureTime;

	public Customer(int id, int time) {
		this.id = id;
		this.time = time;
	}
	
	/*
	 * Getters and setters
	 */
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getWaiting() {
		return waiting;
	}
	public void setWaiting(int waiting) {
		this.waiting = waiting;
	}
	public int getPplInFront() {
		return pplInFront;
	}
	public void setPplInFront(int pplInFront) {
		this.pplInFront = pplInFront;
	}
	public int getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(int departureTime) {
		this.departureTime = departureTime;
	}
}
