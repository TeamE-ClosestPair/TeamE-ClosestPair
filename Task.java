import java.util.*;

public class Task {
    // 정적 변수
    static Scanner scan = new Scanner(System.in);
    static int cnt = scan.nextInt();  // 점의 개수 입력
    static int[][] s = new int[cnt][2];  // 각 점들의 배열 생성

    // 거리구하는 함수
    public static double dist(int[] x1, int[] x2){
        return  Math.sqrt((Math.pow((x1[0] - x2[0]),2) + Math.pow((x1[1] - x2[1]),2)));
    }

    // 문제해결 함수
    public static double solve(int start, int end) {
        int length = end - start;

        if (length == 2) return dist(s[start], s[start+1]);
        else if (length == 3) return Math.min(Math.min(dist(s[start], s[start+1]), dist(s[start], s[start+2])), dist(s[start+1], s[start+2]));
        else {
            int mid = (start + end) / 2;
            double d = Math.min(solve(start, mid), solve(mid, end));  // 재귀함수

            int midx = s[mid][0];  // 중간 x좌표

            List<int[]> new_lst = new ArrayList<>(); // 중간에 걸치는 점들의 새로운 arraylist
            for (int i = start; i < end; i++) {
                if (Math.pow((s[i][0] - midx), 2) <= d)
                    new_lst.add(s[i]);  // 중간 x좌표에서부터 각 점의 길이가 d보다 작거나 같은놈들을 arrayList에 추가
            }

            int new_len = new_lst.toArray().length;
            if (new_len >= 2) {
                new_lst.sort(new Comparator<int[]>() {   // Y좌표기준으로 정렬
                    @Override
                    public int compare(int[] o1, int[] o2) {
                        return o1[1] - o2[1];
                    }
                });
                for (int i = 0; i < new_len - 1; i++) {
                    for (int j = i + 1; j < new_len; j++) {
                        if (Math.pow((new_lst.get(i)[1] - new_lst.get(j)[1]), 2) > d ) {  // Y좌표의 차이가 d보다 크면 무의미
                            break;
                        } else if ((new_lst.get(i)[0] < midx) && (new_lst.get(j)[0]) < midx) {  // 둘 다 왼쪽면에 있다면
                            continue;
                        } else if ((new_lst.get(i)[0] >= midx) && (new_lst.get(j)[0]) >= midx) {   // 둘 다 오른쪽면에 있다면
                            continue;
                        }
                        d = Math.min(d, dist(new_lst.get(i), new_lst.get(j)));

                    }
                }

            }
            return d;


        }

    }
    // 메인 함수
    public static void main(String[] args) {

        for (int i = 0; i < cnt; i++) {
            s[i][0] = scan.nextInt();  // x좌표 입력
            s[i][1] = scan.nextInt();  // y좌표 입력
        }
        Arrays.sort(s, new Comparator<int[]>() {           // X좌표기준으로 정렬
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        System.out.println(solve(0,cnt));
    }
}


