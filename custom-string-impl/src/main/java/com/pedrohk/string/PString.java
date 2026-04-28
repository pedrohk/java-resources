package com.pedrohk.string;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public final class PString implements Iterable<Character> {
    private final char[] value;

    public PString(String original) {
        this.value = original != null ? original.toCharArray() : new char[0];
    }

    private PString(char[] value) {
        this.value = value;
    }

    public int length() {
        return value.length;
    }

    public boolean isEmpty() {
        return value.length == 0;
    }

    public char charAt(int index) {
        if (index < 0 || index >= value.length) throw new IndexOutOfBoundsException();
        return value[index];
    }

    public char[] toArray() {
        char[] copy = new char[value.length];
        System.arraycopy(value, 0, copy, 0, value.length);
        return copy;
    }

    public PString reverse() {
        char[] reversed = new char[value.length];
        for (int i = 0; i < value.length; i++) {
            reversed[i] = value[value.length - 1 - i];
        }
        return new PString(reversed);
    }

    public PString substring(int start, int end) {
        if (start < 0 || end > value.length || start > end) throw new IndexOutOfBoundsException();
        char[] sub = new char[end - start];
        System.arraycopy(value, start, sub, 0, end - start);
        return new PString(sub);
    }

    public PString trim() {
        int start = 0;
        int end = value.length - 1;
        while (start <= end && value[start] <= ' ') start++;
        while (end >= start && value[end] <= ' ') end--;
        return substring(start, end + 1);
    }

    public int indexOf(char target) {
        for (int i = 0; i < value.length; i++) {
            if (value[i] == target) return i;
        }
        return -1;
    }

    public PString replace(char target, char replacement) {
        char[] replaced = new char[value.length];
        for (int i = 0; i < value.length; i++) {
            replaced[i] = (value[i] == target) ? replacement : value[i];
        }
        return new PString(replaced);
    }

    public String toJson() {
        return "\"" + new String(value) + "\"";
    }


    public void foreach(Consumer<? super Character> action) {
        for (char c : value) action.accept(c);
    }

    @Override
    public Iterator<Character> iterator() {
        return new Iterator<>() {
            private int cursor = 0;

            @Override
            public boolean hasNext() {
                return cursor < value.length;
            }

            @Override
            public Character next() {
                if (!hasNext()) throw new NoSuchElementException();
                return value[cursor++];
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PString other)) return false;
        if (this.length() != other.length()) return false;
        for (int i = 0; i < value.length; i++) {
            if (this.value[i] != other.value[i]) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int h = 0;
        for (char c : value) h = 31 * h + c;
        return h;
    }

    @Override
    public String toString() {
        return new String(value);
    }
}
