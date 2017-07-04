import java.util.LinkedList;

public class Experiment {
    public static void main(String[] args) {
        LinkedList<Integer> l = new LinkedList<>();
        for(int i = 0 ; i < 1_000_000 ; i++)
        {
            l.add(i);
        }

        int center = l.size() >> 1;
        System.out.println(center);

        for (int i = 0; i < 500 ; i++)
        {
            l.add(center, 500-i);
        }
        for (int i = center - 10; i < center + 520; i++) {
            System.out.println(l.get(i).toString());
        }
    }
}
