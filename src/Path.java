import java.util.ArrayList;
import java.util.List;

import com.sun.scenario.effect.impl.prism.ps.PPSColorAdjustPeer;

public class Path implements ElementWithWeight, Comparable<Path>{

	private List<Vertex> vertex_list = new ArrayList<Vertex>();
	public double weight = -1;
	public Path() {
		// TODO Auto-generated constructor stub
	}
	
	public Path(List<Vertex> vertex_list, double weight) {
		// TODO Auto-generated constructor stub
		this.vertex_list = vertex_list;
		this.weight = weight;
	}
	public double get_weight() {
		return weight;
	}
	
	public List<Vertex> get_Vertices() {
		return vertex_list;
	}	
	
	public Path convPath(Path path) {
		for(int i=0;i<path.get_Vertices().size();i++) {
			if((path.get_Vertices().size()-i)>1) {
				for(Link edge:path.get_Vertices().get(i).adjacencies) {
					if(edge.des == path.get_Vertices().get(i+1).id) {
						weight = weight + edge.weight;
					}
				}
			}else {
				for(Link edge:path.get_Vertices().get(i).adjacencies) {
					if(edge.des == vertex_list.get(0).id) {
						weight = weight + edge.weight;
					}
				}
			}
		}
		vertex_list.addAll(0,path.get_Vertices());
		return this;
	}
	
	public Path subPath (int src, int dst) {
		
		if(src > dst) {
			return new Path();
		}else {
			Path subpath = new Path();
			subpath.vertex_list = this.vertex_list.subList(src-1, dst);
			return subpath;
		}
		
	}
	
	public void printPath() {
		
		List<Integer> vertex_id = new ArrayList<Integer>();
		for(Vertex vertex : vertex_list) {
			vertex_id.add(new Integer(vertex.id));
		}
		System.out.println(vertex_id+" the weight is :"+weight);
	}
	
	@Override
	public int compareTo(Path path) {
		// TODO Auto-generated method stub
		double diff = this.weight - path.weight;
		if(diff < 0)
			return -1;
		else if(diff > 0)
			return 1;
		else 
			return 0;
	}
	
}
