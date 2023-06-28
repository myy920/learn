import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Demo {

    public static void main(String[] args) {
        List<R> collect = Stream.of("a", "b", "c", "d").parallel().map(p -> {
            try {
                return getR(p);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());

        collect = collect;
    }

    private static R getR(String p) throws InterruptedException {
        System.out.println(Thread.currentThread() + p);
        Thread.sleep(100000);
        R r = new R();
        r.setName(p);
        r.setQty((long) p.hashCode());
        return r;
    }

    public static class R {

        private String name;

        private Long qty;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getQty() {
            return qty;
        }

        public void setQty(Long qty) {
            this.qty = qty;
        }
    }

}
