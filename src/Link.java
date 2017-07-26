import java.util.BitSet;

/**
 * Created by zhangjing60 on 17/6/29.
 */

public class Link {
    final int wlNum = Database.wlNum;

    int src;

    public int getDes() {
        return des;
    }

    public void setDes(int des) {
        this.des = des;
    }

    int des;

    public double getWeight() {
        return weight;
    }

    public void setWeight(double initWeight) {
        this.weight = initWeight;
    }

    double weight;

    public int getSrc() {
        return src;
    }

    public void setSrc(int src) {
        this.src = src;
    }

    public BitSet getWlMask() {
        return wlMask;
    }

    public void setWlMask(BitSet wlMask) {
        this.wlMask = wlMask;
    }

    BitSet wlMask = new BitSet(wlNum);
    public Link (int i, int j) {
        this.src = i;
        this.des = j;
    }
    public Link(int i, int j ,double weight){
        this.src = i;
        this.des = j;
        this.weight = weight;
    }
    public boolean isFull(){
        if(wlMask.size() >= 10){
            return true;
        }
        return false;
    }

}