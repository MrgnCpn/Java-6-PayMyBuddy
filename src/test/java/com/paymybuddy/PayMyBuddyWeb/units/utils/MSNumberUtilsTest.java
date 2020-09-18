package com.paymybuddy.paymybuddyweb.units.utils;

import com.paymybuddy.paymybuddyweb.utils.MSNumberUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MSNumberUtilsTest {

    @Tag("MSNumberUtilsTest")
    @Test
    void getDouble_2_digits_test() {
        assertThat(MSNumberUtils.getDoubleTwoDigits(1.12)).isEqualTo(1.12);
        assertThat(MSNumberUtils.getDoubleTwoDigits(1234.1)).isEqualTo(1234.1);
        assertThat(MSNumberUtils.getDoubleTwoDigits(1234.123)).isEqualTo(1234.12);
        assertThat(MSNumberUtils.getDoubleTwoDigits(1234.12356789)).isEqualTo(1234.12);
    }
}