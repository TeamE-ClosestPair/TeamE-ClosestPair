# TeamE-ClosestPair




최근접 점의 쌍을 찾는 문제는 n개의 점이 있다고 가정 할 때, 서로 다른 점 n개를 2개씩 짝지어서
구하는 방법과 분할 정복으로 문제를 풀 수 있다. 전자의 경우, 시간 복잡도가 O(n^2)로 n이 커질수록
시간 복잡도가 기하 급수적으로 증가하지만 분할 정복 방식으로 문제를 해결하면 시간 복잡도는 O(nlog^2n)
이다. 따라서 이 문제를 풀기 위해서는 분할 정복을 이용하는 편이 효율적이다. 

해당 알고리즘은 다양한 곳에서 활용되는데 컴퓨터 그래픽스, 컴퓨터 비전, 지리정보시스템, 분자 모델링, 항공 트래픽 제어 등의 분야에서 사용된다.



따라서 TeamE가 작성한 코드는 다음과 같다.


```java


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


```




해당 코드에 대한 자세한 설명은 아래 사진과 함께 첨부하기로 한다.





## 2차원 평면에 점 생성하기


![](https://postfiles.pstatic.net/MjAyMTAzMjlfMjc2/MDAxNjE3MDI0NjE0MzM3.O-tS_DEsrd4Lq4-gp0D3nRdUfiXE4_wvM1PpHzyMxNEg.GFlrArnivUOeg35SMWPs81NBbwdJwDw40Q8Rg8gn_70g.PNG.codnjs060/image.png?type=w773)

먼저 사용자에게 값을 입력 받기 위해서 Scanner를 이용한다. Scanner로 입력 받은 값은 점의 개수이다.

각 점들은 2차원 평면에 위치해 있고 수월한 계산을 위해 x,y평면에 위치 시키기 위해 int [][]를 사용한다.


## 두 점 사이의 거리 구하는 dist메소드 만들기


![](https://postfiles.pstatic.net/MjAyMTAzMjlfNDAg/MDAxNjE3MDI0NjQ3Nzcz.OYQCZNmUNPPljwnU24rscfBj_JjcLKyBDMaEVLdx5Y4g.JnFqVQR3rYHZ6BULyXd_P3Rz3aJ4fy_IEBy680TVz3Ig.PNG.codnjs060/image.png?type=w773)

두점 사이의 거리를 구하는 공식을 사용하여 dist메소드를 만들었다. (x1-x2)와 (y7-y2)를 거듭 제곱 하기 위해서 Math.pow함수를 이용하고, 제곱근을 의미하는 Math.sqrt를 이용하여 두 점 사이의 거리를 구한다.
이때, dist의 자료형은 제곱근의 해를 구하기 위해 크기가 8바이트이고 실수형인 double로 한다.



## 문제 해결을 위한 solve메소드


![](https://postfiles.pstatic.net/MjAyMTAzMjlfMjI2/MDAxNjE3MDI0NzAxMzgy.XryXZVBtMWY8jdfbdt9EMNKG_9dC3-CB6m6xJkQqdN8g.yhUenhuEiuvz8WrE3y-B5Qat72iDzfB1UJwUskMvaKYg.PNG.codnjs060/image.png?type=w773)

int lenth = end - start; 가 의미 하는 바는 점의 개수 이다. 점이 2개면 그 둘의 거리만 비교하면 되고, 두 점이 최근접 점의 쌍이 된다.

점이 3개면 그냥 서로 다른 3개의 점을 2개씩 짝지어서 거리를 계산하고 그 중 최소 값을 구하면 된다.

하지만, 점의 개수가 4개 이상이라면, 정렬된 점들을 같은 크기로 분할한다. 이걸 mid라고 하자. mid는 점의 개수를 1/2 하는 것이기 때문에 start+end/2를 한다.

분할된 배열에 대해서 재귀적으로 거리의 최솟값을 구해 최근접 점의 쌍을 찾는다.



## 중간에 걸치는 점들 고려하기


![](https://postfiles.pstatic.net/MjAyMTAzMjlfMjk0/MDAxNjE3MDI3NjY0Nzg3.voOAY0b2xnAje3Dz_l0DU--PTChSxy715zejewKLSGQg.lnkEr3VkXS14Osz7g8fQkTAb3-6Ae22vjZgMcdKLWqMg.PNG.codnjs060/image.png?type=w773)

편의 상 분할된 배열을 왼쪽 부분과 오른쪽 부분이라 하자. 아래 사진과 같이 최근접 점의 쌍이 왼쪽 부분의 가장 오른쪽에 있는 점과 오른쪽 부분의 가장 왼쪽 점의 쌍인 경우를 고려해야한다.

![](https://www.google.com/url?sa=i&url=https%3A%2F%2Fhygenie-studynote.tistory.com%2F46&psig=AOvVaw0PT17du9O9eGJjKw0wdpbY&ust=1617117697835000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCNjFk4nn1e8CFQAAAAAdAAAAABAD)

따라서 중간에 걸치는 점들의 새로운 ArrayList를 만들고 이에 해당하는 점들의 길이가 위에서 선언한 d보다 작거나 같은 경우 ArrayList에 추가한다.

알고리즘의 전처리(processing) 과정으로서 점을 x좌표로 정렬하여 배열의 중간 인덱스를 계산하여 분할하면 되지만 중간 영역에 있는 점들은 y좌표를 기준으로 배열한 후에 위에서 아래로(혹은 아래에서 위로) 각 점을 기준으로 거리가 d 이내인 주변의 점들 사이의 거리를 각각 계산해야 하기 때문에 Override가 comparator 인터페이스를 사용해서 y좌표로 정렬하였다.



## 각 부분해 중에서 가장 작은 값 찾기


![](https://postfiles.pstatic.net/MjAyMTAzMjlfMiAg/MDAxNjE3MDI0Nzk2MDgy.2aQj3BW6qGAE91PbRVHcDoSYplQ0aCHMjdi4_QeKjJAg.Pl4UuMUhD7MpVUzZU-JMDd8HYcCy9QbqcXJhF4jKyw8g.PNG.codnjs060/image.png?type=w773)

중간에 걸치는 점들을 y좌표로 정렬했기 때문에 이제 하나씩 순서대로 비교한다. 각 두 점의 Y좌표의 차이가 d보다 크면 어차피 거리도 크기때문에
break문으로 탈출한다. 그리고 d보다 작아도 두 점이 둘 다 midx보다 작거나(왼쪽면) , midx보다 크면(오른쪽면)
이미 재귀함수 호출할 때 처리하였기에 고려 할 필요가 없어 continue문으로 되돌아 간다.
이 세 조건문을 모두 통과하면 비로소 두 점과 앞에서 구했던 d 중에서 다시 한 번 최솟값을 구한다


## main 메소드


![](https://postfiles.pstatic.net/MjAyMTAzMjlfMTQw/MDAxNjE3MDI0ODIwMjg3.v0xzkiX2pE9dtMgf08KZ_YFEfdvoxNDT_0uZ51aJVW0g.CxH7CZHVy97elx-xOCYO4wYeBgV4SgPij8eKm5m-BWUg.PNG.codnjs060/image.png?type=w773)

점의 개수와 각 점들의 x, y좌표를 입력 받기 위해 for문을 사용한다. 입력 받은 x좌표로 미리 정렬 되어 있다고 가정 하고 문제를 풀었기 때문에 x좌표를 기준으로 정렬하는 인터페이스를 선언한다.


solve 메소드에 반환된 최근접 점들 사이의 거리(d)가 출력된다.



##실행결과

![](https://blogfiles.pstatic.net/MjAyMTAzMzBfMTM1/MDAxNjE3MDMyMTcxODY0.N7MTyaNvLf09WPYL2iO65CRj2RnSSbqh_zDm5YlKmI0g.X0xDS1cz16NZnHXGYy81f2BWILRPwcCV7ITISCS8TGkg.PNG.codnjs060/image.png)


앞서 작성한 코드를 실행 하면 위와 같은 결과로 출력된다. 




























<출처> 
최근접 점의 쌍, 양성봉, 생능 출판, 2013, 74~83

사진 ClosestPair of Points <https://hygenie-studynote.tistory.com/46>(2021-03-30)
