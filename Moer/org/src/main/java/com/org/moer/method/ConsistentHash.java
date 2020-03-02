package com.org.moer.method;
import java.util.*;

public class ConsistentHash<T> {
    //节点的复制因子,实际节点个数 * numberOfReplicas =虚拟节点个数
    private final int numberOfReplicas;
    // 存储虚拟节点的hash值到真实节点的映射
    private final SortedMap<Integer, T> circle = new TreeMap<Integer, T>();


    public ConsistentHash(int numberOfReplicas, Collection<T> nodes) {
        this.numberOfReplicas = numberOfReplicas;
        for (T node : nodes) {
            add(node);

        }
    }

    public void add(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
//            对于一个实际机器节点node，对应的numberOfReplicas个虚拟节点
//            不用的虚拟节点i有不用的hash值，但是对应的是同一个机器的node；
//            虚拟的node一般是均衡的分布在换上的，数据存储在顺时针方向的虚拟node上
            String nodestr = node.toString() + i;
            int hashCode = nodestr.hashCode();
            System.out.println("nodeStr:"+nodestr+" --- hashCode: " + hashCode);
            circle.put(hashCode, node);
        }
    }

    public void remove(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.remove(node.toString() + i);
        }
    }

    /*获取一个最近顺时针节点，根据给定的key取hash
     * 然后在取得顺时针方向上最近的一个虚拟节点对应的实际节点
     * 再从实际节点中取出数据*/
    public T get(Object key) {
        if (circle.isEmpty()) {
            return null;
        }
        int hash = key.hashCode();//node 用String 来表示，获取node在hash环中的hashcode；
        System.out.println("hashCode :" + hash);
        if (!circle.containsKey(hash)) {
            //数据应设在两台虚拟机器所在环之间，就安装顺时针方向寻找机器
            SortedMap<Integer, T> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(hash);

    }
    public long getSize(){
        return circle.size();
    }
    /*查看表示整个哈希环中各个虚拟节点的位置*/
    public void testBalance(){
        Set<Integer> sets = circle.keySet();
        SortedSet<Integer> sortedSets = new TreeSet<Integer>(sets);
        for (Integer hashCode: sortedSets) {
            System.out.println(hashCode);
        }
        System.out.println("===========each location`s distance are follows:===============");
        /*查看两个相邻的hashCode*/
        Iterator<Integer> it = sortedSets.iterator();
        Iterator<Integer> it2 = sortedSets.iterator();
        if (it2.hasNext()){
           it2.next();
           long keyPre,keyAfter;
            while (it.hasNext()&&it2.hasNext()) {
                keyPre =it.next();
                keyAfter = it2.next();
                System.out.println(keyAfter-keyPre);
            }
        }
    }

    public static void main(String[] args) {
        Set<String> nodes = new HashSet<String>();
        nodes.add("A");
        nodes.add("B");
        nodes.add("C");
        ConsistentHash hash = new ConsistentHash(2,nodes);
        hash.add("D");
        System.out.println("has circle size:"+hash.getSize());
//        System.out.println("location of each node are follows: ");
//        hash.testBalance();
        String node = (String) hash.get("apple");
        System.out.println("node ------>:"+node);


    }
}
