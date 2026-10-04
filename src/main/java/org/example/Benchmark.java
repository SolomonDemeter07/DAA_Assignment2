package org.example;

import java.io.FileWriter;
import java.util.Arrays;
import java.util.Random;

public class Benchmark {
    private static final int[] SIZES = {100, 1000, 10000, 100000};
    private static final int W1_OPS = 10000;
    private static final int W2_OPS = 1000;
    private static final int W3_OPS = 1000;
    private static final int RUNS = 5;

    public static void main(String[] args) throws Exception {
        try (FileWriter writer = new FileWriter("results.csv")) {
            writer.write("workload,variant,structure,n,time_ms,steps,moves,comparisons\n");
            for (int n : SIZES) {
                runW1(writer, n);
                runW2(writer, n);
                runW3(writer, n, "head");
                runW3(writer, n, "middle");
                runW4(writer, n);
            }
        }
    }

    private static void runW1(FileWriter writer, int n) throws Exception {
        measure(writer, "W1", "-", "DynamicArray", n, () -> {
            DynamicArray arr = new DynamicArray();
            Random r = new Random(42);
            Metrics m = new Metrics();
            for (int i = 0; i < n; i++) arr.add(r.nextInt(), m);
            m.reset();
            for (int i = 0; i < W1_OPS; i++) arr.get(r.nextInt(n), m);
            return m;
        });
        measure(writer, "W1", "-", "MyLinkedList", n, () -> {
            MyLinkedList list = new MyLinkedList();
            Random r = new Random(42);
            Metrics m = new Metrics();
            for (int i = 0; i < n; i++) list.add(r.nextInt(), m);
            m.reset();
            for (int i = 0; i < W1_OPS; i++) list.get(r.nextInt(n), m);
            return m;
        });
    }

    private static void runW2(FileWriter writer, int n) throws Exception {
        measure(writer, "W2", "-", "DynamicArray", n, () -> {
            DynamicArray arr = new DynamicArray();
            Random r = new Random(42);
            Metrics m = new Metrics();
            int[] data = new int[n];
            for (int i = 0; i < n; i++) {
                data[i] = r.nextInt();
                arr.add(data[i], m);
            }
            m.reset();
            for (int i = 0; i < W2_OPS; i++) {
                if (i % 2 == 0) arr.contains(data[r.nextInt(n)], m);
                else arr.contains(r.nextInt(), m);
            }
            return m;
        });
        measure(writer, "W2", "-", "MyLinkedList", n, () -> {
            MyLinkedList list = new MyLinkedList();
            Random r = new Random(42);
            Metrics m = new Metrics();
            int[] data = new int[n];
            for (int i = 0; i < n; i++) {
                data[i] = r.nextInt();
                list.add(data[i], m);
            }
            m.reset();
            for (int i = 0; i < W2_OPS; i++) {
                if (i % 2 == 0) list.contains(data[r.nextInt(n)], m);
                else list.contains(r.nextInt(), m);
            }
            return m;
        });
    }

    private static void runW3(FileWriter writer, int n, String variant) throws Exception {
        measure(writer, "W3", variant, "DynamicArray", n, () -> {
            DynamicArray arr = new DynamicArray();
            Random r = new Random(42);
            Metrics m = new Metrics();
            for (int i = 0; i < n; i++) arr.add(r.nextInt(), m);
            m.reset();
            int idx = variant.equals("head") ? 0 : n / 2;
            for (int i = 0; i < W3_OPS; i++) arr.add(idx, r.nextInt(), m);
            for (int i = 0; i < W3_OPS; i++) arr.remove(idx, m);
            return m;
        });
        measure(writer, "W3", variant, "MyLinkedList", n, () -> {
            MyLinkedList list = new MyLinkedList();
            Random r = new Random(42);
            Metrics m = new Metrics();
            for (int i = 0; i < n; i++) list.add(r.nextInt(), m);
            m.reset();
            int idx = variant.equals("head") ? 0 : n / 2;
            for (int i = 0; i < W3_OPS; i++) list.add(idx, r.nextInt(), m);
            for (int i = 0; i < W3_OPS; i++) list.remove(idx, m);
            return m;
        });
    }

    private static void runW4(FileWriter writer, int n) throws Exception {
        measure(writer, "W4", "-", "MinHeap", n, () -> {
            MinHeap heap = new MinHeap();
            Random r = new Random(42);
            Metrics m = new Metrics();
            for (int i = 0; i < n; i++) heap.insert(r.nextInt(), m);
            m.reset();
            for (int i = 0; i < n; i++) heap.extractMin(m);
            return m;
        });
    }

    private static void measure(FileWriter writer, String workload, String variant, String structure, int n, Task task) throws Exception {
        Result[] results = new Result[RUNS];
        for (int i = 0; i < RUNS; i++) {
            long start = System.nanoTime();
            Metrics m = task.run();
            long end = System.nanoTime();
            results[i] = new Result((end - start) / 1_000_000.0, m.getSteps(), m.getMoves(), m.getComparisons());
        }
        Arrays.sort(results, (a, b) -> Double.compare(a.time, b.time));
        Result median = results[RUNS / 2];
        writer.write(workload + "," + variant + "," + structure + "," + n + "," +
                median.time + "," + median.steps + "," + median.moves + "," + median.comps + "\n");
    }

    interface Task { Metrics run(); }
    static class Result {
        double time; long steps, moves, comps;
        Result(double t, long s, long m, long c) { time = t; steps = s; moves = m; comps = c; }
    }
}