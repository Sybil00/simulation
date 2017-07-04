/**
 * Created by zhangjing60 on 17/6/28.
 */
import  java.util.BitSet;
public interface Simulation {
    static int blockServiceNum = 0;

    public void initialize(double lamda);
    public void run();
    public void dealWithEvent(Event event);
    public void caculatePath(Event event);
    public void generateServicePair(int id);
    public int frand(int ia, int ib);
    public void randomSrcDes(int src,int des);
    public double arriveTimeGen(double beta);//随机时间生成器
    public int countLink();//统计链路的资源信息
    public BitSet reserveSource(Event e);
}
