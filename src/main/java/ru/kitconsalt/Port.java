package ru.kitconsalt;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Port {
    String[] indexes;

    public Port(String[] indexes) {
        if (indexes == null || indexes.length == 0) {
            throw new RuntimeException("Input data must not be empty");
        }
        this.indexes = indexes;
    }

    //Метод, преобразовывающий массив строк indexes в массив последовательностей чисел
    public Integer[][] convert(String[] indexes) {
        Integer[][] result = new Integer[indexes.length][];

        for (int i = 0; i < indexes.length; i++) {
            result[i] = convertStringToInts(indexes[i]);
        }

        return result;
    }

    public static Integer[] convertStringToInts(String origString) {
        String string = origString.trim().replaceFirst("^,+", "");
        String[] split = string.split("\\s*,\\s*");
        ArrayList<Integer> list = new ArrayList<>();

        for (String s : split) {
            boolean isIntRange = s.matches("-?\\d+\\s*-\\s*-?\\d+");

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

    public static List<Integer> revealIntRange(String rangeString) {
        String[] numbers = rangeString.split("(?<=\\d)\\s*-\\s*");

        int start = Integer.parseInt(numbers[0]);
        int end = Integer.parseInt(numbers[1]);

        if (start > end) {
            throw new RuntimeException("Integer range \"" + rangeString +
                    "\" has wrong format. Range must start with a lower number.");
        }

        return IntStream.range(start, end + 1)
                .boxed()
                .collect(Collectors.toList());
    }

    public <T> LinkedHashSet<List<T>> getUniqCombinations(T[][] array) {
        List<List<T>> lists = twoDArrayToListOfLists(array);
        LinkedHashSet<List<T>> resultSet = new LinkedHashSet<>();
        product(resultSet, new ArrayList<>(), lists);
        return resultSet;
    }

    //Метод, возвращающий всевозможные уникальные упорядоченные группы элементов полученных массивов чисел
    public static <T> void product(Set<List<T>> resultSet, List<T> existingCollection, List<List<T>> listOfLists) {
        for (T value : listOfLists.get(0)) {
            List<T> newCollection = new ArrayList<>(existingCollection);
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

    public static <T> List<List<T>> twoDArrayToListOfLists(T[][] twoDArray) {
        List<List<T>> list = new ArrayList<>();
        for (T[] array : twoDArray) {
            List<T> innerList = new ArrayList<>(Arrays.asList(array));
            list.add(innerList);
        }
        return list;
    }
}