import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Horse.class)
@ExtendWith(MockitoExtension.class)
class HorseTest {
    @Test
    public void nullNameException(){
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 1));
    }
    @Test
    public void whenAssertingException() {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    throw new IllegalArgumentException("Name cannot be null.");
                }
        );
        assertEquals("Name cannot be null.", exception.getMessage());
    }
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t\t"})
    public void blankNameException(String name) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1, 1));
        assertEquals("Name cannot be blank.", e.getMessage());
    }
    @Test
    public void nullSpeedException(){
        assertThrows(IllegalArgumentException.class, () -> new Horse("Буцефал", -1, 1));
    }
    @Test
    public void whenAssertingSpeedException() {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    throw new IllegalArgumentException("Speed cannot be negative.");
                }
        );
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }
    @Test
    public void nullDistanceException(){
        assertThrows(IllegalArgumentException.class, () -> new Horse("Буцефал", 1, -1));
    }
    @Test
    public void whenAssertingDistanceException() {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    throw new IllegalArgumentException("Distance cannot be negative.");
                }
        );
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }
    @Test
    public void getName() throws NoSuchFieldException, IllegalAccessException { //1-й варіант
        Horse horse = new Horse("qwerty",1,1 );
        Field name = Horse.class.getDeclaredField("name");
        name.setAccessible(true);
        String nameValue = (String) name.get(horse);
        assertEquals("qwerty",nameValue);
    }
    @Test
    public void getSpeed() throws NoSuchFieldException, IllegalAccessException { //2-й варіант
        double expectedSpeed = 433;
        Horse horse = new Horse("qwerty",expectedSpeed,1 );

        assertEquals(expectedSpeed, horse.getSpeed());
    }
    @Test
    public void getDistance() throws NoSuchFieldException, IllegalAccessException { //3-й варіант
        Horse horse = new Horse("qwerty",1,500 );
        assertEquals(500, horse.getDistance());
    }
    @Test
    public void zeroDistanceByDefault() throws NoSuchFieldException, IllegalAccessException { //3-й варіант
        Horse horse = new Horse("qwerty",1);
        assertEquals(0, horse.getDistance());
    }
    @Test
    void moveUseGetRandom () {
        try (MockedStatic<Horse> mockedStatic =  mockStatic( Horse.class)) {
            new Horse("qwerty", 31, 283).move();
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @Test
    public void testGetRandomDistance() {
        mockStatic(Horse.class);
        when(Horse.getRandomDouble(0.2, 0.9)).thenReturn(0.5);
        Horse horse = new Horse("Black Beauty", 10.0, 0.0);
        horse.move();
        assertEquals(5.0, horse.getDistance(), 0.01);
    }


}
