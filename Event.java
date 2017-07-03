package simulation;

import java.util.List;

public class Event {

	/**
	 * @param args
	 */
	double time;
	double holdTime;
	int id;
	int srcNode;
	int desNode;
	EventType eventType;
	List<List<Integer>> workPath;
	public enum EventType{ARRIVAL,END,NEW_ARRIVAL,DISABLE};
    public Event(EventType type,double time, int i) {
    	this.eventType = type;
    	this.time = time;
    	this.id = i;
    	srcNode = 0;
    	desNode = 0;
    }
    public Event(Event e) {
    	this.eventType = e.getEventType();
    	this.time = e.getTime();
    	this.id = e.getId();
    	this.srcNode = e.getSrc();
    	this.desNode = e.getDes();
    	this.holdTime = e.getHoldTime();
    }
    public void setResAndDest(int source,int dest){
    	this.srcNode = source;
    	this.desNode = dest;
    }
    public void setTime(double time){
    	this.time = time;
    }
    public void setHoldTime(double holdTime){
    	this.holdTime = holdTime;
    }
    public void setId(int id){
    	this.id = id;
    }
    public void setEventType(EventType type){
    	this.eventType = eventType;
    }
    public int getSrc(){
    	return srcNode;
    }
    public int getDes(){
    	return desNode;
    }
    public int getId(){
    	return id;
    }
	public double getTime() {
		// TODO 自动生成的方法存根
		return time;
	}
	public EventType getEventType(){
		return eventType;
	}
	public double getHoldTime(){
		return holdTime;
	}
}
