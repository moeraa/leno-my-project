package com.org.moer;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.collections4.CollectionUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author zhangjd
 * @since 2020-07-23
 */
@Data
@Accessors(chain = true)
public class People {
    String name;
    String idfaMd5;
    String idfa;
    String IMEI;
    String IMEIMd5;
    Long phone;
    String phoneMd5;
    String dvId;

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        List<People> list1 = new CopyOnWriteArrayList<>();
        List<People> list2 = new CopyOnWriteArrayList<>();
        List<People> list3 = new CopyOnWriteArrayList<>();
        new Thread(() -> {
            for (int i = 0; i < 50000; i++) {
                People people = new People()
                        .setDvId(RandomUtil.randomString(10))
                        .setIdfa(RandomUtil.randomString(10))
                        .setIdfaMd5(RandomUtil.randomString(16))
                        .setIMEI(RandomUtil.randomString(16))
                        .setIMEIMd5(RandomUtil.randomString(16))
                        .setName(RandomUtil.randomString(5))
                        .setPhone(RandomUtil.randomLong(13))
                        .setPhoneMd5(RandomUtil.randomString(16));
                list1.add(people);
                if (i % 50 == 0) {
                    list2.add(people);
                }
                if (i % 100 == 0) {
                    list3.add(people);
                }
            }

        }).start();
        new Thread(() -> {
            for (int i = 0; i < 50000; i++) {
                People people = new People()
                        .setDvId(RandomUtil.randomString(10))
                        .setIdfa(RandomUtil.randomString(10))
                        .setIdfaMd5(RandomUtil.randomString(16))
                        .setIMEI(RandomUtil.randomString(16))
                        .setIMEIMd5(RandomUtil.randomString(16))
                        .setName(RandomUtil.randomString(5))
                        .setPhone(RandomUtil.randomLong(13))
                        .setPhoneMd5(RandomUtil.randomString(16));
                list2.add(people);
                if (i % 50 == 0) {
                    list3.add(people);
                }
                if (i % 100 == 0) {
                    list1.add(people);
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 50000; i++) {
                People people = new People()
                        .setDvId(RandomUtil.randomString(10))
                        .setIdfa(RandomUtil.randomString(10))
                        .setIdfaMd5(RandomUtil.randomString(16))
                        .setIMEI(RandomUtil.randomString(16))
                        .setIMEIMd5(RandomUtil.randomString(16))
                        .setName(RandomUtil.randomString(5))
                        .setPhone(RandomUtil.randomLong(13))
                        .setPhoneMd5(RandomUtil.randomString(16));
                list3.add(people);
                if (i % 50 == 0) {
                    list1.add(people);
                }
                if (i % 100 == 0) {
                    list2.add(people);
                }
            }
        }).start();

        Thread.sleep(3000);
        System.out.println("list1 size:" + list1.size());
        System.out.println("list2 size:" + list2.size());
        System.out.println("list3 size:" + list3.size());

        Collection<People> union = CollUtil.union(list1, list2);
        System.out.println("1、2交集" + union.size());
        Instant start = Instant.now();
        Collection subtract = CollectionUtils.subtract(union, list3);
       /* HashSet<People> subtract = new HashSet<>(union);
        System.out.println(subtract.size());
        HashSet<People> h2 = new HashSet<>(list3);
        subtract.removeAll(h2);*/
        Instant now = Instant.now();
        long seconds = Duration.between(start, now).getSeconds();
        System.out.println("持续时间：" + seconds);
        System.out.println("交集之后差集：" + subtract.size());
    }
}

