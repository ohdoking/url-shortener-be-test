package com.github.vivyteam.url.util;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class Base62UtilTest {

    @InjectMocks
    private Base62Util base62Util;

    private static Stream<Arguments> provideBase62EncodedAndDecodedValue() {
        return Stream.of(
                Arguments.of(0, "0"),
                Arguments.of(1, "1"),
                Arguments.of(100, "1C"),
                Arguments.of(1000000000, "15FTGg"),
                Arguments.of(53234, "dQC")
        );
    }

    @ParameterizedTest
    @MethodSource("provideBase62EncodedAndDecodedValue")
    public void givenLongId_whenExecuteEncode_ThenReturnEncodedValue(long input, String expected) {

        // when
        String result = base62Util.encode(input);

        // then
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("provideBase62EncodedAndDecodedValue")
    public void given1cEncodedValue_whenExecuteDecode_ThenReturnDecodedValue(long expected, String input) {

        // when
        long result = base62Util.decode(input);

        // then
        assertEquals(expected, result);
    }

}