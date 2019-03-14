import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

/*
   1. 아래와 같은 명령어를 입력하면 컴파일이 이루어져야 하며, Solution5 라는 이름의 클래스가 생성되어야 채점이 이루어집니다.
       javac Solution5.java -encoding UTF8


   2. 컴파일 후 아래와 같은 명령어를 입력했을 때 여러분의 프로그램이 정상적으로 출력파일 output5.txt 를 생성시켜야 채점이 이루어집니다.
       java Solution5

   - 제출하시는 소스코드의 인코딩이 UTF8 이어야 함에 유의 바랍니다.
   - 수행시간 측정을 위해 다음과 같이 time 명령어를 사용할 수 있습니다.
       time java Solution5
   - 일정 시간 초과시 프로그램을 강제 종료 시키기 위해 다음과 같이 timeout 명령어를 사용할 수 있습니다.
       timeout 0.5 java Solution5   // 0.5초 수행
       timeout 1 java Solution5     // 1초 수행
 */

class Solution5 {
	static final int max_n = 1000;

	static int n, H;
	static int[] h = new int[max_n], d = new int[max_n-1];
	static int Answer;

	public static void main(String[] args) throws Exception {
		/*
		   동일 폴더 내의 input5.txt 로부터 데이터를 읽어옵니다.
		   또한 동일 폴더 내의 output5.txt 로 정답을 출력합니다.
		 */
		BufferedReader br = new BufferedReader(new FileReader("input5.txt"));
		StringTokenizer stk;
		PrintWriter pw = new PrintWriter("output5.txt");

		/*
		   10개의 테스트 케이스가 주어지므로, 각각을 처리합니다.
		 */
		for (int test_case = 1; test_case <= 10; test_case++) {
			/*
			   각 테스트 케이스를 표준 입력에서 읽어옵니다.
			   먼저 블록의 개수와 최대 높이를 각각 n, H에 읽어들입니다.
			   그리고 각 블록의 높이를 h[0], h[1], ... , h[n-1]에 읽어들입니다.
			   다음 각 블록에 파인 구멍의 깊이를 d[0], d[1], ... , d[n-2]에 읽어들입니다.
			 */
			stk = new StringTokenizer(br.readLine());
			n = Integer.parseInt(stk.nextToken()); H = Integer.parseInt(stk.nextToken());
			stk = new StringTokenizer(br.readLine());
			for (int i = 0; i < n; i++) {
				h[i] = Integer.parseInt(stk.nextToken());
			}
			stk = new StringTokenizer(br.readLine());
			for (int i = 0; i < n-1; i++) {
				d[i] = Integer.parseInt(stk.nextToken());
			}


			/////////////////////////////////////////////////////////////////////////////////////////////
			/*
			   이 부분에서 여러분의 알고리즘이 수행됩니다.
			   문제의 답을 계산하여 그 값을 Answer에 저장하는 것을 가정하였습니다.
			 */
			/////////////////////////////////////////////////////////////////////////////////////////////
			Answer = 0;
			int [] possible_O = new int[H+1]; // 각각 높이에 몇가지 경우의 수가 가능한지
			int [] possible_X = new int[H+1];
			int [] Otemp = new int[H+1];
			int [] Xtemp = new int[H+1];

			if(h[0] <= H)
				possible_O[h[0]]++;

			for(int start =1; start < n ; start++){

				if(h[start] <= H)
					Otemp[h[start]]++;

				for(int i=0; i<=H ; i++){
					int increasedO = i + h[start] - d[start-1];
					int notincreasedO = i + h[start]; // 현재 높이(i)에서 가능한 경우의 수 - 연속 & 불연속
					if(increasedO <= H)
					{
						Otemp[increasedO] += possible_O[i];
						if(notincreasedO <= H)
							Otemp[notincreasedO] += possible_X[i];
					}
					Xtemp[i] = possible_O[i] + possible_X[i]; // 안놓은경우 -> 그대롤 옮김
				}

					for(int i=0; i<=H; i++) {
						possible_O[i] = Otemp[i] % 1000000;
						possible_X[i] = Xtemp[i] % 1000000;
						Otemp[i] = 0;
						Xtemp[i] = 0;// temp 값을 원래 값으로 옮겨준다.
					}

			}
			for(int i=0; i<H+1; i++){
				Answer += ((possible_O[i] + possible_X[i]));
				Answer %= 1000000;
			}

			// 시간 복잡도는 빅 세타 nH이다.
			// 높이 H로 가능한 경우의 수를 길이 H 배열을 이용하여 카운팅 하고,
			// 길이를 1부터 n까지 늘려가며 다이나믹 프로그래밍을 이용하여 해결하면 n번의 루프로 정답을 얻을 수 있다.
			// output5.txt로 답안을 출력합니다.
			pw.println("#" + test_case + " " + Answer);

			pw.flush();
		}

		br.close();
		pw.close();
	}
}

