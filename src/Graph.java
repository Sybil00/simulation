/**
 * Created by zhangjing60 on 17/6/28.
 */

import java.awt.*;
import java.util.ArrayList;
        import java.util.List;

public class Graph {

    /**
     * @param args
     */
    static final int MaxNodeNum = 20;
    double[][] weight = new double[MaxNodeNum][MaxNodeNum];
    double[][] updateWeight = new double[MaxNodeNum][MaxNodeNum];
    public int ksp( int src, int dest, int k , List<List<Integer>> list){
        int res = 0;
        return res;
    }

    public  double MobileRange(double[] needHelp, double[] mobileNode, double[] neighbor, int range) {
        double x1 = needHelp[0];
        double y1 = needHelp[1];
        double x2 = mobileNode[0];
        double y2 = mobileNode[1];

        double x3 = neighbor[0];
        double y3 = neighbor[1];

        double yk = ((x3-x2)*(x1-x2)*(y1-y2) + Math.pow(y3*(y1-y2),2) + Math.pow(y2*(x1-x2),2)) / (Math.pow((x1-x2),2)+Math.pow((y1-y2),2));
        double xk = ((x1-x2)*x2*(y1-y2) + (x1-x2)*(x1-x2)*(yk-y2)) / ((x1-x2)*(y1-y2));

        if (x1 == x2){
            xk = x1;
        }

        if (y1 == y2)
            xk = x3;

        double[] point_k = {xk,yk};// 垂足坐标

        double d1 = Math.sqrt(Math.pow((xk - x1),2) + Math.pow((yk - y1),2));
        double d2 = Math.sqrt(Math.pow((xk - x2),2) + Math.pow((yk - y3),2));
        double d12 = Math.min(d1, d2);// 垂足与直线上两点中较近点的距离
        double d3= Math.sqrt(Math.pow((xk - x3),2) + Math.pow((yk - y3),2));// 垂线距离

        double dis = Math.sqrt(Math.pow(range,2) -Math.pow (d3,2)) - d12;

        return dis;
    }
    public  double angle(double[] a, double[] b) {
        double ab = a[0]*b[0] + a[1]*b[1];
        double lenASquare = Math.pow(a[0],2) + Math.pow(a[1],2);
        double lenBSquare = Math.pow(b[0],2) + Math.pow(b[1],2);
        double lenA = Math.sqrt(lenASquare);
        double lenB = Math.sqrt(lenBSquare);
        double angle = Math.acos(ab/(lenA*lenB))*180/Math.PI;
        return angle;

    }



}