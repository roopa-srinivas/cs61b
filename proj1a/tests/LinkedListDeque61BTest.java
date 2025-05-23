import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDeque61BTest {

     @Test
     /** In this test, we have three different assert statements that verify that addFirst works correctly. */
     public void addFirstTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addFirst("back"); // after this call we expect: ["back"]
         assertThat(lld1.toList()).containsExactly("back").inOrder();

         lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
         assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

         lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
     }

     @Test
     /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
      *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
     public void addLastTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addLast("front"); // after this call we expect: ["front"]
         lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
         lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
     }

     @Test
     /** This test performs interspersed addFirst and addLast calls. */
     public void addFirstAndAddLastTest() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
         lld1.addLast(0);   // [0]
         lld1.addLast(1);   // [0, 1]
         lld1.addFirst(-1); // [-1, 0, 1]
         lld1.addLast(2);   // [-1, 0, 1, 2]
         lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

         assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
     }
     // Below, you'll write your own tests for LinkedListDeque61B.
     @Test
     /** This test performs isEmpty calls. */
     public void isEmptyTest() {
         Deque61B<Integer> lldEmpty = new LinkedListDeque61B<>();
         assertThat(lldEmpty.isEmpty()).isTrue();

         Deque61B<Integer> lldNonEmpty = new LinkedListDeque61B<>();
         lldNonEmpty.addLast(0);
         lldNonEmpty.addLast(1);
         lldNonEmpty.addFirst(-1);
         lldNonEmpty.addLast(2);
         lldNonEmpty.addFirst(-2);
         assertThat(lldNonEmpty.isEmpty()).isFalse();
     }

     @Test
     /** This test performs a size call*/
     public void sizeTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        lld1.addLast(0);
        lld1.addLast(1);
        lld1.addFirst(-1);
        lld1.addLast(2);
        lld1.addFirst(-2);
        assertThat(lld1.size()).isEqualTo(5);
     }

     @Test
    /** This performs a test for the get method */
    public void testGet() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
         lld1.addLast(0);
         lld1.addLast(1);
         lld1.addFirst(-1);
         lld1.addLast(2);
         lld1.addFirst(-2);
         assertThat(lld1.get(3)).isEqualTo(1);
         assertThat(lld1.get(28723)).isEqualTo(null);
         assertThat(lld1.get(-3)).isEqualTo(null);
     }

     @Test
     /** Test that checks getRecursive */
     public void testGetRecursive() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
         lld1.addLast(0);
         lld1.addLast(1);
         lld1.addFirst(-1);
         lld1.addLast(2);
         lld1.addFirst(-2);
         assertThat(lld1.getRecursive(3)).isEqualTo(1);
         assertThat(lld1.getRecursive(28723)).isEqualTo(null);
         assertThat(lld1.getRecursive(-3)).isEqualTo(null);
     }

     @Test
    /** Tests removeFirst and removeLast */
    public void testRemoveFirstAndRemoveLast() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
         lld1.addLast(0);
         lld1.addLast(1);
         lld1.addFirst(-1);
         lld1.addLast(2);
         lld1.addFirst(-2);
         while (!lld1.isEmpty()) {
             lld1.removeFirst();
         }
         assertThat(lld1.isEmpty()).isTrue();
         lld1.addFirst(3);
         assertThat(lld1.toList()).containsExactly((3)).inOrder();

         Deque61B<Integer> lld2 = new LinkedListDeque61B<>();
         lld2.addLast(0);
         lld2.addLast(1);
         lld2.addFirst(-1);
         lld2.addLast(2);
         lld2.addFirst(-2);
         while (!lld2.isEmpty()) {
             lld2.removeLast();
         }
         assertThat(lld2.isEmpty()).isTrue();
         assertThat(lld2.toList()).containsExactly().inOrder();
         lld2.addLast(5);
         assertThat(lld2.toList()).containsExactly((5)).inOrder();


         Deque61B<Integer> lld3 = new LinkedListDeque61B<>();
         lld3.addFirst(2);
         lld3.addFirst(1);
         lld3.addFirst(0);
         lld3.addFirst(-1);
         lld3.addFirst(-2);
         lld3.removeFirst();
         assertThat(lld3.toList()).containsExactly(-1, 0, 1, 2).inOrder();

         Deque61B<Integer> lld4 = new LinkedListDeque61B<>();
         lld4.addFirst(2);
         lld4.addFirst(1);
         lld4.addFirst(0);
         lld4.addFirst(-1);
         lld4.addFirst(-2);
         lld4.removeFirst();
         assertThat(lld4.toList()).containsExactly(-1, 0, 1, 2).inOrder();

         Deque61B<Integer> lld5 = new LinkedListDeque61B<>();
         lld5.addLast(-2);
         lld5.addLast(-1);
         lld5.addLast(0);
         lld5.addLast(1);
         lld5.addLast(2);
         lld5.removeFirst();
         assertThat(lld5.toList()).containsExactly(-1, 0, 1, 2).inOrder();

         Deque61B<Integer> lld6 = new LinkedListDeque61B<>();
         lld6.addLast(-2);
         lld6.addLast(-1);
         lld6.addLast(0);
         lld6.addLast(1);
         lld6.addLast(2);
         lld6.removeLast();
         assertThat(lld6.toList()).containsExactly(-2, -1, 0, 1).inOrder();
     }
}