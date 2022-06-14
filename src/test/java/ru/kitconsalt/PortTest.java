package ru.kitconsalt;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PortTest {

    @Test
    void convertStringToIntsTest() {
        assertArrayEquals(Port.convertStringToInts("1,3-5"), new Integer[]{1, 3, 4, 5});
        assertArrayEquals(Port.convertStringToInts("3-5,1"), new Integer[]{3, 4, 5, 1});
        assertArrayEquals(Port.convertStringToInts(",1,3-5,"), new Integer[]{1, 3, 4, 5});
        assertArrayEquals(Port.convertStringToInts("1,-4--2"), new Integer[]{1, -4, -3, -2});
        assertArrayEquals(Port.convertStringToInts("0-1,2,3-5"), new Integer[]{0, 1, 2, 3, 4, 5});
    }

    @Test
    void revealIntRangeTest() {
        assertEquals(Port.revealIntRange("3-5"), Arrays.asList(3, 4, 5));
        assertEquals(Port.revealIntRange("3 - 5"), Arrays.asList(3, 4, 5));
        assertEquals(Port.revealIntRange("3-3"), List.of(3));
        assertEquals(Port.revealIntRange("0-3"), Arrays.asList(0, 1, 2, 3));
        assertEquals(Port.revealIntRange("-0-3"), Arrays.asList(0, 1, 2, 3));
        assertEquals(Port.revealIntRange("-3-5"), Arrays.asList(-3, -2, -1, 0, 1, 2, 3, 4, 5));
        assertEquals(Port.revealIntRange("-5--2"), Arrays.asList(-5, -4, -3, -2));
        assertEquals(Port.revealIntRange("-5 - -2"), Arrays.asList(-5, -4, -3, -2));

        assertThrows(RuntimeException.class, () -> Port.revealIntRange("-4--5"));

        Exception exception = assertThrows(RuntimeException.class, () -> Port.revealIntRange("4-2"));
        String expectedMessage = "Range must start with a lower number";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getUniqCombinationsTest() {
        Port port = new Port(new String[]{"1,3-5", "2", "3-4"});
        Integer[][] convert = port.convert(port.indexes);
        LinkedHashSet<List<Integer>> combinations = port.getUniqCombinations(convert);

        ArrayList<List<Integer>> actualCombinations = new ArrayList<>(combinations);
        ArrayList<List<Integer>> expectedCombinations = new ArrayList<>();

        expectedCombinations.add(Arrays.asList(1, 2, 3));
        expectedCombinations.add(Arrays.asList(1, 2, 4));
        expectedCombinations.add(Arrays.asList(3, 2, 3));
        expectedCombinations.add(Arrays.asList(3, 2, 4));
        expectedCombinations.add(Arrays.asList(4, 2, 3));
        expectedCombinations.add(Arrays.asList(4, 2, 4));
        expectedCombinations.add(Arrays.asList(5, 2, 3));
        expectedCombinations.add(Arrays.asList(5, 2, 4));

        assertEquals(expectedCombinations.size(), actualCombinations.size());

        for (int i = 0; i < actualCombinations.size(); i++) {
            assertEquals(expectedCombinations.get(i), actualCombinations.get(i));
        }
    }
}