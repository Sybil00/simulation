import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Vector;

public class YenTopKShortestPath {

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	public List<Path> getResult_list() {
		return result_list;
	}

	public void setResult_list(List<Path> result_list) {
		this.result_list = result_list;
	}

	public Map<Path, Vertex> getPath_derivation_vertex_index() {
		return path_derivation_vertex_index;
	}

	public void setPath_derivation_vertex_index(Map<Path, Vertex> path_derivation_vertex_index) {
		this.path_derivation_vertex_index = path_derivation_vertex_index;
	}

	public PriorityQueue<Path> getPath_candidates() {
		return path_candidates;
	}

	public void setPath_candidates(PriorityQueue<Path> path_candidates) {
		this.path_candidates = path_candidates;
	}

	public Vertex getSrc() {
		return src;
	}

	public void setSrc(Vertex src) {
		this.src = src;
	}

	public Vertex getDes() {
		return des;
	}

	public void setDes(Vertex des) {
		this.des = des;
	}

	public int getSrcId() {
		return srcId;
	}

	public void setSrcId(int srcId) {
		this.srcId = srcId;
	}

	public int getDesId() {
		return desId;
	}

	public void setDesId(int desId) {
		this.desId = desId;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	private Graph graph;

	// intermediate variables
	private List<Path> result_list = new Vector<Path>();
	private Map<Path, Vertex> path_derivation_vertex_index = new HashMap<Path, Vertex>();
	private PriorityQueue<Path> path_candidates = new PriorityQueue<Path>();
	
	// the ending vertices of the paths
	private Vertex src = null;
	private Vertex des = null;
	
	private int srcId;
	private int desId;
	// variables for debugging and testing
	private String file_name; 

	public YenTopKShortestPath(Graph graph, int src, int des) {
		this.graph = graph;
		this.srcId = src;
		this.desId = des;
		init();
	}



	private void init()
	{
		//Graph graph = new Graph(file_name);
		src = graph.getVertexIdList().get(srcId);
		des = graph.getVertexIdList().get(desId);
		// get the shortest path by default if both source and target exist
		List<Vertex> vertexList = new ArrayList<>();
			for(Vertex vertex: graph.getVertexList()){
				vertexList.add(vertex);
			}
		if(src != null && des != null)
		{
			Path shortest_path = get_shortest_path(src, des, vertexList);
			if(!shortest_path.get_Vertices().isEmpty())
			{
				path_candidates.add(shortest_path);
				path_derivation_vertex_index.put(shortest_path, src);
			}
		}
	}
	
	public Path get_shortest_path(Vertex source_vt, Vertex target_vt, List<Vertex> all_node)
	{
		Dijkstra.computePath(source_vt, all_node);
		return Dijkstra.getShortestPathTo(target_vt);
	}
	
	public Path next() {
		Path cur_path = path_candidates.poll();
		path_candidates.remove(cur_path);
		result_list.add(cur_path);
		
		int count = result_list.size();
		boolean cycle_break;
		
		int path_length = cur_path.get_Vertices().size();
		for(int i=0; i<path_length - 1; i++) {
			
			Graph graph_tmp = new Graph(graph);
			int cur_path_derivation_id = cur_path.get_Vertices().get(i).id;//cur derivation node in the cur path
			int delete_edge=0; //the number of delete edge
			for(Link e:graph_tmp.getVertexIdList().get(cur_path_derivation_id).adjacencies) {
				delete_edge++;
				if(e.des == cur_path.get_Vertices().get(i + 1).id) {
					break;
				}
			}
			graph_tmp.getVertexList().get(cur_path_derivation_id).adjacencies.remove(delete_edge - 1);
			
			Path sPath = get_shortest_path(graph_tmp.getVertexList().get(cur_path_derivation_id), graph_tmp.getVertexList().get(des.id), graph_tmp.getVertexList());
			
			Path rPath = cur_path.subPath(1, i);
			sPath.convPath(rPath);
			boolean duplication = true;
			for(int j=0;j<result_list.size();j++) {
				
				int num=0;
				for(int k=0; k<sPath.get_Vertices().size(); k++) {
					if(sPath.get_Vertices().get(k).id == result_list.get(j).get_Vertices().get(k).id) {
						num++;
					}else {
						break;
					}
						
				}
				if(num == sPath.get_Vertices().size())
					duplication = false;
			}
			if(duplication==true)
				path_candidates.add(sPath);

		}
		return cur_path;
	}
	
	public List<Path> getKShortestPath(int k) {
		int count = 1;
		while(!path_candidates.isEmpty() && count<k) {
			next();
			count++;
		}
		return result_list;
	}
	public void printResultPath() {
		int i=1;
		for(Path path:result_list) {
			System.out.println("-----------------");
			System.out.print("the " + i++ + " shortest path");
			path.printPath();
		}
		
	}
}
