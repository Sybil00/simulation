/**
 * Created by zhangjing60 on 17/6/28.
 */

import java.awt.Point;
import java.awt.geom.Arc2D;
import java.util.*;

import  Event.EventType;

public class SimulationImpl implements Simulation {

    /**
     * @param args
     */

    double startTime;
    double currentTime;
    double holdTime;
    double lamda;
    double mu;
    int serviceId;
    Database database = new Database();
    int MAXIMUM = database.getMaximum();
    int serviceQuantity = database.getServiceQuantity();
    int blockIgnore = database.getBlockIgnore();
    Map<Integer,Event> serviceEventMap;
    int[][] linkUtilization = database.getLinkUtilization();
    int[][] adjacency = database.getAdjacency();
    double[][] weightedTopo = new double[MAXIMUM][MAXIMUM];
    List<Node> nodeList = database.getNodeList();
    int blockServiceNum =0;
    Queue<Event> eventQueue = new PriorityQueue<Event>(50,new Comparator<Event>(){

        @Override
        public int compare(Event arg0, Event arg1) {
            // TODO 自动生成的方法存根
            double t1 = arg0.getTime();
            double t2 = arg1.getTime();
            return t1 > t2 ? 1 : (t1 < t2 ? -1 : 0);
        }

    });

    @Override
    public void initialize(double lamda) {
        // TODO 自动生成的方法存根
        this.lamda = lamda;
        this.mu = 1;
        this.currentTime = 0;
        this.serviceId = 1;
        database.initializeDB();
    }

    @Override
    public void run() {
        // TODO 自动生成的方法存根
        int countNumber = 0;
        //随机播种C++中是srand(1);
        generateServicePair(serviceId);
        Database db = new Database();
        serviceQuantity = db.getServiceQuantity();
        MAXIMUM = db.getMaximum();
        blockIgnore =db.getBlockIgnore();

        while(serviceId <= serviceQuantity ) {
            {
                if(eventQueue.peek().getEventType() == EventType.ARRIVAL){
                    dealWithEvent(eventQueue.peek());
                    serviceId++;
                    generateServicePair(serviceId);
                }
                else
                    dealWithEvent(eventQueue.peek());
            }
        }
        while(!eventQueue.isEmpty()){
            dealWithEvent(eventQueue.peek());
        }
        double blockRate = countNumber/serviceQuantity;

    }
    //该部分是对于事件的处理，属于核心部分
    @Override
    public void dealWithEvent(Event event) {
        // TODO 自动生成的方法存根
        switch(event.getEventType()){
            case ARRIVAL:
            {
                Event event_t = new Event(event);
                int src = event_t.getSrcNode();
                List<Integer> neighbor = null;
                List<Double> distance = new ArrayList<>();
                if(countLoad(src) >= helpStart)
                    {
                        neighbor = findNeighbor(src);
                        int helpNeighbor = calculateNW(neighbor);
                        // 说明该节点可以进行帮助
                        if (countLoad(helpNeighbor) < helpAvailable) {
                           // 获取进行help的节点的邻节点
                            Node a = nodeList.get(src);
                            Node b = nodeList.get(helpNeighbor);
                            List<Integer> neighbor2Stage = findNeighbor(helpNeighbor);
                            for(int i : neighbor2Stage) {
                                // 先判断角度,确定三个点的关系
                                Node c = nodeList.get(i);
                                double range = generateVector(a,b,c);
                                    distance.add(range);
                            }
                            Collections.sort(distance);
                            // 选择移动最小的
                            double d = distance.get(0);
                            //更新节点的坐标
                            Node movedPosition = updatePosition(d,a,b);
                            //在list中替换节点
                            nodeList.set(helpNeighbor,movedPosition);
                            event_t.setSrcNode(helpNeighbor);
                            eventQueue.poll();
                            eventQueue.offer(event_t);
                        }
                        else {
                            blockServiceNum ++;
                            eventQueue.poll();
                        }
                    }
                else
                {
                    caculatePath(event_t);
                    boolean ret = reserveSource(event_t);
                    if(ret) {
                    serviceEventMap.put(event_t.getId(),event_t);
                    }
                    else {
                        blockServiceNum++;
                    }
                    eventQueue.poll();
                }

                break;
            }
            case END:
            {
                reliefResource(event);
                eventQueue.poll();
                serviceEventMap.remove(event.getId());
                break;
            }


        }
    }

