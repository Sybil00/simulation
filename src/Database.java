/**
 * Created by zhangjing60 on 17/6/28.
 */
import java.awt.Point;
import java.io.*;
import java.util.*;
public class Database {

    /**
     * @param args
     */
    static final int MAXIMUM = 20;
    static final int ServiceQuantity = 100000;
    static final int k = 1;
    static int blockIgnore = 10000;
    static int wlNum = 10;
    //最短路部分
    Map<Point,List<Integer>> SP_path;
    //K最短路部分
    List<List<Integer>> multiPath ;
    Map<Point,List<Path>> KSP_path;
    int[][] linkUtilization = new int[MAXIMUM][MAXIMUM];

    public List<Node> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<Node> nodeList) {
        this.nodeList = nodeList;
    }

    private List<Node> nodeList =new ArrayList<Node>();


    Map<Point,Link> indexLinkMap ;
    int[][] adjacency = new int[MAXIMUM][MAXIMUM];
    static double[][] weightedTopo;//存储链路可靠性
    Graph graph = null;



    public Map<Point, Link> getIndexLinkMap() {
        return indexLinkMap;
    }

    public void setIndexLinkMap(Map<Point, Link> indexLinkMap) {
        this.indexLinkMap = indexLinkMap;
    }

    public static int getBlockIgnore() {
        return blockIgnore;
    }
    public static void setBlockIgnore(int blockIgnore) {
        Database.blockIgnore = blockIgnore;
    }
    public int[][] getLinkUtilization() {
        return linkUtilization;
    }
    public void setLinkUtilization(int[][] linkUtilization) {
        this.linkUtilization = linkUtilization;
    }
    public int[][] getAdjacency() {
        return adjacency;
    }
    public void setAdjacency(int[][] adjacency) {
        this.adjacency = adjacency;
    }
    public void calculateKPath(int src,int des,List<List<Integer>> multipath){

    }
    public int getServiceQuantity(){
        return ServiceQuantity;
    }
    public int getMaximum(){
        return MAXIMUM;
    }
    public void initTopology(){
        String s1 = "data/position.data";
        String s2 = "data/topology.data";
        initAdjancency(s2);
        this.graph = new Graph(s1,s2);
        this.indexLinkMap = this.graph.linkMap;

    }

    public void initializeDB(){
        initTopology();
        calculateKpath();
    }

    public void calculateKpath() {
        for(int i = 0; i < MAXIMUM; i++) {
            for(int j = 0; j < MAXIMUM; j++) {
                if(i!=j){
                    YenTopKShortestPath ksp = new YenTopKShortestPath(this.graph,i,j);
                    List<Path> list = ksp.getKShortestPath(k);
                    KSP_path.put(new Point(i,j),list);
                }
            }
        }
    }
    public void initAdjancency(String s) {
        try {
            FileReader input = new FileReader(s);
            BufferedReader bufRead = new BufferedReader(input);
            String line;
            line =  bufRead.readLine();
            int j = 0;
            while (line != null) {
                String[] strList = line.trim().split("\\s+");
                for(int i = 0; i < MAXIMUM;i++) {
                    adjacency[j][i] = Integer.parseInt(strList[i]);
                }
                j++;
                line = bufRead.readLine();
            }
            bufRead.close();

        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

//    //从文件读取节点坐标，初始化节点
//    public void initPosition(String s){
//        List<Double> list = readFileIntoList(s);
//        for(int i = 0; i < MAXIMUM; i++) {
//            double x = list.get(i);
//            double y = list.get(i+MAXIMUM);
//            Node node = new Node(x,y,i);
//            nodeList.add(node);
//        }
//
//    }
//    //从文件读取邻接矩阵
//    public void readTopology(String s){
//        List<Double> list = readFileIntoList(s);
//        for (int i = 0; i < MAXIMUM; i++) {
//            for (int j = 0; j < MAXIMUM;j++){
//               double k = list.get(j + i*MAXIMUM);
//                adjacency[i][j] = (int)k;
//                if(k != 0) {
//                    Point p = new Point(i,j);
//                    indexLinkMap.put(p, new Link(i,j));
//                }
//            }
//        }
//    }
//    public void initWeightedTopology(){
//        for(int i =0 ;i < MAXIMUM; i++) {
//            for(int j = 0 ; j< MAXIMUM; j++) {
//            }
//        }
//    }
//
//    public void initializeDB() {
//        // TODO 自动生成的方法存根
//        readTopology("net.data");
//        initPosition("position.data");
//        calculateKPath();
//    }
//    public void calculateKpath(){
//        graph = new Graph();
//        for(int i = 0;i < MAXIMUM; i ++ ){
//            for(int j = 0; j < MAXIMUM; j++) {
//                if(i==j)
//                    continue;
//                else{
//                    graph.ksp(i,j,ksp,multiPath);
//                    KSP_path.put(new Point(i,j),multiPath);
//                }
//            }
//        }
//    }
//    public  List<Double> readFileIntoList(String fileName) {
//        File filename = new File(fileName);
//        InputStreamReader reader = null;
//        try {
//            reader = new InputStreamReader(new FileInputStream(filename));
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        BufferedReader br = new BufferedReader(reader);
//        String line = "";
//        ArrayList<Double> a = new ArrayList<>();
//        try {
//            while ((line = br.readLine()) != null) {
//                Scanner sca = new Scanner(line.trim());
//                while (sca.hasNextDouble()) {
//                    a.add(sca.nextDouble());
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            br.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return a;
//    }
    public List<Path> calculateKpath(int src,int des){
        YenTopKShortestPath ksp = new YenTopKShortestPath(this.graph,src,des);
        List<Path> list = ksp.getKShortestPath(k);
        return list;

    }


}

