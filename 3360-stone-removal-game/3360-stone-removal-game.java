
class Solution {
    public boolean canAliceWin(int n) {

        int remove = 10;

        while (n >= remove) {
            n -= remove;
            remove--;
        }

        // Alice wins if Bob cannot make his move
        return remove % 2 == 1;
    }
}
