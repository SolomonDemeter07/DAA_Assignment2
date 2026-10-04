package org.example;

public class MyLinkedList {
    private Node head;
    private Node tail;
    private int size;

    private static class Node {
        int data;
        Node next;
        Node(int data) { this.data = data; }
    }

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public void add(int x, Metrics metrics) {
        Node newNode = new Node(x);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
        metrics.addMove();
    }

    public void add(int index, int x, Metrics metrics) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        Node newNode = new Node(x);
        if (index == 0) {
            newNode.next = head;
            head = newNode;
            if (tail == null) tail = newNode;
            metrics.addMove();
        } else if (index == size) {
            add(x, metrics);
            return;
        } else {
            Node current = head;
            metrics.addStep();
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
                metrics.addStep();
            }
            newNode.next = current.next;
            current.next = newNode;
            metrics.addMove();
            metrics.addMove();
        }
        size++;
    }

    public int remove(int index, Metrics metrics) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        int removedValue;
        if (index == 0) {
            removedValue = head.data;
            head = head.next;
            if (head == null) tail = null;
            metrics.addMove();
        } else {
            Node current = head;
            metrics.addStep();
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
                metrics.addStep();
            }
            removedValue = current.next.data;
            current.next = current.next.next;
            if (current.next == null) tail = current;
            metrics.addMove();
        }
        size--;
        return removedValue;
    }

    public int get(int index, Metrics metrics) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node current = head;
        metrics.addStep();
        for (int i = 0; i < index; i++) {
            current = current.next;
            metrics.addStep();
        }
        return current.data;
    }

    public boolean contains(int x, Metrics metrics) {
        Node current = head;
        metrics.addStep();
        while (current != null) {
            metrics.addComparison();
            if (current.data == x) {
                return true;
            }
            current = current.next;
            metrics.addStep();
        }
        return false;
    }

    public int size() {
        return size;
    }
}