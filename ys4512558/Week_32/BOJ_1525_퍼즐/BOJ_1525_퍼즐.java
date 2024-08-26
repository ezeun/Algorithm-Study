import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ1525 {

    static Map<Integer, Boolean> isv;
    static int[] dx = new int[]{-1, 1, 0, 0};
    static int[] dy = new int[]{0, 0, -1, 1};
    static int destKey = 123456789;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        isv = new HashMap<>();
        int[][] map = new int[3][3];
        int x = 0;
        int y = 0;
        for (int i = 0; i < 3; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 0) {
                    map[i][j] = 9;
                    x = i;
                    y = j;
                }
            }
        }
        int key = makeKey(map);
        isv.put(key, true);
        State state = new State(x, y, map);
        System.out.println(key == destKey ? 0 : bfs(state));
    }

    private static int bfs(State start) {
        Queue<State> queue = new ArrayDeque<>();
        queue.offer(start);

        int cnt = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            cnt++;
            while (size-- > 0) {
                State state = queue.poll();

                for (int i = 0; i < 4; i++) {
                    int nx = state.x + dx[i];
                    int ny = state.y + dy[i];

                    if (nx < 0 || ny < 0 || nx >= 3 || ny >= 3) continue;
                    int[][] copy = copyMap(state.map);
                    swap(copy, state.x, state.y, nx, ny);
                    int key = makeKey(copy);
                    if(key == destKey) return cnt;
                    if (isv.getOrDefault(key, false)) continue;
                    queue.offer(new State(nx, ny, copy));
                    isv.put(key, true);
                }
            }
        }
        return -1;
    }

    private static void swap(int[][] map, int x, int y, int nx, int ny) {
        int temp = map[x][y];
        map[x][y] = map[nx][ny];
        map[nx][ny] = temp;
    }

    private static int makeKey(int[][] map) {
        int key = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                key = key * 10 + map[i][j];
            }
        }
        return key;
    }

    public static int[][] copyMap(int[][] map) {
        int[][] copy = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                copy[i][j] = map[i][j];
            }
        }
        return copy;
    }
}

class State {
    int x, y;
    int[][]
        map;

    public State(int x, int y, int[][] map) {
        this.x = x;
        this.y = y;
        this.map = map;
    }
}