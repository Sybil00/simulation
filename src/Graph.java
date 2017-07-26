/**
 * Created by zhangjing60 on 17/6/28.
 */

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static jdk.nashorn.internal.objects.Global.print;

public class Graph {

    /**
     * @param args
     */

    //index of vertex
    public Map<Integer, Vertex> vertexIdList = new HashMap<Integer, Vertex>();
    public Map<Point,Link> linkMap = new HashMap<>();

    public Map<Integer, Vertex> getVertexIdList() {
        return vertexIdList;
    }

    public void setVertexIdList(Map<Integer, Vertex> vertexIdList) {
        this.vertexIdList = vertexIdList;
    }

    public Map<Point, Link> getLinkMap() {
        return linkMap;
    }

    public void setLinkMap(Map<Point, Link> linkMap) {
        this.linkMap = linkMap;
    }

    public List<Vertex> getVertexList() {
        return vertexList;
    }

    public void setVertexList(List<Vertex> vertexList) {
        this.vertexList = vertexList;
    }

    public List<Link> getLinkList() {
        return linkList;
    }

    public void setLinkList(List<Link> linkList) {
        this.linkList = linkList;
    }

    public static int getMaxNodeNum() {
        return MaxNodeNum;
    }

//    public double[][] getWeight() {
//        return weight;
//    }
//
//    public void setWeight(double[][] weight) {
//        this.weight = weight;
//    }
//
//    public double[][] getUpdateWeight() {
//        return updateWeight;
//    }
//
//    public void setUpdateWeight(double[][] updateWeight) {
//        this.updateWeight = updateWeight;
//    }

    //vertexList of vertexes of topo
    private List<Vertex> vertexList = new ArrayList<Vertex>();
    //vertexList of edges of topo
    public List<Link> linkList = new ArrayList<>();

    static final int MaxNodeNum = 20;
//    double[][] weight = new double[MaxNodeNum][MaxNodeNum];
//    double[][] updateWeight = new double[MaxNodeNum][MaxNodeNum];
    public Graph(Graph graph) {
        this.vertexList = new ArrayList<>(graph.getVertexList());
        this.linkList = new ArrayList<>(graph.getLinkList());
        this.vertexIdList = new HashMap<>(graph.getVertexIdList());
        this.linkMap = new HashMap<>(graph.getLinkMap());
    }

