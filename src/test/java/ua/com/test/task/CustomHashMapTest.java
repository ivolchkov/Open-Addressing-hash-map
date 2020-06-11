package ua.com.test.task;

import org.junit.Before;
import org.junit.Test;
import ua.com.test.task.exception.ElementNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class CustomHashMapTest {
    private static final int SIZE = 10;

    private CustomHashMap map;

    @Before
    public void clean() {
        map = new CustomHashMap(SIZE);
    }

    @Test
    public void put_shouldAddElement() {
        map.put(1, 1);
        int expected = 1;
        int actual = map.size();

        assertEquals(expected, actual);
    }

    @Test
    public void put_shouldReplaceElementValueWithSameKey() {
        map.put(1, 1);
        map.put(1, 8);
        long expected = 8;
        long actual = map.get(1);

        assertEquals(expected, actual);
    }

    @Test
    public void get_shouldReturnElementValue() {
        map.put(1, 2);
        long expected = 2;
        long actual = map.get(1);

        assertEquals(expected, actual);
    }

    @Test
    public void get_shouldThrowIllegalArgumentException() {
        map.put(1, 2);

        assertThrows("Invalid key value!", ElementNotFoundException.class,
                () -> map.get(2));
    }


    @Test
    public void size_shouldReturnValidSize() {
        for (int i = 0; i < 16; i++) {
            map.put(i, i);
        }
        int expected = 16;
        int actual = map.size();

        assertEquals(expected, actual);
    }
}