import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

/*
   1. 아래와 같은 명령어를 입력하면 컴파일이 이루어져야 하며, Solution4 라는 이름의 클래스가 생성되어야 채점이 이루어집니다.
       javac Solution4.java -encoding UTF8


   2. 컴파일 후 아래와 같은 명령어를 입력했을 때 여러분의 프로그램이 정상적으로 출력파일 output4.txt 를 생성시켜야 채점이 이루어집니다.
       java Solution4

   - 제출하시는 소스코드의 인코딩이 UTF8 이어야 함에 유의 바랍니다.
   - 수행시간 측정을 위해 다음과 같이 time 명령어를 사용할 수 있습니다.
       time java Solution4
   - 일정 시간 초과시 프로그램을 강제 종료 시키기 위해 다음과 같이 timeout 명령어를 사용할 수 있습니다.
       timeout 0.5 java Solution4   // 0.5초 수행
       timeout 1 java Solution4     // 1초 수행
 */

class Solution4 {
	static final int max_n = 100000;

	static int n;
	static int[][] A = new int[3][max_n];
	static int Answer;

	public static int max(int i, int j){
		return i > j ? i : j;
	}
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("input4.txt"));
		StringTokenizer stk;
		PrintWriter pw = new PrintWriter("output4.txt");

		for (int test_case = 1; test_case <= 10; test_case++) {

			stk = new StringTokenizer(br.readLine());
			n = Integer.parseInt(stk.nextToken());
			for (int i = 0; i < 3; i++) {
				stk = new StringTokenizer(br.readLine());
				for (int j = 0; j < n; j++) {
					A[i][j] = Integer.parseInt(stk.nextToken());
				}
			}

			int [][] Sum = new int[n][7];
			Answer = 0x80000000;
			Sum[0][1] = A[0][0] - A[2][0];
			Sum[0][2] = A[0][0] - A[1][0];
			Sum[0][3] = A[1][0] - A[2][0];
			Sum[0][4] = A[2][0] - A[1][0];
			Sum[0][5] = A[1][0] - A[0][0];
			Sum[0][6] = A[2][0] - A[0][0];

			for(int i=1; i<n; i++){
				Sum[i][1] = max(Sum[i-1][4], Sum[i-1][5]) + A[0][i] - A[2][i];
				Sum[i][2] = max(Sum[i-1][3], Sum[i-1][6]) + A[0][i] - A[1][i] ;
				Sum[i][3] = max(Sum[i-1][2], Sum[i-1][6]) + A[1][i] - A[2][i];
				Sum[i][4] = max(Sum[i-1][1], Sum[i-1][5]) + A[2][i] - A[1][i];
				Sum[i][5] = max(Sum[i-1][4], Sum[i-1][1]) + A[1][i] - A[0][i];
				Sum[i][6] = max(Sum[i-1][3], Sum[i-1][2]) + A[2][i] - A[0][i];
			}

			for(int i=1; i<=6; i++){
				if(Answer < Sum[n-1][i])
					Answer = Sum[n-1][i];
			}
			//총 6가지의 케이스가 생긴다.
			// 그 케이스들을 분류하면 빅 세타 n 시간으로 결과가 보장된다.

			// output4.txt로 답안을 출력합니다.
			pw.println("#" + test_case + " " + Answer);

			pw.flush();
		}

		br.close();
		pw.close();
	}
}

