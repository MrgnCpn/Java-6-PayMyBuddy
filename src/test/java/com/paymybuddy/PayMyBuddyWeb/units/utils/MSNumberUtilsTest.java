package com.paymybuddy.PayMyBuddyWeb.units.utils;

import com.paymybuddy.PayMyBuddyWeb.Utils.MSNumberUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MSNumberUtilsTest {

    @Tag("MSNumberUtilsTest")
    @Test
    void getDouble_2_digits_test() {
        assertThat(MSNumberUtils.getDouble_2_digits(1.12)).isEqualTo(1.12);
        assertThat(MSNumberUtils.getDouble_2_digits(1234.1)).isEqualTo(1234.1);
        assertThat(MSNumberUtils.getDouble_2_digits(1234.123)).isEqualTo(1234.12);
        assertThat(MSNumberUtils.getDouble_2_digits(1234.12356789)).isEqualTo(1234.12);
    }
}