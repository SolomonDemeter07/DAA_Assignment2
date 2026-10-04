# In-Memory Workload Engine (Assignment 2)

## Project Overview
This project features custom implementations of fundamental data structures (`DynamicArray`, `MyLinkedList`, and `MinHeap`)
built strictly from scratch for primitive `int` values. The engine is benchmarked across four distinct workloads to analyze
real-world performance, cache locality, and physical operations.

## How to Build the Project
1. Open the project folder in IntelliJ IDEA.
2. Allow Maven to download dependencies (JUnit 5).
3. Select **Build > Build Project** (`Ctrl + F9`) from the top menu.

## How to Run the Benchmark
1. Navigate to `src/main/java/org/example/Benchmark.java`.
2. Click the green **Run** arrow next to the `main` method.
3. The system will execute all workloads and automatically generate `results/results.csv` in the root directory.

## How to Run the Tests
1. Navigate to `src/test/java/org/example/AlgorithmTest.java`.
2. Click the green **Run** arrow next to the class declaration.
3. The JUnit test suite will verify correctness, edge cases, and the MinHeap property.