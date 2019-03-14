import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.lang.String;

/*
   1. 아래와 같은 명령어를 입력하면 컴파일이 이루어져야 하며, Solution4 라는 이름의 클래스가 생성되어야 채점이 이루어집니다.
       javac Solution2.java -encoding UTF8


   2. 컴파일 후 아래와 같은 명령어를 입력했을 때 여러분의 프로그램이 정상적으로 출력파일 output4.txt 를 생성시켜야 채점이 이루어집니다.
       java Solution2

   - 제출하시는 소스코드의 인코딩이 UTF8 이어야 함에 유의 바랍니다.
   - 수행시간 측정을 위해 다음과 같이 time 명령어를 사용할 수 있습니다.
       time java Solution2
   - 일정 시간 초과시 프로그램을 강제 종료 시키기 위해 다음과 같이 timeout 명령어를 사용할 수 있습니다.
       timeout 0.5 java Solution2   // 0.5초 수행
       timeout 1 java Solution2     // 1초 수행
 */

class Solution2 {

	/*
		** 주의사항
		정답의 숫자가 매우 크기 때문에 답안은 반드시 int 대신 long 타입을 사용합니다.
		그렇지 않으면 overflow가 발생해서 0점 처리가 됩니다.
		Answer[0]을 a의 개수, Answer[1]을 b의 개수, Answer[2]를 c의 개수라고 가정했습니다.
	*/
    static int n;                           // 문자열 길이
    static String s;                        // 문자열
	static long[] Answer = new long[3];     // 정답

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("input2.txt"));
		StringTokenizer stk;
		PrintWriter pw = new PrintWriter("output2.txt");

		for (int test_case = 1; test_case <= 10; test_case++) {

			stk = new StringTokenizer(br.readLine());
			n = Integer.parseInt(stk.nextToken());
			s = br.readLine();

			Answer[0] = 0;  // a 의 갯수
			Answer[1] = 0;  // b 의 갯수
			Answer[2] = 0;  // c 의 갯수

			long [][][] Data = new long[n+1][n][3];

			for(int i=0; i<n; i++){
				char temp = s.charAt(i);
				if(temp == 'a')
					Data[1][i][0]++;
				else if(temp == 'b')
					Data[1][i][1]++;
				else
					Data[1][i][2]++;
			}

			for(int length = 2; length <= n; length++){
				for(int start=0; start <n; start++){
					if(length + start > n)
						break;
					for(int b = 1; b <length; b++){
						Data[length][start][0] += Data[b][start][0] * Data[length-b][start+b][2]
								+ Data[b][start][1] * Data[length-b][start+b][2]
								+ Data[b][start][2] * Data[length-b][start+b][0];
						Data[length][start][1] += Data[b][start][0] * Data[length-b][start+b][0]
								+ Data[b][start][0] * Data[length-b][start+b][1]
								+ Data[b][start][1] * Data[length-b][start+b][1];
						Data[length][start][2] += Data[b][start][1] * Data[length-b][start+b][0]
								+ Data[b][start][2] * Data[length-b][start+b][1]
								+ Data[b][start][2] * Data[length-b][start+b][2];
					}
				}
			} // 시간복잡도 n^3
			// 길이가 짧은 것 부터 Bottom-up 방식으로 쌓아 올려간다.
			// 모든 시작점에서 모든 길이에 대해 접근하다 보니 n^3의 시간복잡도가 생겼다.

			Answer[0] = Data[n][0][0];
			Answer[1] = Data[n][0][1];
			Answer[2] = Data[n][0][2];

			pw.println("#" + test_case + " " + Answer[0] + " " + Answer[1] + " " + Answer[2]);

			pw.flush();
		}

		br.close();
		pw.close();
	}
}

