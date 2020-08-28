package com.paymybuddy.PayMyBuddyWeb.units.utils;

import com.paymybuddy.PayMyBuddyWeb.Utils.MSFileUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class MSFileUtilsTest {

    @Tag("MSFileUtilsTest")
    @Test
    void isFileExist_test() throws IOException {
        String filePath = "src/main/resources/static/test.txt";
        assertThat(MSFileUtils.isFileExist(filePath, "txt")).isEqualTo(null);
        File file = new File(filePath);
        file.createNewFile();
        assertThat(MSFileUtils.isFileExist(filePath, "txt")).isEqualTo("unknown");
        file.delete();
    }
}