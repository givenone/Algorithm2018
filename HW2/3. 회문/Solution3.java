import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.lang.String;
/*
   1. 아래와 같은 명령어를 입력하면 컴파일이 이루어져야 하며, Solution4 라는 이름의 클래스가 생성되어야 채점이 이루어집니다.
       javac Solution3.java -encoding UTF8


   2. 컴파일 후 아래와 같은 명령어를 입력했을 때 여러분의 프로그램이 정상적으로 출력파일 output4.txt 를 생성시켜야 채점이 이루어집니다.
       java Solution3

   - 제출하시는 소스코드의 인코딩이 UTF8 이어야 함에 유의 바랍니다.
   - 수행시간 측정을 위해 다음과 같이 time 명령어를 사용할 수 있습니다.
       time java Solution3
   - 일정 시간 초과시 프로그램을 강제 종료 시키기 위해 다음과 같이 timeout 명령어를 사용할 수 있습니다.
       timeout 0.5 java Solution3   // 0.5초 수행
       timeout 1 java Solution3     // 1초 수행
 */

class Solution3 {
	static final int max_n = 100000;

	static int n;
	static String s;
	static int Answer;
	public static int max(int a, int b){
		return a>b ? a : b;
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("input3.txt"));
		StringTokenizer stk;
		PrintWriter pw = new PrintWriter("output3.txt");

		for (int test_case = 1; test_case <= 10; test_case++) {
			/*
			   각 테스트 케이스를 파일에서 읽어옵니다.
               첫 번째 행에 쓰여진 문자열의 길이를 n에 읽어들입니다.
               그 다음 행에 쓰여진 문자열을 s에 한번에 읽어들입니다.
			 */
			stk = new StringTokenizer(br.readLine());
			n = Integer.parseInt(stk.nextToken());
			s = br.readLine();

			Answer = 0;
			int [][] hlong = new int[n+1][n]; //length : 1~n, start : 0~n-1
			boolean [][] isPal = new boolean[n+1][n];
			char [] ch = s.toCharArray();

			for(int i=0; i<n; i++){
				isPal[0][i] = true;
				hlong[0][i] = 0;
				isPal[1][i] = true;
				hlong[1][i] = 1;
			}

			for(int length = 2; length <= n; length++){
				for(int start = 0; start < n; start++){
					if(start + length > n)
						break;
					if(ch[start] == ch[start + length - 1]){
							isPal[length][start] = true;
							hlong[length][start] = hlong[length-2][start+1] + 2;

					}
					else {
						hlong[length][start] = max(hlong[length-1][start] , hlong[length-1][start+1]);
					}
				}
			}
			// 시간복잡도 n^2
			// 최장 공통 부분순서와 비슷한 방법으로 실행된다.
			// 양쪽이 같으면 그 사이 것(길이 -2의 문제)의 최대 길이 + 2가 된다.
			// 아니라면 길이-1 의 문제 두개 중 최대가 답이다.
			Answer = hlong[n][0];


			// output4.txt로 답안을 출력합니다.
			pw.println("#" + test_case + " " + Answer);
			/*
			   아래 코드를 수행하지 않으면 여러분의 프로그램이 제한 시간 초과로 강제 종료 되었을 때,
			   출력한 내용이 실제로 파일에 기록되지 않을 수 있습니다.
			   따라서 안전을 위해 반드시 flush() 를 수행하시기 바랍니다.
			 */
			pw.flush();
		}

		br.close();
		pw.close();
	}
}

