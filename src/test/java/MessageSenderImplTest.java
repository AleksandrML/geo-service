import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;
import ru.netology.sender.MessageSenderImpl;

import java.util.Map;
import java.util.stream.Stream;

public class MessageSenderImplTest {

    @ParameterizedTest
    @MethodSource("provideParameters")
    public void testSend(Map<String, String> ipInput, String expectedOutput) {
        // given:
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp("172.0.32.11")).
                thenReturn(new Location("Moscow", Country.RUSSIA, "Lenina", 15));
        Mockito.when(geoService.byIp("96.00.00.00")).
                thenReturn(new Location("New York", Country.USA, null,  0));
        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");
        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");
        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);

        // when:
        String result = messageSender.send(ipInput);

        // then:
        Assertions.assertEquals(expectedOutput, result);
    }

    private static Stream<Arguments> provideParameters() {
        return Stream.of(
                Arguments.of(Map.of(MessageSenderImpl.IP_ADDRESS_HEADER, "172.0.32.11"), "Добро пожаловать"),
                Arguments.of(Map.of(MessageSenderImpl.IP_ADDRESS_HEADER, "96.00.00.00"), "Welcome")
        );
    }

}
