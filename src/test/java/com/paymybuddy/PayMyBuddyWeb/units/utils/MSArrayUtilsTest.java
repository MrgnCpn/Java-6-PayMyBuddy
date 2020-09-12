package com.paymybuddy.PayMyBuddyWeb.units.utils;

import com.paymybuddy.PayMyBuddyWeb.Utils.MSArrayUtils;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MSArrayUtilsTest {
    private Map<String, String> map;

    @BeforeEach
    void init_each() {
        map = new HashMap<>();
        map.put("3", "E"); // 1
        map.put("1", "U"); // 5
        map.put("5", "F"); // 2
        map.put("4", "L"); // 3
        map.put("2", "S"); // 4
    }

    @Tag("MSArrayUtilsTest")
    @Test
    void sortMap_test() {
        List<String> list = new ArrayList<>();
        list = parseMap(list);
        assertThat(list.get(0)).isEqualTo("1 : U");
        assertThat(list.get(1)).isEqualTo("2 : S");
        assertThat(list.get(2)).isEqualTo("3 : E");
        assertThat(list.get(3)).isEqualTo("4 : L");
        assertThat(list.get(4)).isEqualTo("5 : F");


        map = MSArrayUtils.sortMapByValue(map);
        list = parseMap(list);
        assertThat(list.get(0)).isEqualTo("3 : E");
        assertThat(list.get(1)).isEqualTo("5 : F");
        assertThat(list.get(2)).isEqualTo("4 : L");
        assertThat(list.get(3)).isEqualTo("2 : S");
        assertThat(list.get(4)).isEqualTo("1 : U");

        map = MSArrayUtils.sortMapByKey(map);
        list = parseMap(list);
        assertThat(list.get(0)).isEqualTo("1 : U");
        assertThat(list.get(1)).isEqualTo("2 : S");
        assertThat(list.get(2)).isEqualTo("3 : E");
        assertThat(list.get(3)).isEqualTo("4 : L");
        assertThat(list.get(4)).isEqualTo("5 : F");
    }

    private List<String> parseMap(List<String> list){
        list.clear();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            list.add(entry.getKey() + " : " + entry.getValue());
        }
        return list;
    }
}