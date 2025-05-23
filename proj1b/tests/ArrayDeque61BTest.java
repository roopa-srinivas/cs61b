import deque.ArrayDeque61B;

import deque.Deque61B;
import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

     @Test
     @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
     void noNonTrivialFields() {
         List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                 .toList();

         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
     }

     @Test
    public void addFirstAndAddLastTest() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
        ad1.addLast(0);   // [0]
        ad1.addLast(1);   // [0, 1]
        ad1.addFirst(-1); // [-1, 0, 1]
        ad1.addLast(2);   // [-1, 0, 1, 2]
        ad1.addFirst(-2); // [-2, -1, 0, 1, 2]

        assertThat(ad1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
    }

     @Test
     void testGet() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();
        ad1.addLast(0);
        ad1.addLast(1);
        ad1.addFirst(-1);
        ad1.addLast(2);
        ad1.addFirst(-2);
        assertThat(ad1.get(3)).isEqualTo(1);
        assertThat(ad1.get(28723)).isEqualTo(null);
        assertThat(ad1.get(-3)).isEqualTo(null);
    }

    @Test
    public void isEmptyTest() {
        Deque61B<Integer> adEmpty = new ArrayDeque61B<>();
        assertThat(adEmpty.isEmpty()).isTrue();

        Deque61B<Integer> adNonEmpty = new ArrayDeque61B<>();
        adNonEmpty.addLast(0);
        adNonEmpty.addLast(1);
        adNonEmpty.addFirst(-1);
        adNonEmpty.addLast(2);
        adNonEmpty.addFirst(-2);
        assertThat(adNonEmpty.isEmpty()).isFalse();
    }

    @Test
    public void sizeTest() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();
        assertThat(ad1.size()).isEqualTo(0);
        ad1.addLast(0);
        ad1.addLast(1);
        ad1.addFirst(-1);
        ad1.addLast(2);
        ad1.addFirst(-2);
        assertThat(ad1.size()).isEqualTo(5);
        while (!ad1.isEmpty()) {
            ad1.removeFirst();
        }
        assertThat(ad1.size()).isEqualTo(0);
    }

    @Test
    public void testRemoveFirstAndRemoveLast() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();
        ad1.addLast(0);
        ad1.addLast(1);
        ad1.addFirst(-1);
        ad1.addLast(2);
        ad1.addFirst(-2);
        while (!ad1.isEmpty()) {
            ad1.removeFirst();
        }
        assertThat(ad1.isEmpty()).isTrue();
        ad1.addFirst(3);
        assertThat(ad1.toList()).containsExactly((3)).inOrder();

        Deque61B<Integer> ad2 = new ArrayDeque61B<>();
        ad2.addLast(0);
        ad2.addLast(1);
        ad2.addFirst(-1);
        ad2.addLast(2);
        ad2.addFirst(-2);
        while (!ad2.isEmpty()) {
            ad2.removeLast();
        }
        assertThat(ad2.isEmpty()).isTrue();
        assertThat(ad2.toList()).containsExactly().inOrder();
        ad2.addLast(5);
        assertThat(ad2.toList()).containsExactly((5)).inOrder();


        Deque61B<Integer> ad3 = new ArrayDeque61B<>();
        ad3.addFirst(2);
        ad3.addFirst(1);
        ad3.addFirst(0);
        ad3.addFirst(-1);
        ad3.addFirst(-2);
        ad3.removeFirst();
        assertThat(ad3.toList()).containsExactly(-1, 0, 1, 2).inOrder();

        Deque61B<Integer> ad4 = new ArrayDeque61B<>();
        ad4.addFirst(2);
        ad4.addFirst(1);
        ad4.addFirst(0);
        ad4.addFirst(-1);
        ad4.addFirst(-2);
        ad4.removeFirst();
        assertThat(ad4.toList()).containsExactly(-1, 0, 1, 2).inOrder();

        Deque61B<Integer> ad5 = new ArrayDeque61B<>();
        ad5.addLast(-2);
        ad5.addLast(-1);
        ad5.addLast(0);
        ad5.addLast(1);
        ad5.addLast(2);
        ad5.removeFirst();
        assertThat(ad5.toList()).containsExactly(-1, 0, 1, 2).inOrder();

        Deque61B<Integer> ad6 = new ArrayDeque61B<>();
        ad6.addLast(-2);
        ad6.addLast(-1);
        ad6.addLast(0);
        ad6.addLast(1);
        ad6.addLast(2);
        ad6.removeLast();
        assertThat(ad6.toList()).containsExactly(-2, -1, 0, 1).inOrder();
    }

    @Test
    public void resizeTest() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();
        ad1.addLast(0);
        ad1.addLast(1);
        ad1.addFirst(-1);
        ad1.addLast(2);
        ad1.addFirst(-2);
        ad1.addFirst(-3);
        ad1.addLast(3);
        ad1.addLast(4);
        ad1.addFirst(-4);
        assertThat(ad1.toList()).containsExactly(-4, -3, -2, -1, 0, 1, 2, 3, 4).inOrder();

        Deque61B<Integer> ad2 = new ArrayDeque61B<>();
        ad2.addLast(0);
        ad2.addLast(1);
        ad2.addFirst(-1);
        ad2.addLast(2);
        ad2.addFirst(-2);
        ad2.addFirst(-3);
        ad2.addLast(3);
        ad2.addFirst(-4);
        ad2.addLast(4);
        assertThat(ad2.toList()).containsExactly(-4, -3, -2, -1, 0, 1, 2, 3, 4).inOrder();

        Deque61B<Integer> ad3 = new ArrayDeque61B<>();
        ad3.addLast(0);
        ad3.addLast(1);
        ad3.addFirst(-1);
        ad3.addLast(2);
        ad3.addFirst(-2);
        ad3.addFirst(-3);
        ad3.addLast(3);
        ad3.addFirst(-4);
        ad3.addLast(4);
        ad3.addFirst(-5);
        ad3.addFirst(-6);
        ad3.addFirst(-7);
        ad3.addFirst(-8);
        ad3.addFirst(-9);
        ad3.addLast(5);
        ad3.addLast(6);
        ad3.addLast(7);
        ad3.addLast(8);
        ad3.addLast(9);
        ad3.addFirst(-10);
        for (int i = 0; i < 15; i++) {
            ad3.removeLast();
        }
        assertThat(ad3.toList()).containsExactly(-10, -9, -8, -7, -6).inOrder();

        Deque61B<Integer> ad4 = new ArrayDeque61B<>();
        ad4.addLast(0);
        ad4.addLast(1);
        ad4.addFirst(-1);
        ad4.addLast(2);
        ad4.addFirst(-2);
        ad4.addFirst(-3);
        ad4.addLast(3);
        ad4.addFirst(-4);
        ad4.addLast(4);
        ad4.addFirst(-5);
        ad4.addFirst(-6);
        ad4.addFirst(-7);
        ad4.addFirst(-8);
        ad4.addFirst(-9);
        ad4.addLast(5);
        ad4.addLast(6);
        ad4.addLast(7);
        ad4.addLast(8);
        ad4.addLast(9);
        ad4.addFirst(-10);
        for (int i = 0; i < 15; i++) {
            ad4.removeFirst();
        }
        assertThat(ad4.toList()).containsExactly(5, 6, 7, 8, 9).inOrder();
    }

}
