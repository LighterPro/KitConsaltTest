package ru.kitconsalt;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Port {
    String[] indexes;
    Integer[][] arrayOfConvertedIndexes;

    public Port(String[] indexes) {
        assert indexes != null;
        assert indexes.length > 0;
        this.indexes = indexes;
    }

    public Integer[][] convert(String[] indexes) {
        Integer[][] result = new Integer[indexes.length][];

        for (int i = 0; i < indexes.length; i++) {
            result[i] = convertStringToInts(indexes[i]);
        }

        return result;
    }

    private static Integer[] convertStringToInts(String string) {
        String[] split = string.trim().split("\\s*,\\s*");
        ArrayList<Integer> list = new ArrayList<>();

        for (String s : split) {
            boolean isIntRange = s.matches("-?\\d+ *- *-?\\d+");

            if (isIntRange) {
                list.addAll(revealIntRange(s));
            } else {
                list.add(Integer.parseInt(s));
            }
        }
        Integer[] result = new Integer[list.size()];
        list.toArray(result);
        return result;
    }

    private static List<Integer> revealIntRange(String rangeString) {
        String[] numbers = rangeString.split(" *- *");

        int start = Integer.parseInt(numbers[0]);
        int end = Integer.parseInt(numbers[1]);

        try {
            if (start > end) {
                throw new IntRangeFormatException("Integer range \"" + rangeString + "\" has wrong format. Range must start with a lower number.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return IntStream.range(start, end + 1).boxed().collect(Collectors.toList());
    }

    public <T> LinkedHashSet<List<T>> getCartesianProduct(List<List<T>> lists) {
        LinkedHashSet<List<T>> resultSet = new LinkedHashSet<>();
        product(resultSet, new ArrayList<>(), lists);
        return resultSet;
    }

    private static <T> void product(Set<List<T>> resultSet, List<T> existingCollection, List<List<T>> listOfLists) {
        for (T value : listOfLists.get(0)) {
            List<T> newCollection = new ArrayList<>();
            newCollection.addAll(existingCollection);
            newCollection.add(value);

            if (listOfLists.size() == 1) {
                resultSet.add(newCollection);
            } else {
                List<List<T>> newListOfLists = new ArrayList<>();
                for (int i = 1; i < listOfLists.size(); i++) {
                    newListOfLists.add(listOfLists.get(i));
                }
                product(resultSet, newCollection, newListOfLists);
            }
        }
    }

    private static <T> List<List<T>> twoDArrayToListOfLists(T[][] twoDArray) {
        List<List<T>> list = new ArrayList<>();
        for (T[] array : twoDArray) {
            List<T> innerList = new ArrayList<>();
            innerList.addAll(Arrays.asList(array));
            list.add(innerList);
        }
        return list;
    }
}

