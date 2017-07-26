/**
 * Created by zhangjing60 on 17/6/28.
 */

import java.util.List;

public class Event {

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getHoldTime() {
        return holdTime;
    }

    public void setHoldTime(double holdTime) {
        this.holdTime = holdTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSrcNode() {
        return srcNode;
    }

    public void setSrcNode(int srcNode) {
        this.srcNode = srcNode;
    }

    public int getDesNode() {
        return desNode;
    }

    public void setDesNode(int desNode) {
        this.desNode = desNode;
    }



    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    /**
     * @param args
     */
    double time;
    double holdTime;
    int id;
    int srcNode;
    int desNode;
    EventType eventType;

    public int getWorkWavalength() {
        return workWavalength;
    }

    public void setWorkWavalength(int workWavalength) {
        this.workWavalength = workWavalength;
    }

    int workWavalength;

    public List<Path> getMultiPath() {
        return multiPath;
    }

    public void setMultiPath(List<Path> multiPath) {
        this.multiPath = multiPath;
    }

    public Path getWorkPath() {
        return workPath;
    }

    public void setWorkPath(Path workPath) {
        this.workPath = workPath;
    }

    List<Path> multiPath;
    Path workPath;
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
        this.srcNode = e.getSrcNode();
        this.desNode = e.getDesNode();
        this.holdTime = e.getHoldTime();
    }
    public void setResAndDest(int source,int dest){
        this.srcNode = source;
        this.desNode = dest;
    }

}
