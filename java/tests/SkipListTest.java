import datastructures.SkipList;
import tools.Utilities;

public class SkipListTest {
    static SkipList sList;
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 5; i ++) {
            testSimpleUniqueInsertion(50 + ((int) (Math.random() * 50)));
            testSimpleInsertion(50 + ((int) (Math.random() * 50)));
            testUniqueFind();
            testDelete();
        }
    }

    private static void testSimpleUniqueInsertion(int length) throws Exception {
        System.out.printf("\tTestSimpleUniqueInsertion (n = %d)\n", length);
        SkipList list = new SkipList();
        boolean[] visited = new boolean[1024];
        addToList(list, length, visited);

        int[] array = list.toArray();

        for (int i = 0; i < array.length - 1; i ++) {
            if (array[i] >= array[i + 1]) throw new Exception(String.format("Invalid sorted order: %s", Utilities.toString(array)));
        }
    }

    private static void testSimpleInsertion(int length) throws Exception {
        System.out.printf("\tTestSimpleInsertion (n = %d)\n", length);
        SkipList list = new SkipList();
        addToList(list, length, null);

        int[] array = list.toArray();

        for (int i = 0; i < array.length - 1; i ++) {
            if (array[i] > array[i + 1]) throw new Exception(String.format("Invalid sorted order: %s", Utilities.toString(array)));
        }
    }

    private static void testUniqueFind() throws Exception {
        System.out.println("\tTestUniqueFind");
        SkipList list = new SkipList();
        boolean[] visited = new boolean[4096];
        addToList(list, visited.length / 2, visited);

        for (int i = 0; i < visited.length; i ++) {
            if (visited[i] && !list.find(i)) throw new Exception("Element not found in SkipList");
        }
    }

    private static void testDelete() throws Exception {
        System.out.println("\tTestDelete");
        SkipList list = new SkipList();
        int[] freq = new int[10];
        int count = freq.length;

        for (int i = 0; i < count; i ++) {
            int value = (int) (Math.random() * freq.length);
            freq[value] ++;
            list.add(value);
        }

        int index = 0;
        while (count > 0) {
            if (freq[index] > 0) {
                if (!list.delete(index)) throw new Exception("Failed to delete element");
                count --;
                freq[index] --;
            } else if (list.delete(index)) throw new Exception("Deleted element that does not exist");

            index = (index + 1) % freq.length;
        }
    }

    private static void addToList(SkipList list, int numOfElements, boolean[] visited) throws Exception {
        for (int i = 0; i < numOfElements; i ++) {
            // Find a unique element to insert.
            int value;
            do {
                value = (int) (Math.random() * numOfElements) + 1;
            } while (visited != null && visited[value]);
            if (visited != null)
                visited[value] = true;

            list.add(value);
        }
    }
}