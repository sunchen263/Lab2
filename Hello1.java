import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Hello1 {
		private static String txtStr;
		private static String[] strList; 
		private static String[] wordList;
		private static int wordNum;
		
		private static MyGraph g;
		private static Map map;
		
		private static String[] strSplit(String txtStr_this)
		{
			StringBuffer tmpStr = new StringBuffer();
			
			for(int i=0; i<txtStr_this.length(); i++)
			{
				char c = txtStr_this.charAt(i);
				if(c>='A' && c<='Z')
					tmpStr.append((char)(c+32));	//大写字母转小写
				else if(c>='a' && c<='z')
					tmpStr.append(c);
				else
					tmpStr.append(' ');
			}
			
		//	strList = (new String(tmpStr)).split("     |    |   |  | ");
			return (new String(tmpStr)).split("     |    |   |  | ");
		}
		
		private static boolean GetFile(String fileName) throws IOException
		{
			File rf0 = new File(fileName);		//判断文件是否存在
			if(!rf0.exists())
				return false;
			
			FileInputStream rf = new FileInputStream(fileName);
			
			byte[] buffer = new byte[rf.available()];	//读取到文件尾
			while(rf.read(buffer)!=-1)
			{
			//	System.out.print(new String(buffer));
				continue;
			}
			txtStr = new String(buffer);
		//	System.out.print(txtStr);		//
			rf.close();
			
			return true;
		}
		
		public static String GetTxtStr()
		{
			return txtStr;
		}
		
		private static void setGraph()
		{
		//	Map<String,int> map = new HashMap<String,int>();
			map = new HashMap();
			
			wordNum = 0;
			wordList = new String[txtStr.length()];
			for(int i = 0; i<strList.length; i++)
			{
				if(!map.containsKey(strList[i]))
				{
					map.put(strList[i], wordNum);
					wordList[wordNum] = strList[i];
					wordNum++;
					
				}
			}
			
			g = new MyGraph(wordNum);
			for(int i=0; i<strList.length-1; i++)
			{
				int v0 = (int)(map.get(strList[i]));
				int v1 = (int)(map.get(strList[i+1]));
				int cost = g.getEdge(v0, v1);
				
				g.setEdge(v0, v1, cost+1);
			}
		//	return g;
					
		}
		
/*		private static String[] getBridgeWords(String word1, String word2)
		{
			String[] bridgeWordList;
			
			if(!map.containsKey(word1) || !map.containsKey(word2)){
				bridgeWordList = new String[0];
			}
			
			
				
		}
*/
		public static String generateNewText(String inputText)
		{
			String[] inputTxtList = Hello1.strSplit(inputText);
			StringBuffer newString = new StringBuffer();
			
			if(inputTxtList.length == 1) return inputTxtList[0];	//只有一个单词的情况
			
			int i;
			for(i = 0; i<inputTxtList.length - 1; i++){
				String word1 = inputTxtList[i];
				String word2 = inputTxtList[i+1];
				newString.append(word1+" ");
				
				if(map.containsKey(word1) && map.containsKey(word2)){
					int[] bridgeList =g.getBridge((int)map.get(word1), (int)map.get(word2));
					if(bridgeList.length != 0){
						newString.append(wordList[bridgeList[0]]+" ");
					}
						
				}
			}
			newString.append(inputTxtList[i]);
			
			return (new String(newString));
		}
		
		public static String queryBridgeWords(String word1, String word2)
		{
			String strReturn = "";
		//	int v0, v1;
			
			if(!map.containsKey(word1) && !map.containsKey(word2))
			{
				strReturn = "No \"" + word1 + "\" and \"" + word2 +
						"\" in the graph!\r\n";
				return strReturn;
			}
			
			if(!map.containsKey(word1))
			{
				strReturn = "No \"" + word1 + "\" in the graph!\r\n";
				return strReturn;
			}
			
			if(!map.containsKey(word2))
			{
				strReturn = "No \"" + word2 + "\" in the graph!\r\n";
				return strReturn;
			}
			
			int v0 = (int)(map.get(word1));
			int v1 = (int)(map.get(word2));
			int[] bridgeList = g.getBridge(v0, v1);
			
			if(bridgeList.length == 0){
				strReturn = strReturn + "No bridge words from " + 
						"\"" + word1 + "\" to \"" +  word2 + "\"!" + "\r\n";
				return strReturn;
			}
			
			strReturn = strReturn + "The bridge words from \"" + word1 + "\" to \"" +
						word2 + "\" is: ";
			for(int i = 0; i<bridgeList.length; i++)
				strReturn = strReturn + wordList[bridgeList[i]] + " ";
			strReturn = strReturn + "\r\n";
			
			return strReturn;
		}
		
		public static void showDirectedGraph(String pathDir) throws IOException
		{
			 File DotFile = new File(pathDir + "Graph.dot");
			 FileWriter NewFile = new FileWriter(DotFile);
             NewFile.write("digraph abc{\r\n\tnode [shape=\"record\"];\r\n\t");
             
             for(int i = 0; i<wordNum; i++)
            	 NewFile.write(wordList[i]+";\r\n\t");
             
             for(int i = 0; i<wordNum; i++)
            	 for(int j = 0; j<wordNum; j++)
            		 if(g.getEdge(i, j)!=0)
            			 NewFile.write(wordList[i]+"->"+wordList[j]
            					 +" [ label = \""+g.getEdge(i, j)+"\" ];\r\n\t");
             NewFile.write("}");
             NewFile.close();
             
          //   Runtime rt = Runtime.getRuntime();
          //   Process p = rt.exec("cmd.exe /c dot d:/Graph.dot -T png -o d:/1.png");
          //   p = rt.exec("cmd.exe /c d:/1.png");
           //  System.out.println(p.toString());
		}
		
		public static String calcShortestPath(String word1, String word2)
		{
			if(!map.containsKey(word1) || !map.containsKey(word2))
				return "Words not exist!\r\n";
			
			int v0 = (int)map.get(word1);
			int v1 = (int)map.get(word2);
			
			int[] path = g.getDisPath(v0, v1);
			
			if(path==null)		//两个单词之间无路径
				return "No path from "+"\""+word1+"\" to \""+word2+"\"!\r\n";
			
			String pathStr = "";
			int i;
			for(i = 0; i<path.length - 1; i++){
				pathStr = pathStr + wordList[path[i]] + " -> ";
			}
			pathStr = pathStr + wordList[path[i]] + "\r\n";
			
			return pathStr;
			
		}
		
		public static String calcShortestPath(String word)
		{
			if(!map.containsKey(word))
				return "Words not exist!\r\n";
			
			int v = (int)map.get(word);
		//	int v1 = (int)map.get(word2);
			String pathStr = "";
			for(int i = 0; i<wordNum; i++){
			//	if(g.getEdge(v, i)==0)	continue;
				
				int[] path = g.getDisPath(v, i);
				if(path==null)	continue;	//两单词之间无路径
				
				pathStr = pathStr + wordList[v] + " to " + wordList[i] + ": ";
				int j;
				for(j = 0; j<path.length - 1; j++){
					pathStr = pathStr + wordList[path[j]] + " -> ";
				}
				pathStr = pathStr + wordList[path[j]] + "\r\n\n";
				
			}
			
			return pathStr;
			
		}
		
		public static String randomWalk()
		{
			g.removeVisited();
			
			String walkStr = "";
			Random r = new Random();
			
			int index1 = r.nextInt(wordNum);	//生成随机游走起点
			walkStr = walkStr + wordList[index1] + " ";
			do{
				int[] outDegreeList = g.getOutDegree(index1);	//获得该顶点出度
				if(outDegreeList.length == 0)				//如果
					break;
				
				int index2 = outDegreeList[r.nextInt(outDegreeList.length)];
						//从outDegreeList中随机选出一个元素
				walkStr = walkStr + wordList[index2] + " ";
				
				if(g.IsVisited(index1, index2))
					break;
				
				g.setVisited(index1, index2, true);
				
				index1  = index2;
			}while(true);
			
			return walkStr + "\r\n";
		}
		
		public static void InitMain(String path) throws IOException
		{
			GetFile(path);
			strList = strSplit(txtStr);
			setGraph();
/*
  		System.out.println("Input:");
		//	byte[] buffer = new byte[512];
		//	System.in.read(buffer);
		//	System.out.println(new String(buffer));
			
			Scanner sc = new Scanner(System.in);
			String s=sc.nextLine();
			
			while(!GetFile(s)){
				System.out.println("File not exists!");
			//	System.in.read(buffer);
				s = sc.nextLine();
			}
			
		//	Scanner sc = new Scanner(System.in);
		//	String s=sc.nextLine();
			
			strList = strSplit(txtStr);
			for(int i = 0; i<strList.length; i++)
				System.out.println(strList[i]);
			
			setGraph();
			System.out.println(g.getEdge(0, 1));
			
		//	System.out.println("two words:");
		//	String s1 = sc.nextLine();  String s2 = sc.nextLine();
		//	System.out.println(queryBridgeWords(s1, s2));
			
			showDirectedGraph();
			
		//	String newtxt = sc.nextLine();		//验证功能4
		//	System.out.println(generateNewText(newtxt));//generateNewText(newtxt);
			
		//	String wd1 = sc.nextLine();
		//	String wd2 = sc.nextLine();
		//	System.out.println(calcShortestPath(wd1, wd2));
			
	//		MyAwt A = new MyAwt();		
	//		A.getAWT();
			
			
			while(true){
				System.out.println(randomWalk());
				sc.nextLine();
			}
		//	sc.close();
*/		
		//	MyAwt A = new MyAwt();		
		//	A.getAWT();

/*			
			MyGraph tg = new MyGraph(4);
			tg.setEdge(0, 1, 5);
			tg.setEdge(0, 3, 7);
			tg.setEdge(1, 2, 4);
			tg.setEdge(1, 3, 2);
			tg.setEdge(2, 0, 3);
			tg.setEdge(2, 1, 3);
			tg.setEdge(2, 3, 2);
			tg.setEdge(3, 2, 1);
			System.out.println(tg.getDistance(0, 2));
			System.out.println(tg.getDistance(1, 0));
*/			

/*			MyGraph tg = new MyGraph(4);
			tg.setEdge(0, 1, 2);
			tg.setEdge(0, 2, 6);
			tg.setEdge(0, 3, 4);
			tg.setEdge(1, 2, 3);
			tg.setEdge(2, 0, 7);
			tg.setEdge(2, 3, 1);
			tg.setEdge(3, 0, 5);
			tg.setEdge(3, 2, 12);
			System.out.println(tg.getDistance(0, 2));
			System.out.println(tg.getDistance(1, 0));
			System.out.println(tg.getDisPath(1, 0)[0]);
			System.out.println(tg.getDisPath(1, 0)[1]);
			System.out.println(tg.getDisPath(1, 0)[2]);
			System.out.println(tg.getDisPath(1, 0)[3]);
*/		
		}		
}
