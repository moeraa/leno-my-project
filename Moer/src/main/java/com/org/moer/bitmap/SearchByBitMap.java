package com.org.moer.bitmap;

import cn.hutool.cache.impl.LRUCache;

import java.util.HashMap;
import java.util.List;

/**
 * @author : hepw
 * @date : 2020/9/24 21:02
 * @func :
 */
public class SearchByBitMap {
    private final HashMap<String, ByteBitMap> data = new HashMap<>();
    //private final LinkedList LRUList = new LinkedList();

    private static Integer LEN;

    public SearchByBitMap(Integer len) {
        LEN = len;
    }

    private void create(String key) {
        if (!data.containsKey(key)) {
            data.put(key, new ByteBitMap(LEN));
        }
    }

    public void add(String key, int val) {
        if (!data.containsKey(key)) {
            create(key);
        }
        data.get(key).add(val);
    }

    public void add(String key, List<Integer> vals) {
        if (!data.containsKey(key)) {
            create(key);
        }
        ByteBitMap bitMap = data.get(key);
        vals.forEach(x -> {
            bitMap.add(x);
        });
    }

    public void add(String key, int[] vals) {
        if (!data.containsKey(key)) {
            create(key);
        }
        ByteBitMap bitMap = data.get(key);
        for (int i = 0; i < vals.length; i++) {
            bitMap.add(vals[i]);
        }

    }

    public List<Integer> union(List<String> keys) {
        ByteBitMap bitMap = new ByteBitMap(LEN);
        bitMap.initBase();
        keys.forEach(x -> {
            if (data.containsKey(x)) {
                bitMap.union(data.get(x));
            }
        });
        return bitMap.getValues();
    }
}
