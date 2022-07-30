import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;

import java.util.stream.Stream;

public class GeoServiceImplTest {

    @ParameterizedTest
    @MethodSource("provideParameters")
    public void testByIpCountry(String ipInput, Country expectedOutput) {
        // given ipInput and expectedOutput as arguments and:
        GeoServiceImpl geoService = new GeoServiceImpl();

        // when:
        Location resultLocation = geoService.byIp(ipInput);

        // then:
        Assertions.assertEquals(expectedOutput,
                resultLocation != null ? resultLocation.getCountry() : null);
    }

    @ParameterizedTest
    @MethodSource("provideParameters2")
    public void testByIpCity(String ipInput, String expectedOutput) {
        // given ipInput and expectedOutput as arguments and:
        GeoServiceImpl geoService = new GeoServiceImpl();

        // when:
        Location resultLocation = geoService.byIp(ipInput);

        // then:
        Assertions.assertEquals(expectedOutput,
                resultLocation != null ? resultLocation.getCity() : null);
    }

    private static Stream<Arguments> provideParameters() {
        return Stream.of(
                Arguments.of("127.0.0.1", null),
                Arguments.of("172.0.32.11", Country.RUSSIA),
                Arguments.of("96.00.00.00", Country.USA),
                Arguments.of("32.00.00.00", null)
        );
    }

    private static Stream<Arguments> provideParameters2() {
        return Stream.of(
                Arguments.of("127.0.0.1", null),
                Arguments.of("172.0.32.11", "Moscow"),
                Arguments.of("96.00.00.00", "New York"),
                Arguments.of("32.00.00.00", null)
        );
    }

}
