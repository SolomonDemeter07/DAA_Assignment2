package org.example;

public class DynamicArray {
    private int[] data;
    private int size;

    public DynamicArray() {
        data = new int[10];
        size = 0;
    }

    public void add(int x, Metrics metrics) {
        if (size == data.length) {
            resize(metrics);
        }
        data[size++] = x;
        metrics.addMove();
    }

    public void add(int index, int x, Metrics metrics) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (size == data.length) {
            resize(metrics);
        }
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
            metrics.addMove();
            metrics.addStep();
        }
        data[index] = x;
        size++;
        metrics.addMove();
    }

    public int remove(int index, Metrics metrics) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        int removed = data[index];
        metrics.addStep();
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
            metrics.addMove();
            metrics.addStep();
        }
        size--;
        return removed;
    }

    public int get(int index, Metrics metrics) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        metrics.addStep();
        return data[index];
    }

    public boolean contains(int x, Metrics metrics) {
        for (int i = 0; i < size; i++) {
            metrics.addStep();
            metrics.addComparison();
            if (data[i] == x) {
                return true;
            }
        }
        return false;
    }

    private void resize(Metrics metrics) {
        int[] newData = new int[data.length * 2];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
            metrics.addMove();
            metrics.addStep();
        }
        data = newData;
    }

    public int size() {
        return size;
    }
}