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
    static final int ksp = 3;
    static int blockIgnore = 10000;
    static int wlNum = 10;
    //最短路部分
    Map<Map<Integer,Integer>,List<Integer>> SP_path;
    //K最短路部分
    List<List<Integer>> multiPath ;
    Map<Point,List<List<Integer>>> KSP_path;
    int[][] linkUtilization = new int[MAXIMUM][MAXIMUM];

    public List<Node> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<Node> nodeList) {
        this.nodeList = nodeList;
    }

    private List<Node> nodeList =new ArrayList<Node>();
    Graph graph ;

    Map<Point,Link> indexLinkMap ;
    int[][] adjacency;
    static double[][] weightedTopo;//存储链路可靠性




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
    public void calculateKPath(){

    }
    public void calculateKPath(int src,int des,List<List<Integer>> multipath){

    }
    public int getServiceQuantity(){
        return ServiceQuantity;
    }
    public int getMaximum(){
        return MAXIMUM;
    }

    //从文件读取节点坐标，初始化节点
    public void initPosition(String s){
        List<Double> list = readFileIntoList(s);
        for(int i = 0; i < MAXIMUM; i++) {
            double x = list.get(i);
            double y = list.get(i+MAXIMUM);
            Node node = new Node(x,y,i);
            nodeList.add(node);
        }

    }
    //从文件读取邻接矩阵
    public void readTopology(String s){
        List<Double> list = readFileIntoList(s);
        for (int i = 0; i < MAXIMUM; i++) {
            for (int j = 0; j < MAXIMUM;j++){
               double k = list.get(j + i*MAXIMUM);
                adjacency[i][j] = (int)k;
                if(k != 0) {
                    Point p = new Point(i,j);
                    indexLinkMap.put(p, new Link(i,j));
                }
            }
        }
    }
    public void initWeightedTopology(){
        for(int i =0 ;i < MAXIMUM; i++) {
            for(int j = 0 ; j< MAXIMUM; j++) {
            }
        }
    }

    public void initializeDB() {
        // TODO 自动生成的方法存根
        readTopology("net.data");
        initPosition("position.data");
        calculateKPath();
    }
    public void calculateKpath(){
        graph = new Graph();
        for(int i = 0;i < MAXIMUM; i ++ ){
            for(int j = 0; j < MAXIMUM; j++) {
                if(i==j)
                    continue;
                else{
                    graph.ksp(i,j,ksp,multiPath);
                    KSP_path.put(new Point(i,j),multiPath);
                }
            }
        }
    }
    public  List<Double> readFileIntoList(String fileName) {
        File filename = new File(fileName);
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(new FileInputStream(filename));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(reader);
        String line = "";
        ArrayList<Double> a = new ArrayList<>();
        try {
            while ((line = br.readLine()) != null) {
                Scanner sca = new Scanner(line.trim());
                while (sca.hasNextDouble()) {
                    a.add(sca.nextDouble());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return a;
    }
    public void calculateKpath(int src,int des, List<List<Integer>> multiPath){

    }


}

