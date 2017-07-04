public class Experiment {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        LinkedList<Integer> l = new LinkedList<>();

        for(int i = 0 ; i < 1_000_000 ; i++)
        {
            l.add(i);
        }
        ArrayList<Integer> l2 = new ArrayList<>(l.size()+500);
        l2.addAll(l);
        ArrayList<Integer> l3 = new ArrayList<>(500);
        int center = l.size() >> 1;
        for (int i = 0; i < 500 ; i++)
        {
            l3.add(i);
        }
        l2.addAll(center,l3);
        l = new LinkedList<>(l2);
        System.out.println( (System.currentTimeMillis() - start) +" ms");
    }
}
