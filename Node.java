package simulation;

public class Node {

	/**
	 * @param args
	 */
	 public int nodeId;
	 public double x;
	 public double y;
	 public double utilization;
	 public int degree ;
	 public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}

	public Node(double x,double y) {
		 this.x = x;
		 this.y = y;
	 }
	public Node(double x,double y,int id){
		this.x = x;
		this.y = y;
		this.nodeId = id;
	}
	public Node(int id){
		this.nodeId = id;
	}
	 
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getUtilization() {
		return utilization;
	}
	public void setUtilization(double utilization) {
		this.utilization = utilization;
	}
	 

}
