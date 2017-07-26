import java.util.*;


public class Dijkstra {
	
	public static void computePath(Vertex source, List<Vertex> all_node){
		source.minDistance = 0;
		PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
		vertexQueue.add(source);
		if(!all_node.isEmpty()){
			all_node.remove(source);
		}
		while(!all_node.isEmpty()){
			Vertex s = vertexQueue.poll();
			List<Link> adjacencies = s.adjacencies;
			for(Link e : adjacencies) {
				for(Vertex node : all_node) {
					if (node.id == e.des) {
						double distanceThroughs = s.minDistance + e.weight;
						// Vertex curPrevious = node.getPrevious();
						if(distanceThroughs < node.minDistance) {
							node.minDistance = distanceThroughs;
							node.previous = s;
						}
					}
				}
			}
			
			double tmp_distance = Double.MAX_VALUE;
			Vertex tmp_node = null;
			for (Vertex node : all_node) {
				if (node.minDistance < tmp_distance) {
					tmp_distance = node.minDistance;
					tmp_node = node;
				}
			}
			vertexQueue.add(tmp_node);
			all_node.remove(tmp_node);
		}
	}
	
	
	public static Path getShortestPathTo (Vertex target){
		
		List<Vertex> resultPath = new ArrayList<Vertex>();
		List<Integer> result_id = new ArrayList<Integer>();
		Path path;
		Double weight;
		
		for (Vertex vertex = target; vertex!=null; vertex = vertex.previous) {
			resultPath.add(vertex);
			result_id.add(vertex.id);
		}
		Collections.reverse(resultPath);
		Collections.reverse(result_id);
		
		weight = target.minDistance;
		path = new Path(resultPath, weight);
		return path;
	}
	
}
