/**
 * Created by zhangjing60 on 17/6/28.
 */
import java.util.ArrayList;
import  java.util.BitSet;
import java.util.List;
import java.util.Map;

public interface Simulation {
    double helpStart = 0.8;//阈值,资源利用率超过这个值就需要邻节点进行帮助
    double helpAvailable = 0.5;//节点的资源利用率低于该值时,认为可以实施帮助
    int rCommunicate = 1400;
    int rAcess = 800;
    void initialize(double lamda);
    void run();
    void dealWithEvent(Event event);
    void caculatePath(Event event);
    void generateServicePair(int id);
    int frand(int ia, int ib);
    void randomSrcDes(int src,int des);
    double arriveTimeGen(double beta);//随机时间生成器
    int countLink();//统计链路的资源信息
    boolean reserveSource(Event e);
    double countLoad(int i);//计算节点i的资源占用比例,小于阈值返回false,大于等于阈值返回true;
    List<Integer>  findNeighbor(int i) ;
    int calculateNW(List<Integer> neighbor);
    void reliefResource(Event e);

    //help方法;

}