    @Override
    public void caculatePath(Event event) {
        // TODO 自动生成的方法存根
        int src = event.getSrcNode();
        int des = event.getDesNode();
        Point p = new Point(src,des);
        event.setMultiPath(database.KSP_path.get(p));
    }

    @Override
    public void generateServicePair(int id) {
        // TODO 自动生成的方法存根
        double arriveTime = currentTime + arriveTimeGen(this.lamda);
        currentTime = arriveTime;
        double holdTime = arriveTimeGen(this.mu);
        int source = 0, dest = 0;
        randomSrcDes(source,dest);
        Event eventIn = new Event(EventType.ARRIVAL, arriveTime, id);
        eventIn.setId(id);
        eventIn.setHoldTime(holdTime);
        //这个方法有问题
        eventIn.setResAndDest(source, dest);
        Event eventOut = new Event(EventType.END,arriveTime,id);
        eventOut.setTime(arriveTime + holdTime);
        eventOut.setResAndDest(source,dest);
        eventQueue.add(eventIn);
        eventQueue.add(eventOut);


    }

    @Override
    public int frand(int ia, int ib) {
        // TODO 自动生成的方法存根

        int fr = (int)(Math.random()*(ib-ia+1)+ia);
        return fr;
    }

    @Override
    public void randomSrcDes(int src, int des) {
        // TODO 自动生成的方法存根
        //存在问题，改变的只是本地变量，没有改变本身
        src = frand(0,MAXIMUM - 1);
        des = frand(0,MAXIMUM - 1);
        while(src == des){
            des = frand(0,MAXIMUM - 1);
        }
    }

    @Override
    public double arriveTimeGen(double beta) {
        // TODO 自动生成的方法存根
        assert beta > 0;
        double u, x;
        do{
            u = Math.random();
        }
        while(u == 0 );
        assert(u != 0 && u != 1);
        x = -(1 / beta)*Math.log(u);
        return x;
    }

    @Override
    public int countLink() {
        // TODO 自动生成的方法存根
        int countNumber = 0;
        for(int i = 0; i < 20; i++)
            for(int j = 0 ; j < 20; j++) {
                Point p = new Point(i,j);
                Link l = database.getIndexLinkMap().get(p);
                if(l!= null) {
                    countNumber += l.wlMask.cardinality();
                }
            }
        return countNumber;
    }
    /*
    波长分配函数,返回一个BitSet 表示可用进行波长分配的波长集合
     */
    @Override
    public boolean reserveSource(Event e){
        int id = e.getId();
        int index = Integer.MIN_VALUE;
        List<List<Integer>> path = e.getMultiPath();
        BitSet wl = new BitSet();

        List<Integer> workPath = null;
        for(List<Integer> singlePath: path){
            Object[] p = singlePath.toArray();
            BitSet bs = new BitSet(10);
            for(int i = 1; i < p.length;i++) {

                int begin = (Integer)p[i-1];
                int end = (Integer)p[i];
                Link link = database.getIndexLinkMap().get(new Point(begin,end));
                BitSet wlMask = link.wlMask;
                boolean ret = link.isFull();
                //如果波长全部被占用,则跳出
                if(ret)
                    break;
                else {
                    bs.or(wlMask);
                    //如果两条链路上的可用波长都不满足波长一致性,直接跳出
                    if(bs.isEmpty())
                        break;


                }

            }
            //根据bs的大小确定是否有可用的波长
            if(bs.isEmpty()) return false;
            else{
                wl = bs;
                workPath = singlePath;
                e.setWorkPath(workPath);
            }

        }
        index = wl.nextClearBit(0);
        for(int i =1 ; i < workPath.size() ;i++) {
            int begin = workPath.get(i-1);
            int end =workPath.get(i);
            Link link = database.getIndexLinkMap().get(new Point(begin,end));
            link.setWaveLengthId(index);
            BitSet updatedMask = link.getWlMask();
            updatedMask.set(index);
            link.setWlMask(updatedMask);
            //更新资源
            linkUtilization[begin][end] ++;
            linkUtilization[end][begin] ++;
        }
        return true;

    }

    @Override
    public double countLoad(int i) {
        int count = 0;
        int sum = 0;
        for(int j =0; j < MAXIMUM; j++){
            if(adjacency[i][j] == 1){
                count += linkUtilization[i][j];
                sum += database.wlNum;
            }
        }
        double util = ((sum > 0)? count/sum: 0);
        return util;
    }

