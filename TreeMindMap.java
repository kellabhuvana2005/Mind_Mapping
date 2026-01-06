import java.util.*;

public class TreeMindMap {

    /* =======================
       BASIC TREE STRUCTURE
       ======================= */
    static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    static TreeNode root;

    /* =======================
       BINARY SEARCH TREE
       ======================= */
    static TreeNode insertBST(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);
        if (val < root.val)
            root.left = insertBST(root.left, val);
        else
            root.right = insertBST(root.right, val);
        return root;
    }

    /* =======================
       TREE TRAVERSALS
       ======================= */
    static void inorder(TreeNode root) {
        if (root == null) return;
        inorder(root.left);
        System.out.print(root.val + " ");
        inorder(root.right);
    }

    static void leftView(TreeNode root) {
        if (root == null) return;
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);

        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode cur = q.poll();
                if (i == 0) System.out.print(cur.val + " ");
                if (cur.left != null) q.add(cur.left);
                if (cur.right != null) q.add(cur.right);
            }
        }
    }

    static void zigZag(TreeNode root) {
        if (root == null) return;
        Queue<TreeNode> q = new LinkedList<>();
        boolean ltr = true;
        q.add(root);

        while (!q.isEmpty()) {
            int size = q.size();
            List<Integer> level = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                TreeNode cur = q.poll();
                level.add(cur.val);
                if (cur.left != null) q.add(cur.left);
                if (cur.right != null) q.add(cur.right);
            }

            if (!ltr) Collections.reverse(level);
            System.out.print(level + " ");
            ltr = !ltr;
        }
    }

    static void verticalOrder(TreeNode root) {
        if (root == null) return;

        Map<Integer, List<Integer>> map = new TreeMap<>();
        Queue<TreeNode> q = new LinkedList<>();
        Queue<Integer> hd = new LinkedList<>();

        q.add(root);
        hd.add(0);

        while (!q.isEmpty()) {
            TreeNode cur = q.poll();
            int h = hd.poll();

            map.putIfAbsent(h, new ArrayList<>());
            map.get(h).add(cur.val);

            if (cur.left != null) {
                q.add(cur.left);
                hd.add(h - 1);
            }
            if (cur.right != null) {
                q.add(cur.right);
                hd.add(h + 1);
            }
        }

        for (List<Integer> list : map.values())
            System.out.print(list + " ");
    }

    /* =======================
       FIND PATH (NO BOOLEAN)
       ======================= */
    static void findPath(TreeNode root, int target, List<TreeNode> path) {
        if (root == null) return;

        if (root.val == target) {
            path.add(root);
            return;
        }

        findPath(root.left, target, path);
        if (!path.isEmpty()) {
            path.add(root);
            return;
        }

        findPath(root.right, target, path);
        if (!path.isEmpty()) {
            path.add(root);
        }
    }

    /* =======================
       DISTANCE BETWEEN TWO NODES
       ======================= */
    static int distanceBetweenNodes(int n1, int n2) {
        List<TreeNode> p1 = new ArrayList<>();
        List<TreeNode> p2 = new ArrayList<>();

        findPath(root, n1, p1);
        findPath(root, n2, p2);

        int i = p1.size() - 1;
        int j = p2.size() - 1;

        while (i >= 0 && j >= 0 && p1.get(i) == p2.get(j)) {
            i--;
            j--;
        }
        return i + j + 2;
    }

    /* =======================
       MICKEY TO CHEESE
       ======================= */
    static String mickeyToCheese(int mickey, int cheese) {

        List<TreeNode> lmr = new ArrayList<>();
        List<TreeNode> lcr = new ArrayList<>();

        findPath(root, mickey, lmr);
        findPath(root, cheese, lcr);

        int i = lmr.size() - 1;
        int j = lcr.size() - 1;

        while (i >= 0 && j >= 0 && lmr.get(i) == lcr.get(j)) {
            i--;
            j--;
        }

        StringBuilder ans = new StringBuilder();

        for (int k = 0; k <= i; k++) ans.append("U");

        while (j >= 0) {
            TreeNode parent = lcr.get(j + 1);
            TreeNode child = lcr.get(j);

            if (parent.left == child) ans.append("L");
            else ans.append("R");

            j--;
        }
        return ans.toString();
    }

    /* =======================
       NODES AT K DISTANCE
       ======================= */
    static int nodesAtKDistance(int start, int k) {

        Map<TreeNode, TreeNode> parent = new HashMap<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);

        TreeNode startNode = null;

        while (!q.isEmpty()) {
            TreeNode cur = q.poll();
            if (cur.val == start) startNode = cur;

            if (cur.left != null) {
                parent.put(cur.left, cur);
                q.add(cur.left);
            }
            if (cur.right != null) {
                parent.put(cur.right, cur);
                q.add(cur.right);
            }
        }

        if (startNode == null) return 0;

        Set<TreeNode> visited = new HashSet<>();
        q.add(startNode);
        visited.add(startNode);

        int dist = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            if (dist == k) return size;

            for (int i = 0; i < size; i++) {
                TreeNode cur = q.poll();

                if (cur.left != null && visited.add(cur.left))
                    q.add(cur.left);

                if (cur.right != null && visited.add(cur.right))
                    q.add(cur.right);

                if (parent.containsKey(cur) && visited.add(parent.get(cur)))
                    q.add(parent.get(cur));
            }
            dist++;
        }
        return 0;
    }

    /* =======================
       BURN A TREE
       ======================= */
    static int burnTree(int start) {
        Map<TreeNode, TreeNode> parent = new HashMap<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);

        TreeNode startNode = null;

        while (!q.isEmpty()) {
            TreeNode cur = q.poll();
            if (cur.val == start) startNode = cur;

            if (cur.left != null) {
                parent.put(cur.left, cur);
                q.add(cur.left);
            }
            if (cur.right != null) {
                parent.put(cur.right, cur);
                q.add(cur.right);
            }
        }

        Set<TreeNode> visited = new HashSet<>();
        q.add(startNode);
        visited.add(startNode);

        int time = -1;
        while (!q.isEmpty()) {
            int size = q.size();
            time++;

            for (int i = 0; i < size; i++) {
                TreeNode cur = q.poll();

                if (cur.left != null && visited.add(cur.left))
                    q.add(cur.left);

                if (cur.right != null && visited.add(cur.right))
                    q.add(cur.right);

                if (parent.containsKey(cur) && visited.add(parent.get(cur)))
                    q.add(parent.get(cur));
            }
        }
        return time;
    }

    /* =======================
       MAIN
       ======================= */
    public static void main(String[] args) {

        int[] arr = {10, 5, 15, 3, 7, 12, 18};
        for (int x : arr)
            root = insertBST(root, x);

        System.out.print("Inorder: ");
        inorder(root);

        System.out.print("\nLeft View: ");
        leftView(root);

        System.out.print("\nZigZag: ");
        zigZag(root);

        System.out.print("\nVertical Order: ");
        verticalOrder(root);

        System.out.print("\nDistance (3,7): ");
        System.out.println(distanceBetweenNodes(3, 7));

        System.out.print("Mickey → Cheese (3→7): ");
        System.out.println(mickeyToCheese(3, 7));

        System.out.print("Nodes at distance 2 from 5: ");
        System.out.println(nodesAtKDistance(5, 2));

        System.out.print("Burn tree from 5: ");
        System.out.println(burnTree(5));
    }
}
