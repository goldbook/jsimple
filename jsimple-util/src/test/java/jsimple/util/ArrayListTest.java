/*
 * Copyright (c) 2012-2014, Microsoft Mobile
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 *
 * This code was adapted from Apache Harmony (http://harmony.apache.org).
 * The original Apache Harmony copyright is below.
 *
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements.  See the NOTICE file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package jsimple.util;

import jsimple.unit.UnitTest;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

public class ArrayListTest extends UnitTest {
    List alist;

    static Object[] objArray;
    {
        objArray = new Object[100];
        for (int i = 0; i < objArray.length; i++)
            objArray[i] = new Integer(i);
    }

    /**
     * @tests java.util.ArrayList#ArrayList()
     */
    //TODO: Find Support_ListTest
/*
    public void test_Constructor() {
        // Test for method java.util.ArrayList()
        new Support_ListTest("", alist).runTest();

        ArrayList subList = new ArrayList();
        for (int i = -50; i < 150; i++)
            subList.add(new Integer(i));
        new Support_ListTest("", subList.subList(50, 150)).runTest();
    }
*/

    /**
     * @tests java.util.ArrayList#ArrayList(int)
     */
    @Test public void test_ConstructorI() {
        // Test for method java.util.ArrayList(int)
        ArrayList al = new ArrayList(5);
        assertEquals("Incorrect arrayList created", 0, al.size());
        
        al = new ArrayList(0);
        assertEquals("Incorrect arrayList created", 0, al.size());
        
        try {
            al = new ArrayList(-1);
            fail("Should throw exception");
        } catch (Throwable t) {
            // Excepted
        }
    }

    /**
     * @tests java.util.ArrayList#ArrayList(java.util.Collection)
     */
    @Test public void test_ConstructorLjava_util_Collection() {
        // Test for method java.util.ArrayList(java.util.Collection)
        ArrayList al = ArrayList.create(objArray);
        assertTrue("arrayList created from collection has incorrect size", al
                .size() == objArray.length);
        for (int counter = 0; counter < objArray.length; counter++)
            assertTrue(
                    "arrayList created from collection has incorrect elements",
                    al.get(counter) == objArray[counter]);

    }

    @Test public void testConstructorWithConcurrentCollection() {
        Collection<String> collection = shrinksOnSize("A", "B", "C", "D");
        ArrayList<String> list = new ArrayList<String>(collection);
        assertFalse(list.contains(null));
    }

    /**
     * @tests java.util.ArrayList#add(int, java.lang.Object)
     */
    @Test public void test_addILjava_lang_Object() {
        // Test for method void java.util.ArrayList.add(int, java.lang.Object)
        Object o;
        alist.add(50, o = new Object());
        assertTrue("Failed to add Object", alist.get(50) == o);
        assertTrue("Failed to fix up list after insert",
                alist.get(51) == objArray[50]
                        && (alist.get(52) == objArray[51]));
        Object oldItem = alist.get(25);
        alist.add(25, null);
        assertNull("Should have returned null", alist.get(25));
        assertTrue("Should have returned the old item from slot 25", alist
                .get(26) == oldItem);
        
        alist.add(0, o = new Object());
        assertEquals("Failed to add Object", alist.get(0), o);
        assertEquals(alist.get(1), objArray[0]);
        assertEquals(alist.get(2), objArray[1]);

        oldItem = alist.get(0);
        alist.add(0, null);
        assertNull("Should have returned null", alist.get(0));
        assertEquals("Should have returned the old item from slot 0", alist
                .get(1), oldItem);

        try {
            alist.add(-1, new Object());
            fail("Should throw Index out of bounds exception");
        } catch (ProgrammerError  e) {
            // Expected
            assertTrue(e.getMessage().startsWith("Index out of bounds"));
        }

        try {
            alist.add(-1, null);
            fail("Should throw index out of bounds exception");
        } catch (ProgrammerError e) {
            // Expected
            assertTrue(e.getMessage().startsWith("Index out of bounds"));
        }

        try {
            alist.add(alist.size() + 1, new Object());
            fail("Should throw index out of bounds error");
        } catch (ProgrammerError e) {
            // Expected
            assertTrue(e.getMessage().startsWith("Index out of bounds"));
        }

        try {
            alist.add(alist.size() + 1, null);
            fail("Should throw index out of bounds error");
        } catch (ProgrammerError e) {
            // Expected
            assertTrue(e.getMessage().startsWith("Index out of bounds"));
        }
    }

    /**
     * @tests java.util.ArrayList#add(int, java.lang.Object)
     */
    @Test public void test_addILjava_lang_Object_2() {
        Object o = new Object();
        int size = alist.size();
        alist.add(size, o);
        assertEquals("Failed to add Object", alist.get(size), o);
        assertEquals(alist.get(size - 2), objArray[size - 2]);
        assertEquals(alist.get(size - 1), objArray[size - 1]);

        alist.remove(size);

        size = alist.size();
        alist.add(size, null);
        assertNull("Should have returned null", alist.get(size));
        assertEquals(alist.get(size - 2), objArray[size - 2]);
        assertEquals(alist.get(size - 1), objArray[size - 1]);
    }
    
    /**
     * @tests java.util.ArrayList#add(java.lang.Object)
     */
    @Test public void test_addLjava_lang_Object() {
        // Test for method boolean java.util.ArrayList.add(java.lang.Object)
        Object o = new Object();
        alist.add(o);
        assertTrue("Failed to add Object", alist.get(alist.size() - 1) == o);
        alist.add(null);
        assertNull("Failed to add null", alist.get(alist.size() - 1));
    }

    /**
     * @tests java.util.ArrayList#addAll(int, java.util.Collection)
     */
    @Test public void test_addAllILjava_util_Collection() {
        // Test for method boolean java.util.ArrayList.addAll(int,
        // java.util.Collection)
        alist.addAll(50, alist);
        assertEquals("Returned incorrect size after adding to existing list",
                200, alist.size());
        for (int i = 0; i < 50; i++)
            assertTrue("Manipulated elements < index",
                    alist.get(i) == objArray[i]);
        for (int i = 0; i >= 50 && (i < 150); i++)
            assertTrue("Failed to ad elements properly",
                    alist.get(i) == objArray[i - 50]);
        for (int i = 0; i >= 150 && (i < 200); i++)
            assertTrue("Failed to ad elements properly",
                    alist.get(i) == objArray[i - 100]);
        ArrayList listWithNulls = new ArrayList();
        listWithNulls.add(null);
        listWithNulls.add(null);
        listWithNulls.add("yoink");
        listWithNulls.add("kazoo");
        listWithNulls.add(null);
        alist.addAll(100, listWithNulls);
        assertTrue("Incorrect size: " + alist.size(), alist.size() == 205);
        assertNull("Item at slot 100 should be null", alist.get(100));
        assertNull("Item at slot 101 should be null", alist.get(101));
        assertEquals("Item at slot 102 should be 'yoink'", "yoink", alist
                .get(102));
        assertEquals("Item at slot 103 should be 'kazoo'", "kazoo", alist
                .get(103));
        assertNull("Item at slot 104 should be null", alist.get(104));
        alist.addAll(205, listWithNulls);
        assertTrue("Incorrect size2: " + alist.size(), alist.size() == 210);
    }

    /**
     * @tests java.util.ArrayList#addAll(int, java.util.Collection)
     */
    @SuppressWarnings("unchecked")
    @Test public void test_addAllILjava_util_Collection_2() {
        // Regression for HARMONY-467
        ArrayList obj = new ArrayList();
        try {
            obj.addAll((int) -1, (Collection) null);
            fail("IndexOutOfBoundsException expected");
        } catch (ProgrammerError e) {
            // Expected
            assertTrue(e.getMessage().startsWith("Index out of bounds"));
        }

        // Regression for HARMONY-5705
        String[] data = new String[] { "1", "2", "3", "4", "5", "6", "7", "8" };
        ArrayList list1 = new ArrayList();
        ArrayList list2 = new ArrayList();
        for (String d : data) {
            list1.add(d);
            list2.add(d);
            list2.add(d);
        }
        while (list1.size() > 0)
            list1.remove(0);
        list1.addAll(list2);
        assertTrue("The object list is not the same as original list", list1
                .containsAll(list2)
                && list2.containsAll(list1));

        obj = new ArrayList();
        for (int i = 0; i < 100; i++) {
            if (list1.size() > 0) {
                obj.removeAll(list1);
                obj.addAll(list1);
            }
        }
        assertTrue("The object list is not the same as original list", obj
                .containsAll(list1)
                && list1.containsAll(obj));

        // Regression for Harmony-5799
        list1 = new ArrayList();
        list2 = new ArrayList();
        int location = 2;

        String[] strings = { "0", "1", "2", "3", "4", "5", "6" };
        int[] integers = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        for (int i = 0; i < 7; i++) {
            list1.add(strings[i]);
        }
        for (int i = 0; i < 10; i++) {
            list2.add(integers[i]);
        }
        list1.remove(location);
        list1.addAll(location, list2);

        // Inserted elements should be equal to integers array
        for (int i = 0; i < integers.length; i++) {
            assertEquals(integers[i], list1.get(location + i));
        }
        // Elements after inserted location should
        // be equals to related elements in strings array
        for (int i = location + 1; i < strings.length; i++) {
            assertEquals(strings[i], list1.get(i + integers.length - 1));
        }
    }
    
    /**
     * @tests java.util.ArrayList#addAll(int, java.util.Collection)
     */
    @Test public void test_addAllILjava_util_Collection_3() {
        ArrayList obj = new ArrayList();
        obj.addAll(0, obj);
        obj.addAll(obj.size(), obj);
        try {
            obj.addAll(-1, obj);
            fail("Should throw IndexOutOfBoundsException");
        } catch (ProgrammerError e) {
            // Expected
            assertTrue(e.getMessage().startsWith("Index out of bounds"));
        }

        try {
            obj.addAll(obj.size() + 1, obj);
            fail("Should throw index out of bounds exception");
        } catch (ProgrammerError e) {
            // Expected
            assertTrue(e.getMessage().startsWith("Index out of bounds"));
        }

        try {
            obj.addAll(0, null);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            // Excepted
        }

        try {
            obj.addAll(obj.size() + 1, null);
            fail("Should throw programmer error");
        } catch (ProgrammerError e) {
            // Expected
            assertTrue(e.getMessage().startsWith("Index out of bounds"));
        }

        try {
            obj.addAll((int) -1, (Collection) null);
            fail("Endex out of bounds error expected");
        } catch (ProgrammerError e) {
            // Expected
            assertTrue(e.getMessage().startsWith("Index out of bounds"));
        }
    }

    @Test public void test_addAllCollectionOfQextendsE() {
        // Regression for HARMONY-539
        // https://issues.apache.org/jira/browse/HARMONY-539
        ArrayList<String> alist = new ArrayList<String>();
        ArrayList<String> blist = new ArrayList<String>();
        alist.add("a");
        alist.add("b");
        blist.add("c");
        blist.add("d");
        blist.remove(0);
        blist.addAll(0, alist);
        assertEquals("a", blist.get(0));
        assertEquals("b", blist.get(1));
        assertEquals("d", blist.get(2));
    }

    /**
     * @tests java.util.ArrayList#addAll(java.util.Collection)
     */
    @Test public void test_addAllLjava_util_Collection() {
        // Test for method boolean
        // java.util.ArrayList.addAll(java.util.Collection)
        List l = new ArrayList();
        l.addAll(alist);
        for (int i = 0; i < alist.size(); i++)
            assertTrue("Failed to add elements properly", l.get(i).equals(
                    alist.get(i)));
        alist.addAll(alist);
        assertEquals("Returned incorrect size after adding to existing list",
                200, alist.size());
        for (int i = 0; i < 100; i++) {
            assertTrue("Added to list in incorrect order", alist.get(i).equals(
                    l.get(i)));
            assertTrue("Failed to add to existing list", alist.get(i + 100)
                    .equals(l.get(i)));
        }
        Set setWithNulls = new HashSet();
        setWithNulls.add(null);
        setWithNulls.add(null);
        setWithNulls.add("yoink");
        setWithNulls.add("kazoo");
        setWithNulls.add(null);
        alist.addAll(100, setWithNulls);
        Iterator i = setWithNulls.iterator();
        assertTrue("Item at slot 100 is wrong: " + alist.get(100), alist
                .get(100) == i.next());
        assertTrue("Item at slot 101 is wrong: " + alist.get(101), alist
                .get(101) == i.next());
        assertTrue("Item at slot 103 is wrong: " + alist.get(102), alist
                .get(102) == i.next());

        try {
            alist.addAll(null);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            // Excepted
        }
        
        // Regression test for Harmony-3481
        ArrayList<Integer> originalList = new ArrayList<Integer>(12);
        for (int j = 0; j < 12; j++) {
            originalList.add(j);
        }

        originalList.remove(0);
        originalList.remove(0);

        ArrayList<Integer> additionalList = new ArrayList<Integer>(11);
        for (int j = 0; j < 11; j++) {
            additionalList.add(j);
        }
        assertTrue(originalList.addAll(additionalList));
        assertEquals(21, originalList.size());

    }

    @Test public void test_ArrayList_addAll_scenario1() {
        ArrayList arrayListA = new ArrayList();
        arrayListA.add(1);
        ArrayList arrayListB = new ArrayList();
        arrayListB.add(1);
        arrayListA.addAll(1, arrayListB);
        int size = arrayListA.size();
        assertEquals(2, size);
        for (int index = 0; index < size; index++) {
            assertEquals(1, arrayListA.get(index));
        }
    }

    @Test public void test_ArrayList_addAll_scenario2() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(1);
        arrayList.addAll(1, arrayList);
        int size = arrayList.size();
        assertEquals(2, size);
        for (int index = 0; index < size; index++) {
            assertEquals(1, arrayList.get(index));
        }
    }
        
    // Regression test for HARMONY-5839
    @Test public void testaddAllHarmony5839() {
        Collection<String> coll = ArrayList.create("1", "2");
        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add(0, "b");
        list.add(0, "c");
        list.add(0, "d");
        list.add(0, "e");
        list.add(0, "f");
        list.add(0, "g");
        list.add(0, "h");
        list.add(0, "i");

        list.addAll(6, coll);

        assertEquals(11, list.size());
        assertFalse(list.contains(null));
    }

    /**
     * @tests java.util.ArrayList#clear()
     */
    @Test public void test_clear() {
        // Test for method void java.util.ArrayList.clear()
        alist.clear();
        assertEquals("List did not clear", 0, alist.size());
        alist.add(null);
        alist.add(null);
        alist.add(null);
        alist.add("bam");
        alist.clear();
        assertEquals("List with nulls did not clear", 0, alist.size());
        /*
         * for (int i = 0; i < alist.size(); i++) assertNull("Failed to clear
         * list", alist.get(i));
         */

    }

    /**
     * @tests java.util.ArrayList#clone()
     */
    @Test public void test_clone() {
        // Test for method java.lang.Object java.util.ArrayList.clone()
        ArrayList x = (ArrayList) (((ArrayList) (alist)).clone());
        assertTrue("Cloned list was inequal to original", x.equals(alist));
        for (int i = 0; i < alist.size(); i++)
            assertTrue("Cloned list contains incorrect elements",
                    alist.get(i) == x.get(i));

        alist.add(null);
        alist.add(25, null);
        x = (ArrayList) (((ArrayList) (alist)).clone());
        assertTrue("nulls test - Cloned list was inequal to original", x
                .equals(alist));
        for (int i = 0; i < alist.size(); i++)
            assertTrue("nulls test - Cloned list contains incorrect elements",
                    alist.get(i) == x.get(i));

    }

    /**
     * @tests java.util.ArrayList#contains(java.lang.Object)
     */
    @Test public void test_containsLjava_lang_Object() {
        // Test for method boolean
        // java.util.ArrayList.contains(java.lang.Object)
        assertTrue("Returned false for valid element", alist
                .contains(objArray[99]));
        assertTrue("Returned false for equal element", alist
                .contains(new Integer(8)));
        assertTrue("Returned true for invalid element", !alist
                .contains(new Object()));
        assertTrue("Returned true for null but should have returned false",
                !alist.contains(null));
        alist.add(null);
        assertTrue("Returned false for null but should have returned true",
                alist.contains(null));
    }

    /**
     * @tests java.util.ArrayList#ensureCapacity(int)
     */
    @Test public void test_ensureCapacityI() {
        // Test for method void java.util.ArrayList.ensureCapacity(int)
        // TODO : There is no good way to test this as it only really impacts on
        // the private implementation.

        Object testObject = new Object();
        int capacity = 20;
        ArrayList al = new ArrayList(capacity);
        int i;
        for (i = 0; i < capacity / 2; i++) {
            al.add(i, new Object());
        }
        al.add(i, testObject);
        int location = al.indexOf(testObject);
        al.ensureCapacity(capacity);
        assertTrue("EnsureCapacity moved objects around in array1.",
                location == al.indexOf(testObject));
        al.remove(0);
        al.ensureCapacity(capacity);
        assertTrue("EnsureCapacity moved objects around in array2.",
                --location == al.indexOf(testObject));
        al.ensureCapacity(capacity + 2);
        assertTrue("EnsureCapacity did not change location.", location == al
                .indexOf(testObject));
    }

    /**
     * @tests java.util.ArrayList#get(int)
     */
    @Test public void test_getI() {
        // Test for method java.lang.Object java.util.ArrayList.get(int)
        assertTrue("Returned incorrect element", alist.get(22) == objArray[22]);
        try {
            alist.get(8765);
            fail("Failed to throw expected exception for index > size");
        } catch (ProgrammerError e) {
            // Expected
            assertTrue(e.getMessage().startsWith("Index out of bounds"));
        }
    }

    /**
     * @tests java.util.ArrayList#indexOf(java.lang.Object)
     */
    @Test public void test_indexOfLjava_lang_Object() {
        // Test for method int java.util.ArrayList.indexOf(java.lang.Object)
        assertEquals("Returned incorrect index", 87, alist
                .indexOf(objArray[87]));
        assertEquals("Returned index for invalid Object", -1, alist
                .indexOf(new Object()));
        alist.add(25, null);
        alist.add(50, null);
        assertTrue("Wrong indexOf for null.  Wanted 25 got: "
                + alist.indexOf(null), alist.indexOf(null) == 25);
    }

    /**
     * @tests java.util.ArrayList#isEmpty()
     */
    @Test public void test_isEmpty() {
        // Test for method boolean java.util.ArrayList.isEmpty()
        assertTrue("isEmpty returned false for new list", new ArrayList()
                .isEmpty());
        assertTrue("Returned true for existing list with elements", !alist
                .isEmpty());
    }

    /**
     * @tests java.util.ArrayList#lastIndexOf(java.lang.Object)
     */
    @Test public void test_lastIndexOfLjava_lang_Object() {
        // Test for method int java.util.ArrayList.lastIndexOf(java.lang.Object)
        alist.add(new Integer(99));
        assertEquals("Returned incorrect index", 100, alist
                .lastIndexOf(objArray[99]));
        assertEquals("Returned index for invalid Object", -1, alist
                .lastIndexOf(new Object()));
        alist.add(25, null);
        alist.add(50, null);
        assertTrue("Wrong lastIndexOf for null.  Wanted 50 got: "
                + alist.lastIndexOf(null), alist.lastIndexOf(null) == 50);
    }

    /**
     * @tests java.util.ArrayList#remove(int)
     */
    @Test public void test_removeI() {
        // Test for method java.lang.Object java.util.ArrayList.remove(int)
        alist.remove(10);
        assertEquals("Failed to remove element", -1, alist
                .indexOf(objArray[10]));
        try {
            alist.remove(999);
            fail("Failed to throw exception when index out of range");
        } catch (ProgrammerError e) {
            // Expected
            assertTrue(e.getMessage().startsWith("Index out of bounds"));
        }

        ArrayList myList = (ArrayList) (((ArrayList) (alist)).clone());
        alist.add(25, null);
        alist.add(50, null);
        alist.remove(50);
        alist.remove(25);
        assertTrue("Removing nulls did not work", alist.equals(myList));

        List list = ArrayList.create("a", "b", "c", "d", "e", "f", "g");
        assertTrue("Removed wrong element 1", list.remove(0) == "a");
        assertTrue("Removed wrong element 2", list.remove(4) == "f");
        assertTrue("Removed wrong element 3", list.equals(ArrayList.create("b", "c", "d", "e", "g" )));

        List l = new ArrayList(0);
        l.add(new Object());
        l.add(new Object());
        l.remove(0);
        l.remove(0);
        try {
            l.remove(-1);
            fail("-1 should cause exception");
        } catch (ProgrammerError e) {
            // Expected
            assertTrue(e.getMessage().startsWith("Index out of bounds"));
        }
        try {
            l.remove(0);
            fail("0 should case exception");
        } catch (ProgrammerError e) {
            // Expected
            assertTrue(e.getMessage().startsWith("Index out of bounds"));
        }
    }

    /**
     * @tests java.util.ArrayList#set(int, java.lang.Object)
     */
    @Test public void test_setILjava_lang_Object() {
        // Test for method java.lang.Object java.util.ArrayList.set(int,
        // java.lang.Object)
        Object obj;
        alist.set(65, obj = new Object());
        assertTrue("Failed to set object", alist.get(65) == obj);
        alist.set(50, null);
        assertNull("Setting to null did not work", alist.get(50));
        assertTrue("Setting increased the list's size to: " + alist.size(),
                alist.size() == 100);
        
        obj = new Object();
        alist.set(0, obj);
        assertTrue("Failed to set object", alist.get(0) == obj);

        try {
            alist.set(-1, obj);
            fail("Should throw index out of bounds exception");
        } catch (ProgrammerError e) {
            // Expected
            assertTrue(e.getMessage().startsWith("Index out of bounds"));
        }

        try {
            alist.set(alist.size(), obj);
            fail("Should throw index out of bounds error");
        } catch (ProgrammerError e) {
            // Expected
            assertTrue(e.getMessage().startsWith("Index out of bounds"));
        }

        try {
            alist.set(-1, null);
            fail("Should throw IndexOutOfBoundsException");
        } catch (ProgrammerError e) {
            // Expected
            assertTrue(e.getMessage().startsWith("Index out of bounds"));
        }

        try {
            alist.set(alist.size(), null);
            fail("Should throw IndexOutOfBoundsException");
        } catch (ProgrammerError e) {
            // Expected
            assertTrue(e.getMessage().startsWith("Index out of bounds"));
        }
    }

    /**
     * @tests java.util.ArrayList#size()
     */
    @Test public void test_size() {
        // Test for method int java.util.ArrayList.size()
        assertEquals("Returned incorrect size for exiting list", 100, alist
                .size());
        assertEquals("Returned incorrect size for new list", 0, new ArrayList()
                .size());
    }

    /**
     * @tests java.util.AbstractCollection#toString()
     */
    @Test public void test_toString() {
        ArrayList l = new ArrayList(1);
        l.add(l);
        String result = l.toString();
        assertTrue("should contain self ref", result.indexOf("(this") > -1);
    }

    /**
     * @tests java.util.ArrayList#toArray()
     */
    @Test public void test_toArray() {
        // Test for method java.lang.Object [] java.util.ArrayList.toArray()
        alist.set(25, null);
        alist.set(75, null);
        Object[] obj = alist.toArray();
        assertEquals("Returned array of incorrect size", objArray.length,
                obj.length);

        for (int i = 0; i < obj.length; i++) {
            if ((i == 25) || (i == 75))
                assertNull("Should be null at: " + i + " but instead got: "
                        + obj[i], obj[i]);
            else
                assertTrue("Returned incorrect array: " + i,
                        obj[i] == objArray[i]);
        }

    }

    /**
     * @tests java.util.ArrayList#toArray(java.lang.Object[])
     */
    @Test public void test_toArray$Ljava_lang_Object() {
        // Test for method java.lang.Object []
        // java.util.ArrayList.toArray(java.lang.Object [])
        alist.set(25, null);
        alist.set(75, null);

        Integer[] argArray = new Integer[alist.size()];
        alist.toArray(argArray);

        argArray = new Integer[1000];
        alist.toArray(argArray);
        assertNull("Failed to set first extra element to null", argArray[alist.size()]);

        for (int i = 0; i < 100; i++) {
            if ((i == 25) || (i == 75))
                assertNull("Should be null: " + i, argArray[i]);
            else
                assertTrue("Returned incorrect array: " + i, argArray[i] == objArray[i]);
        }
    }

    /**
     * @tests java.util.ArrayList#trimToSize()
     */
    @Test public void test_trimToSize() {
        // Test for method void java.util.ArrayList.trimToSize()
        for (int i = 99; i > 24; i--)
            alist.remove(i);
        ((ArrayList) alist).trimToSize();
        assertEquals("Returned incorrect size after trim", 25, alist.size());
        for (int i = 0; i < alist.size(); i++)
            assertTrue("Trimmed list contained incorrect elements", alist
                    .get(i) == objArray[i]);
        ArrayList v = new ArrayList();
        v.add("a");
        ArrayList al = new ArrayList(v);
        Iterator it = al.iterator();
        al.trimToSize();
        try {
            it.next();
            fail("should throw a ConcurrentModificationException");
        } catch (ProgrammerError ioobe) {
            // expected
        }
    }

    /**
     * @test java.util.ArrayList#addAll(int, Collection)
     */
    @Test public void test_addAll() {
        ArrayList list = new ArrayList();
        list.add("one");
        list.add("two");
        assertEquals(2, list.size());

        list.remove(0);
        assertEquals(1, list.size());

        ArrayList collection = new ArrayList();
        collection.add("1");
        collection.add("2");
        collection.add("3");
        assertEquals(3, collection.size());

        list.addAll(0, collection);
        assertEquals(4, list.size());

        list.remove(0);
        list.remove(0);
        assertEquals(2, list.size());

        collection.add("4");
        collection.add("5");
        collection.add("6");
        collection.add("7");
        collection.add("8");
        collection.add("9");
        collection.add("10");
        collection.add("11");
        collection.add("12");

        assertEquals(12, collection.size());

        list.addAll(0, collection);
        assertEquals(14, list.size());
    }

    @Test public void testAddAllWithConcurrentCollection() {
        ArrayList<String> list = new ArrayList<String>();
        list.addAll(shrinksOnSize("A", "B", "C", "D"));
        assertFalse(list.contains(null));
    }

    @Test public void testAddAllAtPositionWithConcurrentCollection() {
        ArrayList<String> list = ArrayList.create("A", "B", "C", "D");

        list.addAll(3, shrinksOnSize("E", "F", "G", "H"));
        assertFalse(list.contains(null));
    }

    /**
     * Sets up the fixture, for example, open a network connection. This method
     * is called before a test is executed.
     */
    @Before public void setUp() {
        alist = new ArrayList();
        for (int i = 0; i < objArray.length; i++)
            alist.add(objArray[i]);
    }

    /**
     * Returns a collection that emulates another thread calling remove() each
     * time the current thread calls size().
     */
    private <T> Collection<T> shrinksOnSize(T... elements) {
        ArrayList<T> ts = ArrayList.create(elements);
        return new HashSet<T>(ts) {
            boolean shrink = true;

            @Override
            public int size() {
                int result = super.size();
                if (shrink) {
                    Iterator<T> i = iterator();
                    i.next();
                    i.remove();
                }
                return result;
            }

            @Override
            public Object[] toArray() {
                shrink = false;
                return super.toArray();
            }
        };
    }
}