    @Override
    public List<Integer> findNeighbor(int i) {
        List<Integer> list = new ArrayList<>();
        for (int j = 0; j < MAXIMUM; j ++) {
            if(adjacency[i][j] == 1) {
                list.add(j);
            }
        }
        return list;
    }

    //
    // 找到权值最小的节点
    @Override
    public int calculateNW(List<Integer> neighbor) {
        Map<Double, Integer> map = new HashMap<>();
        double max = 0;
        for(int i : neighbor) {
            int sum = 0;
            int k = 0;

            for (int j = 0; j < MAXIMUM; j++) {
                if(adjacency[i][j] == 1 ) {
                   double reliability = weightedTopo[i][j];
                    sum += reliability*(database.wlNum - linkUtilization[i][j]);
                    k ++;
                }
            }
            double NW = k > 0 ? sum/k : 0;

            max = max > NW ? max : NW;
            map.put(NW,i);
        }
        return map.get(max);
    }

    @Override
    public void reliefResource(Event e) {
        List<Integer> workPath = e.getWorkPath();
        for(int i =1 ; i < workPath.size() ;i++) {
            int begin = workPath.get(i-1);
            int end =workPath.get(i);
            Link link = database.getIndexLinkMap().get(new Point(begin,end));
            int index = link.getWaveLengthId();
            BitSet updatedMask = link.getWlMask();
            updatedMask.clear(index);
            link.setWlMask(updatedMask);
            //更新资源
            linkUtilization[begin][end] --;
            linkUtilization[end][begin] --;
        }
    }


    /*
    @param a:源节点
    @param b:进行帮助的邻节点
    @param c:b的邻节点
     */
    public double generateVector(Node a, Node b, Node c) {
        double res = 0;
        double xA = a.getX();
        double yA = a.getY();
        double xB = b.getX();
        double yB = b.getY();
        double xC = c.getX();
        double yC = c.getY();
        double[] posA = {xA,yA};
        double[] posB = {xB,yB};
        double[] posC = {xC,yC};
        double[] ba = {xA - xB, yA - yB};
        double[] bc = {xC - xB, yC - yB};
        double disAB = Math.sqrt(Math.pow(xA - xB,2) + Math.pow(yA - yB, 2));
        if(disAB < rAcess) return 0; // 不需要移动;
        double cba = angle(ba,bc);
        if(cba > 90 && cba <= 180 )  {
            double dis = mobileRange(posA, posB, posC ,rCommunicate);// 需要帮助的点与可通信点之间的距离
            // double disAB = Math.sqrt(Math.pow(xA - xB,2) + Math.pow(yA - yB, 2));
            if (dis == 0) {
                res = disAB - rAcess;
            }
            else if (dis < rAcess) {
                 res = rAcess - dis;
            }
            else return Integer.MAX_VALUE;
        }
        else {
            res = disAB - rAcess;
        }
        return res;

    }
    public  double mobileRange(double[] needHelp, double[] mobileNode, double[] neighbor, int range) {
        double res = 0;
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

        // double[] point_k = {xk, yk};// 垂足坐标
        double d1 = Math.sqrt(Math.pow((xk - x1), 2) + Math.pow((yk - y1), 2));
        double d2 = Math.sqrt(Math.pow((xk - x2), 2) + Math.pow((yk - y2), 2));
        double d12 = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));// 垂足与直线上两点中较近点的距离
        double d3 = Math.sqrt(Math.pow((xk - x3), 2) + Math.pow((yk - y3), 2));// 垂线距离


        double dis = Math.sqrt(Math.pow(range, 2) - Math.pow(d3, 2)) ; // C的投影点到通信范围与直线AB的交点之间的距离
        double keep = dis - d2;
        if(keep < d12)  res = d12 - keep;
            return res;

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

    public Node updatePosition(double d, Node src, Node neighbor) {
        double x1 = src.getX();
        double y1 = src.getY();
        double x2 = neighbor.getX();
        double y2 = neighbor.getY();
        double dis = Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
        double x3 = (x1 - x2) * d/dis + x2;
        double y3 = (y1 - y2) * d/dis + y2;
        Node node = new Node(x3,y3,neighbor.getNodeId());
        return node ;
    }

}
