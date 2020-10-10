package com.moer.day.bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : hepw
 * @date : 2020/9/24 19:55
 * @func :
 */
public class ByteBitMap {
    //用于记录数据是否存在(类似记录表)
    private byte[] bits;

    //容量,即要对多少对象进行bitmap操作
    private int capacity;


    public ByteBitMap(int capacity) {
        this.capacity = capacity;
        //1个byte包含8个bit位，那么capacity数据容量需要多少个byte[]来记录数据呢？答案是：capacity/8+1(非8倍数+1操作)
        bits = new byte[(capacity >> 3) + 1];
    }

    /**
     * description: 对象的添加（即标记）.
     *
     * @param objHashCode 需要标记对象的hashcode值(乐观认为不同对象不重复)
     */
    public void add(int objHashCode) {
        // objHashCode/8  得到byte[]的index（可以看作在第几行）
        int arrayIndex = objHashCode >> 3;
        // objHashCode%8  得到在byte[index]的第position位置上的bit位值为1
        int position = objHashCode & 0x07;
        //将1左移position后，那个位置自然就是1，然后和以前的数据做|，这样，那个位置就替换成1了。
        bits[arrayIndex] |= (1 << position);
    }

    public boolean contain(int objHashCode) {
        // objHashCode/8得到byte[]的index
        int arrayIndex = objHashCode >> 3;

        // objHashCode%8得到在byte[index]的位置
        int position = objHashCode & 0x07;

        //将1左移position后，那个位置自然就是1，然后和以前的数据做&，判断是否为0即可
        return (bits[arrayIndex] & (1 << position)) != 0;
    }

    public void clear(int objHashCode) {
        // objHashCode/8得到byte[]的index
        int arrayIndex = objHashCode >> 3;

        // objHashCode%8得到在byte[index]的位置
        int position = objHashCode & 0x07;

        //将1左移position后，那个位置自然就是1，然后对取反，再与当前值做&，即可清除当前的位置了.
        bits[arrayIndex] &= ~(1 << position);

    }

    /**
     * 创建一个用来union的数组
     */
    public void initBase() {
        for (int i = 0; i < bits.length; i++) {
            bits[i] = Byte.MAX_VALUE;
        }
    }

    public void display() {
        for (int i = 0; i < bits.length; i++) {
            for (int j = 0; j < 8; j++) {
                if (((bits[i] >> j) & 0X01) == 1) {
                    System.out.println(i * 8 + j);
                }
            }
        }
    }

    /**
     * 获取id列表
     *
     * @return
     */
    public List<Integer> getValues() {
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < bits.length; i++) {
            if (bits[i] != 0) {
                for (int j = 0; j < 8; j++) {
                    if (((bits[i] >> j) & 0X01) == 1) {
                        values.add(i * 8 + j);
                        System.out.println(i * 8 + j);
                    }
                }
            }
        }
        return values;
    }

    public void union(ByteBitMap bitMap) {
        for (int i = 0; i < bits.length; i++) {
            bits[i] = (byte) (bits[i] & bitMap.bits[i]);
        }
    }

    public static void main(String[] args) {
        ByteBitMap bitmap = new ByteBitMap(100);
        bitmap.add(7);
        System.out.println("插入7成功");

        boolean isexsit = bitmap.contain(7);
        System.out.println("7是否存在:" + isexsit);

        bitmap.clear(7);
        isexsit = bitmap.contain(7);
        System.out.println("7是否存在:" + isexsit);

    }
}
