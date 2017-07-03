package simulation;

import java.awt.Point;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import simulation.Event.EventType;

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
	int MAXIMUM = 0;
	int ServiceQuantity = 0;
	int blockIgnore = 0;
	Map<Integer,Event> serviceEventMap;
	Database database;
	
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
		ServiceQuantity = db.getServiceQuantity();
		MAXIMUM = db.getMaximum();
		blockIgnore =db.getBlockIgnore();
		
		while(serviceId <= ServiceQuantity ) {
			{
				
			}
		}
	}
	//该部分是对于事件的处理，属于核心部分
	@Override
	public void dealWithEvent(Event event) {
		// TODO 自动生成的方法存根
		switch(event.getEventType()){
		case ARRIVAL:
		{
			Event event_t = new Event(event);
			caculatePath(event_t);
			//核心！！！！
			
		}
		case END:
		}
	}
	
	@Override
	public void caculatePath(Event event) {
		// TODO 自动生成的方法存根
		int src = event.getSrc();
		int des = event.getDes();
		Point p = new Point(src,des);
		event.workPath = database.KSP_path.get(p);
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
		return 0;
	}

}
