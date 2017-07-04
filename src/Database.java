/**
 * Created by zhangjing60 on 17/6/28.
 */
import java.awt.Point;
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
    List<Node> list =new ArrayList<Node>();
    Graph graph ;

    Map<Point,Link> indexLinkMap ;





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
    public int[][] adjacency;
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

    //读取邻接矩阵
    public void readTopology(int i, String s){

    }
    /*读取节点坐标
    public void initPosition(int[] a,int[] b){
        for(int i = 0; i < a.length; i++){
            Node node = new Node(a[i],b[i],i);
            list.add(node);
        }
    }*/
    //从文件读取节点坐标，初始化节点
    public void initPosition(String s){

    }
    public void readTopology(){

    }
    public void initializeDB() {
        // TODO 自动生成的方法存根
        readTopology(MAXIMUM,"net.data");
        initPosition("position.data");
    }
    public void calculateKpath(){
        graph = new Graph();
        for(int i = 0;i < MAXIMUM; i ++ ){
            for(int j = 0; j < MAXIMUM; j++) {
                if(i==j)
                    continue;
                else{
                    graph.ksp(i,j,multiPath);
                    KSP_path.put(new Point(i,j),multiPath);


                }
            }
        }
    }
    public void calculateKpath(int src,int des, List<List<Integer>> multiPath){

    }

}

