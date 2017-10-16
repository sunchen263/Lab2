
public class MyGraph {
	
	class Edges{
		public int Cost;
		public boolean Visited; 
		public Edges(){
			this.Cost = 0;
			this.Visited = false;
		}
	}
	
//	private int[][] edges;		
	private	Edges[][] edges;
	private int vertexNum;
	
	private int[][] D;
	private int[][] P;
	private boolean FloydFlag;		//判断是否经过floyd算法处理过
	
	public MyGraph(int vertices)
	{
		vertexNum = vertices;
		
	//	edges = new int[vertices][vertices];
		edges = new Edges[vertices][vertices];
		for(int i=0; i<vertices; i++)
			for(int j=0; j<vertices; j++)
			{
				edges[i][j] = new Edges();
			//	edges[i][j].Cost = 0;
			//	edges[i][j] = 0;
			}
		FloydFlag = false;
		
	}
	
	public void setEdge(int i, int j, int cost)
	{
	//	edges[i][j] = cost;
		edges[i][j].Cost = cost;
	}
	
	public int getEdge(int i, int j)
	{
	//	return edges[i][j];
		return edges[i][j].Cost;
	}
	
	public int[] getBridge(int head, int tail)
	{
		int[] bridgeList;
		int num = 0, j = 0;
		for(int i=0; i<edges.length; i++)
			if(head!=i && tail!=i && edges[head][i].Cost!=0 && edges[i][tail].Cost!=0)
		//	if(head!=i && tail!=i && edges[head][i]!=0 && edges[i][tail]!=0)
				num++;
		
		bridgeList = new int[num];
		
		for(int i=0; i<edges.length; i++)
		//	if(head!=i && tail!=i && edges[head][i]!=0 && edges[i][tail]!=0)
			if(head!=i && tail!=i && edges[head][i].Cost!=0 && edges[i][tail].Cost!=0)
				bridgeList[j++] = i;
		
		return bridgeList;
	}
	
	public int[] getOutDegree(int v)
	{
	//	for(int i = 0; i<vertexNum; i++)		//重新初始化visited
	//		for(int j = 0; j<vertexNum; j++)
	//			edges[i][j].Visited = false;
		
		if(v >= vertexNum)	return null;
		
		int count1 = 0;
		for(int i = 0; i<vertexNum; i++)
			if(edges[v][i].Cost != 0)
		//	if(edges[v][i] != 0)
				count1++;				//此处count用来计算出度
		int[] outDegreeList = new int[count1];
		
		int count2 = 0;					//此处count用来给outDegreeList添加元素
		for(int i = 0; i<vertexNum; i++)
			if(edges[v][i].Cost != 0)			//0  or integer.max
				outDegreeList[count2++] = i;//edges[v][i].Cost;
		
		return outDegreeList;
	}
	
	public boolean IsVisited(int head, int tail)
	{
		if(head>=vertexNum || tail>=vertexNum)
			return false;
		
		return edges[head][tail].Visited;
	}
	
	public void setVisited(int head, int tail, boolean flag)
	{
		edges[head][tail].Visited = flag;
	}
	
	public void removeVisited()
	{
		for(int i = 0; i<vertexNum; i++)
			for(int j = 0; j<vertexNum; j++)
				edges[i][j].Visited = false;
	}
	
	private void Floyd()
	{
		P = new int[vertexNum][vertexNum];
		D = new int[vertexNum][vertexNum];
		
	     for (int i = 0; i < vertexNum; i++) {//初始化D，p  
	         for (int j = 0; j < vertexNum; j++) {  
	             if (edges[i][j].Cost != 0){
	                 P[i][j] = j;
	                 D[i][j] = edges[i][j].Cost;
	            //     P[i][j] = i==j ? j : -1;
	             }
	             else{
	            	 P[i][j] = i==j ? j : -1;
	             //    P[i][j] = -1;  
	                 D[i][j] = i==j ? 0 : Integer.MAX_VALUE;
	             }
	         }  
	     }  
	  
	     for (int x = 0; x < vertexNum; x++) {//进行Floyd算法，从0到n-1所有可能进行遍历  
	         for (int i = 0; i < vertexNum; i++) {  
	             for (int j = 0; j < vertexNum; j++) {  
	                 if (D[i][x] != Integer.MAX_VALUE && D[x][j] != Integer.MAX_VALUE &&
	                		 D[i][j] > D[i][x] + D[x][j]) {  
	                     D[i][j] = D[i][x] + D[x][j];  
	                     P[i][j] = P[i][x];  
	                 }  
	             }  
	         }  
	     }
	     
	     FloydFlag = true;
	}
	
	public int[] getDisPath(int source, int target)
	{
		if(!FloydFlag)
			Floyd();
		
		if(source >= vertexNum || target >= vertexNum || P[source][target] == -1)
			return null;
		
		if(D[source][target] == Integer.MAX_VALUE)		//无路径
			return null;
		
		int k = P[source][target];
		
		int count = 0;					//******
		while(k != target){
			k = P[k][target];
			count++;
		}
		int path[] = new int[count+2];	//此段是计数
		
		k = P[source][target];  count = 0;
		path[count++] = source;
		while(k != target){
			path[count++] = k;
			k = P[k][target];
		//	path[count++] = k;
		}
		path[count] = target;
		
		return path;
	}
	
