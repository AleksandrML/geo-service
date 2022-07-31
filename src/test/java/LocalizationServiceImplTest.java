import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.stream.Stream;

public class LocalizationServiceImplTest {

    @ParameterizedTest
    @MethodSource("provideParameters")
    public void testLocale(Country countryInput, String expectedOutput) {
        // given countryInput and expectedOutput as arguments and:
        LocalizationServiceImpl localizationService = new LocalizationServiceImpl();

        // when:
        String result = localizationService.locale(countryInput);

        // then:
        Assertions.assertEquals(expectedOutput, result);
    }

    private static Stream<Arguments> provideParameters() {
        return Stream.of(
                Arguments.of(Country.RUSSIA, "Добро пожаловать"),
                Arguments.of(Country.USA, "Welcome"),
                Arguments.of(Country.BRAZIL, "Welcome"),
                Arguments.of(Country.GERMANY, "Welcome")
        );
    }
}
