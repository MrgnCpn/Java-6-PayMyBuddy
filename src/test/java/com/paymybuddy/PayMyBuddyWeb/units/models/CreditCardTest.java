package com.paymybuddy.paymybuddyweb.units.models;

import com.paymybuddy.paymybuddyweb.models.CreditCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class CreditCardTest {
    private CreditCard creditCard;

    @BeforeEach
    void init_each(){
        creditCard = new CreditCard(
                1,
                1,
                "VISA",
                "0123456789012345",
                "123",
                "01/20",
                "My Card"
        );
    }

    @Tag("CreditCardTest")
    @Test
    void get_test() {
        assertThat(creditCard.getId()).isEqualTo(1);
        assertThat(creditCard.getUserId()).isEqualTo(1);
        assertThat(creditCard.getNumber()).isEqualTo("0123456789012345");
        assertThat(creditCard.getType()).isEqualTo("VISA");
        assertThat(creditCard.getCvv()).isEqualTo("123");
        assertThat(creditCard.getDate()).isEqualTo("01/20");
        assertThat(creditCard.getWording()).isEqualTo("My Card");
    }

    @Tag("CreditCardTest")
    @Test
    void set_test() {
        creditCard.setId(2);
        creditCard.setUserId(2);
        creditCard.setNumber("4567456745674567");
        creditCard.setType("MAST");
        creditCard.setCvv("987");
        creditCard.setDate("03/21");
        creditCard.setWording("My New Card");

        assertThat(creditCard.getId()).isEqualTo(2);
        assertThat(creditCard.getUserId()).isEqualTo(2);
        assertThat(creditCard.getNumber()).isEqualTo("4567456745674567");
        assertThat(creditCard.getType()).isEqualTo("MAST");
        assertThat(creditCard.getCvv()).isEqualTo("987");
        assertThat(creditCard.getDate()).isEqualTo("03/21");
        assertThat(creditCard.getWording()).isEqualTo("My New Card");
    }

    @Tag("CreditCardTest")
    @Test
    void setAsNull_test() {
        creditCard.setId(0);
        creditCard.setUserId(0);
        creditCard.setNumber("");
        creditCard.setType("");
        creditCard.setCvv("");
        creditCard.setDate("");
        creditCard.setWording("");

        assertThat(creditCard.getId()).isZero();
        assertThat(creditCard.getUserId()).isZero();
        assertThat(creditCard.getNumber()).isEmpty();
        assertThat(creditCard.getType()).isEmpty();
        assertThat(creditCard.getCvv()).isEmpty();
        assertThat(creditCard.getDate()).isEmpty();
        assertThat(creditCard.getWording()).isEmpty();
    }

    @Tag("CreditCardTest")
    @Test
    void getNumberSplited_test(){
        assertThat(creditCard.getNumberSplited().get(0)).isEqualTo("0123");
        assertThat(creditCard.getNumberSplited().get(1)).isEqualTo("4567");
        assertThat(creditCard.getNumberSplited().get(2)).isEqualTo("8901");
        assertThat(creditCard.getNumberSplited().get(3)).isEqualTo("2345");

        assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> creditCard.getNumberSplited().get(-1));
        assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> creditCard.getNumberSplited().get(4));
    }

    @Tag("CreditCardTest")
    @Test
    void getNumberHide_test(){
        assertThat(creditCard.getNumberHide().get(0)).isEqualTo("****");
        assertThat(creditCard.getNumberHide().get(1)).isEqualTo("****");
        assertThat(creditCard.getNumberHide().get(2)).isEqualTo("****");
        assertThat(creditCard.getNumberHide().get(3)).isEqualTo("2345");

        assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> creditCard.getNumberSplited().get(-1));
        assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> creditCard.getNumberSplited().get(4));
    }

    @Tag("CreditCardTest")
    @Test
    void getWordingLib_test(){
        creditCard.setWording("This is a very long credit dard wording");
        assertThat(creditCard.getWordingLib()).isEqualTo("This is a very ...");
    }
}