	public int getDistance(int source, int target)
	{
		if(!FloydFlag)
			Floyd();
		
		return D[source][target];
	}
}


/*
public class MyGraph {
	
	class Edges{
		public int Cost;
		public boolean Visited; 
	}
	
	private int[][] edges;		
//	Edges[][] edges;
	private int vertexNum;
	
	private int[][] D;
	private int[][] P;
	private boolean FloydFlag;		//判断是否经过floyd算法处理过
	
	public MyGraph(int vertices)
	{
		vertexNum = vertices;
		
		edges = new int[vertices][vertices];
	//	edges = new Edges[vertices][vertices];
		for(int i=0; i<vertices; i++)
			for(int j=0; j<vertices; j++)
			//	edges[i][j].Cost = 0;	//
				edges[i][j] = 0;
		
		FloydFlag = false;
		
	}
	
	public void setEdge(int i, int j, int cost)
	{
		edges[i][j] = cost;
	//	edges[i][j].Cost = cost;
	}
	
	public int getEdge(int i, int j)
	{
		return edges[i][j];
	//	return edges[i][j].Cost;
	}
	
	public int[] getBridge(int head, int tail)
	{
		int[] bridgeList;
		int num = 0, j = 0;
		for(int i=0; i<edges.length; i++)
		//	if(head!=i && tail!=i && edges[head][i].Cost!=0 && edges[i][tail].Cost!=0)
			if(head!=i && tail!=i && edges[head][i]!=0 && edges[i][tail]!=0)
				num++;
		
		bridgeList = new int[num];
		
		for(int i=0; i<edges.length; i++)
			if(head!=i && tail!=i && edges[head][i]!=0 && edges[i][tail]!=0)
		//	if(head!=i && tail!=i && edges[head][i].Cost!=0 && edges[i][tail].Cost!=0)
				bridgeList[j++] = i;
		
		return bridgeList;
	}
	
	public int[] getOutDegree(int v)
	{
		if(v >= vertexNum)	return null;
		
		int count1 = 0;
		for(int i = 0; i<vertexNum; i++)
		//	if(edges[v][i].Cost != 0)
			if(edges[v][i] != 0)
				count1++;				//此处count用来计算出度
		int[] outDegreeList = new int[count1];
		
		int count2 = 0;					//此处count用来给outDegreeList添加元素
		for(int i = 0; i<vertexNum; i++)
			if(edges[v][i] != 0)			//0  or integer.max
				outDegreeList[count2++] = edges[v][i];
		
		return outDegreeList;
	}
	
	private void Floyd()
	{
		P = new int[vertexNum][vertexNum];
		D = new int[vertexNum][vertexNum];
		
	     for (int i = 0; i < vertexNum; i++) {//初始化D，p  
	         for (int j = 0; j < vertexNum; j++) {  
	             if (edges[i][j] != 0){
	                 P[i][j] = j;
	                 D[i][j] = edges[i][j];
	            //     P[i][j] = i==j ? j : -1;
	             }
	             else{
	            	 P[i][j] = i==j ? j : -1;
	             //    P[i][j] = -1;  
	                 D[i][j] = i==j ? 0 : Integer.MAX_VALUE;
	             }
	         }  
	     }  
	  
	     for (int x = 0; x < vertexNum; x++) {//进行Floyd算法，从0到n-1所有可能进行遍历  
	         for (int i = 0; i < vertexNum; i++) {  
	             for (int j = 0; j < vertexNum; j++) {  
	                 if (D[i][x] != Integer.MAX_VALUE && D[x][j] != Integer.MAX_VALUE &&
	                		 D[i][j] > D[i][x] + D[x][j]) {  
	                     D[i][j] = D[i][x] + D[x][j];  
	                     P[i][j] = P[i][x];  
	                 }  
	             }  
	         }  
	     }
	     
	     FloydFlag = true;
	}
	
	public int[] getDisPath(int source, int target)
	{
		if(!FloydFlag)
			Floyd();
		
		if(source >= vertexNum || target >= vertexNum || P[source][target] == -1)
			return null;
		
		if(D[source][target] == Integer.MAX_VALUE)		//无路径
			return null;
		
		int k = P[source][target];
		
		int count = 0;					//******
		while(k != target){
			k = P[k][target];
			count++;
		}
		int path[] = new int[count+2];	//此段是计数
		
		k = P[source][target];  count = 0;
		path[count++] = source;
		while(k != target){
			path[count++] = k;
			k = P[k][target];
		//	path[count++] = k;
		}
		path[count] = target;
		
		return path;
	}
	
	public int getDistance(int source, int target)
	{
		if(!FloydFlag)
			Floyd();
		
		return D[source][target];
	}
}


*/






