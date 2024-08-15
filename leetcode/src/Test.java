import java.util.HashMap;
import java.util.Map;

public class Test {


    public static void main(String[] args) {
        System.out.println(2234 ^ 2234 ^ 265);
    }
}

// 9 + 9 + 4 = 22
// 16 +

class Solution {
    public int majorityElement(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.compute(num, (key, value) -> value == null ? 1 : value + 1);
        }
        int maxNum = 0;
        int maxQty = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() > maxQty) {
                maxQty = entry.getValue();
                maxNum = entry.getKey();
            }
        }
        return maxNum;
    }
}