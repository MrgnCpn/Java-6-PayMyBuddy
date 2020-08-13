package com.paymybuddy.PayMyBuddyWeb.Utils;

import java.util.*;

public class MSArrayUtils {

    /**
     * Sort map by kay
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortMapByKey(Map<K, V> map) {
        Map<K, V> sortedMapByKey = new TreeMap<>(map);
        return sortedMapByKey;
    }

    /**
     * Sort map by value
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortMapByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}
