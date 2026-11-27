
import java.util.*;

class Solution {

   public int maximumSaleItems(int[][] items, int budget) {

        int n = items.length;

        // Step 1: Calculate free items for each item
        int[] reward = new int[n];

        for (int i = 0; i < n; i++) {

            int free = 0;

            for (int j = 0; j < n; j++) {

                if (i != j &&
                    items[j][0] % items[i][0] == 0) {
                    free++;
                }
            }

            reward[i] = free + 1;
        }

        // Step 2: Sort items by price in descending order
        Integer[] order = new Integer[n];

        for (int i = 0; i < n; i++) {
            order[i] = i;
        }

        Arrays.sort(order, (a, b) ->
            Integer.compare(items[b][1], items[a][1])
        );

        // dp[c] = maximum reward using items
        // with prices greater than current price
        int[] dp = new int[budget + 1];

        int ans = 0;
        int index = 0;

        // Step 3: Process equal-price groups
        while (index < n) {

            int price = items[order[index]][1];

            // dp represents items with strictly higher prices
            int[] one = new int[budget + 1];

            Arrays.fill(one, -1000000000);

            // Select at least one item from current price group
            for (int x = index; x < n &&
                 items[order[x]][1] == price; x++) {

                int id = order[x];
                int r = reward[id];

                for (int c = budget; c >= price; c--) {

                    one[c] = Math.max(
                        one[c],
                        Math.max(
                            dp[c - price] + r,
                            one[c - price] + r
                        )
                    );
                }
            }

            // Step 4: Calculate answer
            for (int c = 0; c <= budget; c++) {

                if (one[c] >= 0) {

                    int total = one[c] +
                                (budget - c) / price;

                    ans = Math.max(ans, total);
                }
            }

            // Step 5: Add current group to normal DP
            for (int x = index; x < n &&
                 items[order[x]][1] == price; x++) {

                int id = order[x];
                int r = reward[id];

                for (int c = budget; c >= price; c--) {
                    dp[c] = Math.max(
                        dp[c],
                        dp[c - price] + r
                    );
                }
            }

            // Move to next price group
            while (index < n &&
                   items[order[index]][1] == price) {
                index++;
            }
        }

        return ans;
    }
}
