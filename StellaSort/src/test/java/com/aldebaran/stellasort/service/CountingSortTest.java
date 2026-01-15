package com.aldebaran.stellasort.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import org.instancio.Instancio;
import org.instancio.InstancioApi;
import org.instancio.Select;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(InstancioExtension.class)
public class CountingSortTest {
    @Nested
    @DisplayName("When input is valid")
    class ValidInput {
        //#region MethodSources
        static Stream<Arguments> randomPositiveIntsForSmallSizeAndLowRange() {
            Random random = new Random(System.currentTimeMillis());

            Stream.Builder<Arguments> builder = Stream.builder();
            for (int i = 0; i < 100; i++) {
                builder.add(Arguments.of(random.nextInt(1, 1000), random.nextInt(0, 1000)));
            }

            return builder.build();
        }

        static Stream<Arguments> randomPositiveIntsForLargeSizeAndLowRange() {
            Random random = new Random(System.currentTimeMillis());

            Stream.Builder<Arguments> builder = Stream.builder();
            for (int i = 0; i < 100; i++) {
                builder.add(Arguments.of(random.nextInt(10000, 10000000), random.nextInt(0, 1000)));
            }

            return builder.build();
        }

        static Stream<Arguments> randomPositiveIntsForSmallSizeAndHighRange() {
            Random random = new Random(System.currentTimeMillis());

            Stream.Builder<Arguments> builder = Stream.builder();
            for (int i = 0; i < 100; i++) {
                builder.add(Arguments.of(random.nextInt(1, 1000), random.nextInt(10000, 10000000)));
            }

            return builder.build();
        }

        static Stream<Arguments> randomPositiveIntsLargeSizeAndHighRange() {
            Random random = new Random(System.currentTimeMillis());

            Stream.Builder<Arguments> builder = Stream.builder();
            for (int i = 0; i < 100; i++) {
                builder.add(Arguments.of(random.nextInt(10000, 10000000), random.nextInt(10000, 10000000)));
            }

            return builder.build();
        }
        //#endregion

        @Test
        @Tag("fast")
        void shouldReturnEmptyListForNullList() {
            List<Integer> results = CountingSort.sort(null);
            assertTrue(results.isEmpty());
        }

        @Test
        @Tag("fast")
        void shouldReturnEmptyListForEmptyList() {
            List<Integer> results = CountingSort.sort(new ArrayList<>());
            assertTrue(results.isEmpty());
        }

        @ParameterizedTest(name = "sample {index}: listLength={0}, maxElementSize={1}")
        @MethodSource("randomPositiveIntsForSmallSizeAndLowRange")
        @Tag("fast")
        void shouldSortListOfSmallSizeAndLowRangeSuccessfully(int listLength, int maxElementSize) {
            InstancioApi<List<Integer>> api = Instancio.ofList(Integer.class).size(listLength).generate(Select.allInts(), gen -> gen.ints().range(0, maxElementSize));
            System.out.println("Instancio Seed:" + api.asResult().getSeed());

            List<Integer> list = api.create();
            System.out.println("Greatest Element:" + list.stream().max(Integer::compareTo).orElseThrow());

            List<Integer> expected = new ArrayList<>(list);
            Collections.sort(expected); // stable sort

            List<Integer> results = CountingSort.sort(list);

            assertArrayEquals(expected.toArray(), results.toArray());
        }

        @Disabled // it's so slow, it took 9 minutes to complete all tests
        @ParameterizedTest(name = "sample {index}: listLength={0}, maxElementSize={1}")
        @MethodSource("randomPositiveIntsForLargeSizeAndLowRange")
        @Tag("slow")
        void shouldSortListOfLargeSizeAndLowRangeSuccessfully(int listLength, int maxElementSize) {
            InstancioApi<List<Integer>> api = Instancio.ofList(Integer.class).size(listLength).generate(Select.allInts(), gen -> gen.ints().range(0, maxElementSize));
            System.out.println("Instancio Seed:" + api.asResult().getSeed());

            List<Integer> list = api.create();
            System.out.println("Greatest Element:" + list.stream().max(Integer::compareTo).orElseThrow());

            List<Integer> expected = new ArrayList<>(list);
            Collections.sort(expected); // stable sort

            List<Integer> results = CountingSort.sort(list);

            assertArrayEquals(expected.toArray(), results.toArray());
        }

        @ParameterizedTest(name = "sample {index}: listLength={0}, maxElementSize={1}")
        @MethodSource("randomPositiveIntsForSmallSizeAndHighRange")
        @Tag("fast")
        void shouldSortListOfSmallSizeAndHighRangeSuccessfully(int listLength, int maxElementSize) {
            InstancioApi<List<Integer>> api = Instancio.ofList(Integer.class).size(listLength).generate(Select.allInts(), gen -> gen.ints().range(0, maxElementSize));
            System.out.println("Instancio Seed:" + api.asResult().getSeed());

            List<Integer> list = api.create();
            System.out.println("Greatest Element:" + list.stream().max(Integer::compareTo).orElseThrow());

            List<Integer> expected = new ArrayList<>(list);
            Collections.sort(expected); // stable sort

            List<Integer> results = CountingSort.sort(list);

            assertArrayEquals(expected.toArray(), results.toArray());
        }

        @Disabled // it's so slow, it took 10 minutes to complete all tests
        @ParameterizedTest(name = "sample {index}: listLength={0}, maxElementSize={1}")
        @MethodSource("randomPositiveIntsLargeSizeAndHighRange")
        @Tag("slow")
        void shouldSortListOfLargeSizeAndHighRangeSuccessfully(int listLength, int maxElementSize) {
            InstancioApi<List<Integer>> api = Instancio.ofList(Integer.class).size(listLength).generate(Select.allInts(), gen -> gen.ints().range(0, maxElementSize));
            System.out.println("Instancio Seed:" + api.asResult().getSeed());

            List<Integer> list = api.create();
            System.out.println("Greatest Element:" + list.stream().max(Integer::compareTo).orElseThrow());

            List<Integer> expected = new ArrayList<>(list);
            Collections.sort(expected); // stable sort

            List<Integer> results = CountingSort.sort(list);

            assertArrayEquals(expected.toArray(), results.toArray());
        }
    }

    @Nested
    @DisplayName("When input is invalid")
    class InvalidInput {
        //#region MethodSources
        static Stream<Arguments> negativeIntegersForInputLists() {
            return Stream.of(
                    Arguments.of(List.of(-1)),
                    Arguments.of(List.of(-1, 2, 3, 4, 5)),
                    Arguments.of(List.of(-1000, 2, 3, 4, 5)),
                    Arguments.of(List.of(Integer.MIN_VALUE)),
                    Arguments.of(List.of(-100000, -4919571, -241487157, 1000000000, -5))
            );
        }
        //#endregion

        @ParameterizedTest(name = "sample {index}: list={0}")
        @MethodSource("negativeIntegersForInputLists")
        @Tag("fast")
        void shouldThrowErrorWhenInputIncludeNegativeInteger(List<Integer> list) {
            assertThrows(IllegalArgumentException.class, () -> CountingSort.sort(list));
        }
    }
}
