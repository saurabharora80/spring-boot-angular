package org.saurabh.springboot.rest;

import org.junit.Test;
import org.springframework.http.converter.HttpMessageNotReadableException;

import static org.assertj.core.api.Assertions.assertThat;

public class ExceptionHandlersTest {

    @Test
    public void testName() throws Exception {
        HttpMessageNotReadableException httpMessageNotReadableException = new HttpMessageNotReadableException(
                "Could not read JSON: Can not construct instance of org.saurabh.springboot.domain.Gender from String value 'INVALID-GENDER': value not one of declared Enum instance names: [MALE, FEMALE, OTHER]");
        assertThat(new ExceptionHandlers(null).handleException(httpMessageNotReadableException)).isEqualTo("Gender");

    }
}