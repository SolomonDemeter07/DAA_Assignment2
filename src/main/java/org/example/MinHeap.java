package org.example;

public class MinHeap {
    private int[] data;
    private int size;

    public MinHeap() {
        data = new int[10];
        size = 0;
    }

    public void insert(int x, Metrics metrics) {
        if (size == data.length) {
            resize(metrics);
        }
        data[size] = x;
        metrics.addMove();
        bubbleUp(size, metrics);
        size++;
    }

    public int peekMin(Metrics metrics) {
        if (size == 0) throw new IllegalStateException();
        metrics.addStep();
        return data[0];
    }

    public int extractMin(Metrics metrics) {
        if (size == 0) throw new IllegalStateException();
        int min = data[0];
        metrics.addStep();
        data[0] = data[size - 1];
        metrics.addStep();
        metrics.addMove();
        size--;
        bubbleDown(0, metrics);
        return min;
    }

    private void bubbleUp(int index, Metrics metrics) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            metrics.addStep();
            metrics.addStep();
            metrics.addComparison();
            if (data[index] >= data[parent]) {
                break;
            }
            int temp = data[index];
            data[index] = data[parent];
            data[parent] = temp;
            metrics.addMove();
            metrics.addMove();
            metrics.addMove();
            index = parent;
        }
    }

    private void bubbleDown(int index, Metrics metrics) {
        while (index * 2 + 1 < size) {
            int leftChild = index * 2 + 1;
            int rightChild = index * 2 + 2;
            int smallest = leftChild;

            metrics.addStep();
            metrics.addStep();
            metrics.addComparison();
            if (rightChild < size && data[rightChild] < data[leftChild]) {
                smallest = rightChild;
            }

            metrics.addStep();
            metrics.addStep();
            metrics.addComparison();
            if (data[index] <= data[smallest]) {
                break;
            }

            int temp = data[index];
            data[index] = data[smallest];
            data[smallest] = temp;
            metrics.addMove();
            metrics.addMove();
            metrics.addMove();
            index = smallest;
        }
    }

    private void resize(Metrics metrics) {
        int[] newData = new int[data.length * 2];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
            metrics.addStep();
            metrics.addMove();
        }
        data = newData;
    }

    public int size() {
        return size;
    }
}