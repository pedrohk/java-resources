package com.pedrohk.string;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PStringTest {

    @Test
    void testBasicProperties() {
        PString ps = new PString("Java");
        assertEquals(4, ps.length());
        assertFalse(ps.isEmpty());
        assertEquals('a', ps.charAt(1));
        assertArrayEquals(new char[]{'J', 'a', 'v', 'a'}, ps.toArray());
    }

    @Test
    void testReverse() {
        PString ps = new PString("hello");
        assertEquals(new PString("olleh"), ps.reverse());
    }

    @Test
    void testTrimAndSubstring() {
        PString ps = new PString("  data  ");
        assertEquals(new PString("data"), ps.trim());
        assertEquals(new PString("da"), ps.trim().substring(0, 2));
    }

    @Test
    void testSearchAndReplace() {
        PString ps = new PString("banana");
        assertEquals(0, ps.indexOf('b'));
        assertEquals(-1, ps.indexOf('z'));
        assertEquals(new PString("bonono"), ps.replace('a', 'o'));
    }

    @Test
    void testIteration() {
        PString ps = new PString("abc");
        List<Character> list = new ArrayList<>();
        ps.foreach(list::add);
        assertEquals(List.of('a', 'b', 'c'), list);
    }

    @Test
    void testSerialization() {
        PString ps = new PString("test");
        assertEquals("\"test\"", ps.toJson());
    }

    @Test
    void testEqualityAndHash() {
        PString s1 = new PString("java");
        PString s2 = new PString("java");
        PString s3 = new PString("kotlin");

        assertEquals(s1, s2);
        assertNotEquals(s1, s3);
        assertEquals(s1.hashCode(), s2.hashCode());
    }

    @Test
    void testExceptionHandling() {
        PString ps = new PString("short");
        assertThrows(IndexOutOfBoundsException.class, () -> ps.charAt(10));
        assertThrows(IndexOutOfBoundsException.class, () -> ps.substring(5, 2));
    }
}
