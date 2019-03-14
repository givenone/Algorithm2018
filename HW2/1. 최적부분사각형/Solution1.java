import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

/*
   1. 아래와 같은 명령어를 입력하면 컴파일이 이루어져야 하며, Solution1 라는 이름의 클래스가 생성되어야 채점이 이루어집니다.
       javac Solution1.java -encoding UTF8


   2. 컴파일 후 아래와 같은 명령어를 입력했을 때 여러분의 프로그램이 정상적으로 출력파일 output4.txt 를 생성시켜야 채점이 이루어집니다.
       java Solution1

   - 제출하시는 소스코드의 인코딩이 UTF8 이어야 함에 유의 바랍니다.
   - 수행시간 측정을 위해 다음과 같이 time 명령어를 사용할 수 있습니다.
       time java Solution1
   - 일정 시간 초과시 프로그램을 강제 종료 시키기 위해 다음과 같이 timeout 명령어를 사용할 수 있습니다.
       timeout 0.5 java Solution1   // 0.5초 수행
       timeout 1 java Solution1     // 1초 수행
 */

class Solution1 {
	static final int max_n = 200;

	static int n;
	static int[][] A = new int[max_n][max_n];
	static int Answer;

	public static int odd(int a)
	{
		return a%2 == 0 ? a : -a;
	}

	public static int max(int a, int b)
	{
		return a > b ? a : b;
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("input1.txt"));
		StringTokenizer stk;
		PrintWriter pw = new PrintWriter("output1.txt");

		for (int test_case = 1; test_case <= 10; test_case++) {

			stk = new StringTokenizer(br.readLine());
			n = Integer.parseInt(stk.nextToken());
			for (int i = 0; i < n; i++) {
				stk = new StringTokenizer(br.readLine());
				for (int j = 0; j < n; j++) {
					A[i][j] = Integer.parseInt(stk.nextToken());
				}
			}

			Answer = 0x80000000;
			int [][] linearsum = new int[n][n];
			int [][] partialMax = new int[n][n];
			for(int i=0; i<n; i++){
				int temp = odd(A[0][i]);
				partialMax[i][i] = temp;
				if(Answer < temp)
				Answer = temp;
				for(int j=i+1; j<n ; j++)
				{
					partialMax[i][j] = partialMax[i][j-1] +odd(A[0][j]);
					if(partialMax[i][j] > Answer)
						Answer = partialMax[i][j];
				}
			} // initial

			for(int depth=1; depth<n; depth++)
			{
				for(int i=0; i<n; i++)
				{
					linearsum[i][i] = odd(A[depth][i]);
					partialMax[i][i] = max(linearsum[i][i], partialMax[i][i] + linearsum[i][i]);
					if(partialMax[i][i] > Answer)
						Answer = partialMax[i][i];
					for(int j=i+1; j<n; j++)
					{
						linearsum[i][j] = linearsum[i][j-1] + odd(A[depth][j]);
						partialMax[i][j] = max(linearsum[i][j], partialMax[i][j] + linearsum[i][j]);
						if(partialMax[i][j] > Answer)
							Answer = partialMax[i][j];
					}
				}
			}
/* 시간복잡도 n^3
세로 길이를 하나씩 늘려가면서 global maximum을 찾는다.
 */
			pw.println("#" + test_case + " " + Answer);
			pw.flush();
		}

		br.close();
		pw.close();
	}
}