    public double MobileRange(double[] needHelp, double[] mobileNode, double[] neighbor, int range) {
        double x1 = needHelp[0];
        double y1 = needHelp[1];
        double x2 = mobileNode[0];
        double y2 = mobileNode[1];

        double x3 = neighbor[0];
        double y3 = neighbor[1];

        double yk = ((x3 - x2) * (x1 - x2) * (y1 - y2) + Math.pow(y3 * (y1 - y2), 2) + Math.pow(y2 * (x1 - x2), 2)) / (Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
        double xk = ((x1 - x2) * x2 * (y1 - y2) + (x1 - x2) * (x1 - x2) * (yk - y2)) / ((x1 - x2) * (y1 - y2));

        if (x1 == x2) {
            xk = x1;
        }

        if (y1 == y2)
            xk = x3;

        double[] point_k = {xk, yk};// 垂足坐标

        double d1 = Math.sqrt(Math.pow((xk - x1), 2) + Math.pow((yk - y1), 2));
        double d2 = Math.sqrt(Math.pow((xk - x2), 2) + Math.pow((yk - y3), 2));
        double d12 = Math.min(d1, d2);// 垂足与直线上两点中较近点的距离
        double d3 = Math.sqrt(Math.pow((xk - x3), 2) + Math.pow((yk - y3), 2));// 垂线距离

        double dis = Math.sqrt(Math.pow(range, 2) - Math.pow(d3, 2)) - d12;

        return dis;
    }

    public double angle(double[] a, double[] b) {
        double ab = a[0] * b[0] + a[1] * b[1];
        double lenASquare = Math.pow(a[0], 2) + Math.pow(a[1], 2);
        double lenBSquare = Math.pow(b[0], 2) + Math.pow(b[1], 2);
        double lenA = Math.sqrt(lenASquare);
        double lenB = Math.sqrt(lenBSquare);
        double angle = Math.acos(ab / (lenA * lenB)) * 180 / Math.PI;
        return angle;

    }

   
    public Graph() {
        // TODO Auto-generated constructor stub
    }

    public Graph(String s1,String s2) {
        // TODO Auto-generated constructor stub
        //import_topo_from_file(data_filename);
        readPosition(s1);
        readTopology(s2);
        initWeightedTopo();
    }


//    public void import_topo_from_file(final String data_filename) {
//
//        try {
//            FileReader input = new FileReader(data_filename);
//            BufferedReader bufRead = new BufferedReader(input);
//
//            boolean is_first_line = true;
//            String line;
//
//            line = bufRead.readLine();
//            while (line != null) {
//
//                if (line.trim().equals("")) {
//
//                    line = bufRead.readLine();
//                    continue;
//                }
//
//                if (is_first_line) {
//
//                    is_first_line = false;
//                    int vertex_num = Integer.parseInt(line.trim());
//                    //System.out.println(vertex_num);
//                    for (int i = 1; i <= vertex_num; i++) {
//                        Vertex vertex = new Vertex();
//                        vertexList_vertex.add(vertex);
//                        vertexIdList.put(vertex.id, vertex);
//                        //System.out.println(vertexList_vertex.get(i-1).id);
//                        if (i == vertex_num) Vertex.CURRENT_VERTEX_NUM = 1;
//                    }
//                } else {
//                    String[] str_vertexList = line.trim().split("\\s");
//                    Edge edge = new Edge();
//                    edge.source = Integer.parseInt(str_vertexList[0]);
//                    edge.target = Integer.parseInt(str_vertexList[1]);
//                    edge.weight = Double.parseDouble(str_vertexList[2]);
//                    linkvertexList.add(edge);
//                    //System.out.println(edge.source + " " + edge.target + " " + edge.weight);
//                    for (Vertex vertex : vertexList_vertex) {
//                        if (vertex.id == edge.source) {
//                            vertex.adjacencies.add(edge);
//                        }
//                    }
//                }
//                line = bufRead.readLine();
//            }
//            bufRead.close();
//
//        } catch (IOException e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }
//    }

    public  void readPosition(String s){
        try {
            FileReader input = new FileReader(s);
            BufferedReader bufRead = new BufferedReader(input);
            String line;

            line = bufRead.readLine();
            int i = 0;
            while (line != null) {

                String[] strList = line.trim().split("\\s+");
                double x = Double.parseDouble(strList[0]);
                double y = Double.parseDouble(strList[1]);
                Vertex vertex = new Vertex(x,y,i);
                vertexIdList.put(i,vertex);
                vertexList.add(vertex);
                line = bufRead.readLine();
                i++;
            }
            bufRead.close();

        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    public  void readTopology(String s) {
        try {
            FileReader input = new FileReader(s);
            BufferedReader bufRead = new BufferedReader(input);
            String line;
            line =  bufRead.readLine();
            int j = 0;
            while (line != null) {
                String[] str = line.trim().split("\\s+");
                for(int i = 0; i < MaxNodeNum;i++) {
                    if(Integer.parseInt(str[i]) == 1 && i!=j){
                        Link link = new Link(j,i);
                        Point p = new Point (j,i);
                        linkMap.put(p,link);
                        //初始化邻接矩阵

                        Vertex vertex = vertexList.get(j);
                        vertex.adjacencies.add(link);
//                        for(Vertex vertex : vertexList){
//                            if(vertex.getId() == j){
//                                vertex.adjacencies.add(link);
//                                break;
//                            }
//                        }
                    }
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
    public void initWeightedTopo() {
        for(int i = 0; i < MaxNodeNum; i++){
            for(int j = 0; j < MaxNodeNum; j++) {
                if(i !=j ) {
                    Point p = new Point(i,j);
                    Link l = linkMap.get(p);
                    if( l != null){
                        Vertex src = vertexList.get(i);
                        Vertex des = vertexList.get(j);
                        double weight = Math.sqrt(Math.pow(src.getX() -des.getX() ,2)+ Math.pow(src.getY()-des.getY(),2));
                        l.setWeight(weight);
                        linkMap.put(p,l);
                    }

                }
            }
        }
    }
    
}



