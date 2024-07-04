package Week_09.BOJ_16234;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_16234 {

    public static void main(String[] args) throws Exception{

        // Input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int l = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());


        int[][] arr = new int[n][n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }


        int answer = -1;        // 0초부터 시작
        int[] dy = {1,-1,0,0};
        int[] dx = {0,0,1, -1};
        boolean going = true;   // 인구 이동 없을 시 시뮬레이션 종료

        // q: BFS 큐, opened: 인구 이동 영역 체크
        ArrayDeque<Integer> q = new ArrayDeque<>();
        ArrayDeque<Integer> opened = new ArrayDeque<>();


        boolean[][] visited = new boolean[n][n];
        int curr; int ny, nx, tmp, cnt, sum;

        boolean now = false; // visited 배열 초기화하는 대신 매 시뮬레이션마다 now인 것이 unvisited인것으로 체크

        // 시뮬레이션
        while (going) {
            going = false;
            cnt = 0; answer++;

            // visited에 따라 bfs 수행
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if(visited[i][j] == now){
                        q.offerLast(i*n+j);         // bfs 시작점
                        opened.offer(q.peekLast());     // 인구 이동 큐에도 추가
                        visited[i][j] = !now;           // visited로 표시
                        sum=arr[i][j];                  // 인구 count

                        // BFS
                        while(!q.isEmpty()) {
                            curr = q.pollFirst();
                            for (int k = 0; k < 4; k++) {
                                ny = curr/n+dy[k];
                                nx = curr%n+dx[k];

                                if(ny<0 || nx<0 || ny>=n||nx>=n) continue;

                                // 인구 차이 확인 후 bfs 큐 추가, 인구 이동 추가 및 visited 표시, 인구 count
                                tmp = Math.abs(arr[ny][nx]-arr[curr/n][curr%n]);
                                if(visited[ny][nx]==now && l<=tmp && tmp<=r){
                                    q.offerLast(ny*n+nx);
                                    opened.offer(q.peekLast());
                                    visited[ny][nx] = !now;
                                    sum+=arr[ny][nx];
                                }
                            }
                        }

                        // 인구 이동
                        sum /= opened.size();
                        if(opened.size()>1) {           // 두 개 도시 이상이여야 이동이 의미있다
                            while (!opened.isEmpty()) {
                                going = true;           // 인구 이동 발생 -> 시뮬레이션 반복 조건 체크
                                curr = opened.pollFirst();
                                arr[curr/n][curr%n] = sum;  //인구 값 업데이트
                            }
                        }
                        else opened.clear();    // 초기화
                    }
                }
            }
            now=!now;   // visited 변경
        }
        System.out.println(answer);
    }
}
