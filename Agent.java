import java.io.*;
import java.util.*;
import java.lang.*;

public class Agent {

	
	public static void main(String []args) throws IOException{
		int searchtask;
		int depth;
		String initmax = null;	
		Scanner s = new Scanner (new BufferedReader(new FileReader("input.txt")));
		
		// Extract data from input.txt
		searchtask = s.nextInt();	
		initmax = s.next();
		depth = s.nextInt();
		
		// Copy initial player position in a linear array
		String[] init = new String[97];    // Add extra padding of 16 before and after the array to avoid JAVA exceptions
		for(int i=0;i<=96;i++){
			init[i]="*";
		}
		int k=17;
		for(int i=1;i<=8;i++){
			String temp = s.next();
			for(int j=1;j<=8;j++){
				init[k] = String.valueOf(temp.charAt(j-1));
				if(init[k].equals("o"))
					init[k] = "O";
				if(init[k].equals("x"))
					init[k] = "X";
				k++;
			}
		}
		
		
		//Build the positional weights table
		int[] eval = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,99,-8,8,6,6,8,-8,99,-8,-24,-4,-3,-3,-4,-24,-8,8,-4,7,4,4,7,-4,8,6,-3,4,0,0,4,-3,6,6,-3,4,0,0,4,-3,6,8,-4,7,4,4,7,-4,8,-8,-24,-4,-3,-3,-4,-24,-8,99,-8,8,6,6,8,-8,99,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		
		
		
		
		
		if(searchtask==1)
			Greedy(initmax,init,eval);
		else if(searchtask==2)
			Minimax(initmax,init,eval,depth);
		else if(searchtask==3)
			Alphabeta(initmax,init,eval,depth);
		s.close();
	}
	
	// Implement Greedy method
	public static void Greedy(String currentpl,String[] initpos,int[] evaluate) throws IOException{
		String max = currentpl;
		final String[] init = initpos;
		final int[] eval = evaluate;
		final PrintWriter out = new PrintWriter(new FileWriter("output.txt"));
		String min;
		if(max.equals("X"))
			min = "O";
		else if(max.equals("O"))
			min = "X";
		else min = "";
		
		
		class compute{
			// method to compute all possible moves next to min color
			
			boolean contains(int[] x,int y){
				for(int i:x){
					if(i==y)
						return true;
				}
				return false;	
			}
			
			String[] ImplementMove(String[] x,int i, String minimum, String maximum){
				String[] init = x;
				String[] ninit = init.clone();
				String max = maximum;
				String min = minimum;
				
				
				int j=i;
				int[] rhori = new int[] {24,32,40,48,56,64,72,80};
				j++;
				if(init[j].equals(min) && !contains(rhori,i)){       // check only if the first neighbor is a min
					while ((!contains(rhori,j) || init[j].equals(max)) && !init[j].equals("*")){
						if(init[j].equals(max)){
							for(int k=j-1;k>i;k--){
								ninit[k]=max;
							}break;
						}
						else
							j++;
					}
				}
				
				
				//check left horizontals
				j=i;
				int[] lhori = new int[] {17,25,33,41,49,57,65,73};
				j--;
				if(init[j].equals(min) && !contains(lhori,i)){       // check only if the first neighbor is a min
					while ((!contains(lhori,j) || init[j].equals(max)) && !init[j].equals("*")){
						if(init[j].equals(max)){
							for(int k=j+1;k<i;k++){
								ninit[k]=max;
							}break;
						}
						else
							j--;
					}	
				}
				
				
				
				//check top vertical
				j=i;
				int[] tverti = new int[] {17,18,19,20,21,22,23,24};
				j-=8;
				if(init[j].equals(min) && !contains(tverti,i)){       // check only if the first neighbor is a min
					while ((!contains(tverti,j) || init[j].equals(max)) && !init[j].equals("*")){
						if(init[j].equals(max)){
							for(int k=j+8;k<i;k+=8){
								ninit[k]=max;
							}break;	
						}
						else
							j-=8;
					}	
				}
				
				
				//check bottom vertical
				j=i;
				int[] bverti = new int[] {73,74,75,76,77,78,79,80};
				j+=8;
				if(init[j].equals(min) && !contains(bverti,i)){       // check only if the first neighbor is a min
					while ((!contains(bverti,j) || init[j].equals(max)) && !init[j].equals("*")){
						if(init[j].equals(max)){
							for(int k=j-8;k>i;k-=8){
								ninit[k]=max;
							}break;	
						}
						else
							j+=8;
					}	
				}
				
				
				//check top right diagonal
				j=i;
				int[] trdiag = new int[] {17,18,19,20,21,22,23,24,32,40,48,56,64,72,80};
				j-=7;
				if(init[j].equals(min) && !contains(trdiag,i)){       // check only if the first neighbor is a min
					while ((!contains(trdiag,j) || init[j].equals(max)) && !init[j].equals("*")){
						if(init[j].equals(max)){
							for(int k=j+7;k<i;k+=7){
								ninit[k]=max;
							}break;
						}
						else
							j-=7;
					}	
				}
				
				
				//check bottom left diagonal
				j=i;
				int[] bldiag = new int[] {17,25,33,41,49,57,65,73,74,75,76,77,78,79,80};
				j+=7;
				if(init[j].equals(min) && !contains(bldiag,i)){       // check only if the first neighbor is a min
					while ((!contains(bldiag,j) || init[j].equals(max)) && !init[j].equals("*")){
						if(init[j].equals(max)){
							for(int k=j-7;k>i;k-=7){
								ninit[k]=max;
							}break;
						}
						else
							j+=7;
					}	
				}
				
				
				//check top left diagonal
				j=i;
				int[] tldiag = new int[] {17,18,19,20,21,22,23,24,25,33,41,49,57,65,73};
				j-=9;
				if(init[j].equals(min) && !contains(tldiag,i)){       // check only if the first neighbor is a min
					while ((!contains(tldiag,j) || init[j].equals(max)) && !init[j].equals("*")){
						if(init[j].equals(max)){
							for(int k=j+9;k<i;k+=9){
								ninit[k]=max;
							}break;
						}
						else
							j-=9;
					}	
				}
				
				
				//check bottom right diagonal
				j=i;
				int[] brdiag = new int[] {24,32,40,48,56,64,72,73,74,75,76,77,78,79,80};
				j+=9;
				if(init[j].equals(min) && !contains(brdiag,i)){       // check only if the first neighbor is a min
					while ((!contains(brdiag,j) || init[j].equals(max)) && !init[j].equals("*")){
						if(init[j].equals(max)){
							for(int k=j-9;k>i;k-=9){
								ninit[k]=max;
							}break;
						}
						else
							j+=9;
					}	
				}
				ninit[i]=max;
				return ninit;
			}
			
			
			int EvalFunction(String[] l,String minimum, String maximum){
				String[] x = l;
				int value = 0;
				for(int i=17;i<=80;i++){
					if(x.equals(maximum))
						value += eval[i];
					else if (x.equals(minimum))
						value -= eval[i];
				}
				return value;
			}
			
			
			ArrayList<Integer> NextPossibleMoves(String maxvalue){
				String max = maxvalue;
				String min;
				if(max.equals("X"))
					min = "O";
				else if(max.equals("O"))
					min = "X";
				else min = "";
				
				ArrayList<Integer> nextmoves = new ArrayList<Integer>();
				for(int i=17;i<=80;i++){
					if(init[i].equals("*")){    // check all the empty places for next move feasibility
						//check right horizontals
						int j=i;
						int[] rhori = new int[] {24,32,40,48,56,64,72,80};
						j++;
						if(init[j].equals(min) && !contains(rhori,i)){       // check only if the first neighbor is a min
							while ((!contains(rhori,j) || init[j].equals(max)) && !init[j].equals("*")){
								int maxs=0;
								if(init[j].equals(max))
									maxs++;
								// add the i spot to possible moves if there is atleast one max to make the move feasible
								if(maxs!=0){
									if(!nextmoves.contains(i)){
										nextmoves.add(i);
									}	
								}
								j++;
							}
						}
						
						
						//check left horizontals
						j=i;
						int[] lhori = new int[] {17,25,33,41,49,57,65,73};
						j--;
						if(init[j].equals(min) && !contains(lhori,i)){       // check only if the first neighbor is a min
							while ((!contains(lhori,j) || init[j].equals(max)) && !init[j].equals("*")){
								int maxs=0;
								if(init[j].equals(max))
									maxs++;
								// add the i spot to possible moves if there is atleast one max to make the move feasible
								if(maxs!=0){
									if(!nextmoves.contains(i)){
										nextmoves.add(i);
									}	
								}
								j--;
							}	
						}
						
						
						
						//check top vertical
						j=i;
						int[] tverti = new int[] {17,18,19,20,21,22,23,24};
						j-=8;
						if(init[j].equals(min) && !contains(tverti,i)){       // check only if the first neighbor is a min
							while ((!contains(tverti,j) || init[j].equals(max)) && !init[j].equals("*")){
								int maxs=0;
								if(init[j].equals(max))
									maxs++;
								
								// add the i spot to possible moves if there is atleast one max to make the move feasible
								if(maxs!=0){
									if(!nextmoves.contains(i)){
										nextmoves.add(i);
									}	
								}
								j-=8;
							}	
						}
						
						
						//check bottom vertical
						j=i;
						int[] bverti = new int[] {73,74,75,76,77,78,79,80};
						j+=8;
						if(init[j].equals(min) && !contains(bverti,i)){       // check only if the first neighbor is a min
							while ((!contains(bverti,j) || init[j].equals(max)) && !init[j].equals("*")){
								int maxs=0;
								if(init[j].equals(max))
									maxs++;
								
								// add the i spot to possible moves if there is atleast one max to make the move feasible
								if(maxs!=0){
									if(!nextmoves.contains(i)){
										nextmoves.add(i);
									}	
								}
								j+=8;
							}	
						}
						
						
						//check top right diagonal
						j=i;
						int[] trdiag = new int[] {17,18,19,20,21,22,23,24,32,40,48,56,64,72,80};
						j-=7;
						if(init[j].equals(min) && !contains(trdiag,i)){       // check only if the first neighbor is a min
							while ((!contains(trdiag,j) || init[j].equals(max)) && !init[j].equals("*")){
								int maxs=0;
								if(init[j].equals(max))
									maxs++;
								
								// add the i spot to possible moves if there is atleast one max to make the move feasible
								if(maxs!=0){
									if(!nextmoves.contains(i)){
										nextmoves.add(i);
									}	
								}
								j-=7;
							}	
						}
						
						
						//check bottom left diagonal
						j=i;
						int[] bldiag = new int[] {17,25,33,41,49,57,65,73,74,75,76,77,78,79,80};
						j+=7;
						if(init[j].equals(min) && !contains(bldiag,i)){       // check only if the first neighbor is a min
							while ((!contains(bldiag,j) || init[j].equals(max)) && !init[j].equals("*")){
								int maxs=0;
								if(init[j].equals(max))
									maxs++;
								
								// add the i spot to possible moves if there is atleast one max to make the move feasible
								if(maxs!=0){
									if(!nextmoves.contains(i)){
										nextmoves.add(i);
									}	
								}
								j+=7;
							}	
						}
						
						
						//check top left diagonal
						j=i;
						int[] tldiag = new int[] {17,18,19,20,21,22,23,24,25,33,41,49,57,65,73};
						j-=9;
						if(init[j].equals(min) && !contains(tldiag,i)){       // check only if the first neighbor is a min
							while ((!contains(tldiag,j) || init[j].equals(max)) && !init[j].equals("*")){
								int maxs=0;
								if(init[j].equals(max))
									maxs++;
								
								// add the i spot to possible moves if there is atleast one max to make the move feasible
								if(maxs!=0){
									if(!nextmoves.contains(i)){
										nextmoves.add(i);
									}	
								}
								j-=9;
							}	
						}
						
						
						//check bottom right diagonal
						j=i;
						int[] brdiag = new int[] {24,32,40,48,56,64,72,73,74,75,76,77,78,79,80};
						j+=9;
						if(init[j].equals(min) && !contains(brdiag,i)){       // check only if the first neighbor is a min
							while ((!contains(brdiag,j) || init[j].equals(max)) && !init[j].equals("*")){
								int maxs=0;
								if(init[j].equals(max))
									maxs++;
								
								// add the i spot to possible moves if there is atleast one max to make the move feasible
								if(maxs!=0){
									if(!nextmoves.contains(i)){
										nextmoves.add(i);
									}	
								}
								j+=9;
							}	
						}	
					}	
				}
				return nextmoves; //returns the list of postitions possible for next move. Retunrs empty string if no move exists.
			}	
		}
		
		compute c = new compute();
		ArrayList<Integer> nextmoves=new ArrayList<Integer>();
		nextmoves = c.NextPossibleMoves(max);
		
		//Check for Pass Move and End Game
		if(nextmoves.isEmpty()){
			String temp = max;            // if no possible moves for MAX, then change the other player to MAX
			max = min;
			min = temp;
			nextmoves= c.NextPossibleMoves(max);
			if(nextmoves.isEmpty()){
				for(int i=17;i<=80;i++){     // If no move exists, Print the given state itself
					out.print(init[i]);
					if ((i%8) == 0)
						out.println();	
				}
				out.close();
				return;
			}
		}
		
		
		
		// Implement Greedy algorithm to choose the best next move
		int next = nextmoves.get(0);
		String[] nextinit = init.clone();
		nextinit = c.ImplementMove(nextinit, next, min, max);
		for(int i:nextmoves){
			String[] tempinit = init.clone();
			tempinit = c.ImplementMove(tempinit, i, min, max);;
			if(c.EvalFunction(tempinit, min, max)<c.EvalFunction(nextinit, min, max)){
				next = i;
			}
		}
		
		String[] newinit = init.clone();
		newinit = c.ImplementMove(newinit, next, min, max);
		
		

		for(int i=17;i<=80;i++){
			out.print(newinit[i]);
			if ((i%8) == 0)
				out.println();	
		}
		out.close();
	}
	
	
	
	public static void Minimax(String currentpl,String[] initpos,int[] evaluate,int dep) throws IOException{
		String max = currentpl;
		String[] init = initpos;
		final int[] eval = evaluate;
		final int depth = dep;
		final PrintWriter out = new PrintWriter(new FileWriter("output.txt"));
		String min;
		if(max.equals("X"))
			min = "O";
		else if(max.equals("O"))
			min = "X";
		else min = "";
		
		
		class compute{
			// method to compute all possible moves next to min color
			ArrayList<Integer> storageList = new ArrayList<Integer>();
			String[] nodename = {"",
					"","","","","","","","",
					"","","","","","","","",
					"a1","b1","c1","d1","e1","f1","g1","h1",
					"a2","b2","c2","d2","e2","f2","g2","h2",
					"a3","b3","c3","d3","e3","f3","g3","h3",
					"a4","b4","c4","d4","e4","f4","g4","h4",
					"a5","b5","c5","d5","e5","f5","g5","h5",
					"a6","b6","c6","d6","e6","f6","g6","h6",
					"a7","b7","c7","d7","e7","f7","g7","h7",
					"a8","b8","c8","d8","e8","f8","g8","h8",
					};
			String log = "";
			

			int next(int mmvalue,String [] l,String maximum){
				ArrayList<Integer> nextmove=new ArrayList<Integer>();
				nextmove = NextPossibleMoves(l,maximum);
				for(int j:nextmove){
					for(int i=0;i<storageList.toArray().length;i+=2){
						if (storageList.get(i) == j){
							if(storageList.get(i+1) == mmvalue){
								return j;
							}
						}	
					}
				}
				return 0;	
			}
			
			
			boolean contains(int[] x,int y){
				for(int i:x){
					if(i==y)
						return true;
				}
				return false;	
			}
			
			String[] ImplementMove(String[] x,int i, String minimum, String maximum){
				String[] init = x;
				String[] ninit = init.clone();
				String max = maximum;
				String min = minimum;
				int j=i;
				int[] rhori = new int[] {24,32,40,48,56,64,72,80};
				j++;
				if(init[j].equals(min) && !contains(rhori,i)){       // check only if the first neighbor is a min
					while ((!contains(rhori,j) || init[j].equals(max)) && !init[j].equals("*")){
						if(init[j].equals(max)){
							for(int k=j-1;k>i;k--){
								ninit[k]=max;
							}break;
						}
						else
							j++;
					}
				}
				
				
				//check left horizontals
				j=i;
				int[] lhori = new int[] {17,25,33,41,49,57,65,73};
				j--;
				if(init[j].equals(min) && !contains(lhori,i)){       // check only if the first neighbor is a min
					while ((!contains(lhori,j) || init[j].equals(max)) && !init[j].equals("*")){
						if(init[j].equals(max)){
							for(int k=j+1;k<i;k++){
								ninit[k]=max;
							}break;
						}
						else
							j--;
					}	
				}
				
				
				
				//check top vertical
				j=i;
				int[] tverti = new int[] {17,18,19,20,21,22,23,24};
				j-=8;
				if(init[j].equals(min) && !contains(tverti,i)){       // check only if the first neighbor is a min
					while ((!contains(tverti,j) || init[j].equals(max)) && !init[j].equals("*")){
						if(init[j].equals(max)){
							for(int k=j+8;k<i;k+=8){
								ninit[k]=max;
							}break;	
						}
						else
							j-=8;
					}	
				}
				
				
				//check bottom vertical
				j=i;
				int[] bverti = new int[] {73,74,75,76,77,78,79,80};
				j+=8;
				if(init[j].equals(min) && !contains(bverti,i)){       // check only if the first neighbor is a min
					while ((!contains(bverti,j) || init[j].equals(max)) && !init[j].equals("*")){
						if(init[j].equals(max)){
							for(int k=j-8;k>i;k-=8){
								ninit[k]=max;
							}break;	
						}
						else
							j+=8;
					}	
				}
				
				
				//check top right diagonal
				j=i;
				int[] trdiag = new int[] {17,18,19,20,21,22,23,24,32,40,48,56,64,72,80};
				j-=7;
				if(init[j].equals(min) && !contains(trdiag,i)){       // check only if the first neighbor is a min
					while ((!contains(trdiag,j) || init[j].equals(max)) && !init[j].equals("*")){
						if(init[j].equals(max)){
							for(int k=j+7;k<i;k+=7){
								ninit[k]=max;
							}break;
						}
						else
							j-=7;
					}	
				}
				
				
				//check bottom left diagonal
				j=i;
				int[] bldiag = new int[] {17,25,33,41,49,57,65,73,74,75,76,77,78,79,80};
				j+=7;
				if(init[j].equals(min) && !contains(bldiag,i)){       // check only if the first neighbor is a min
					while ((!contains(bldiag,j) || init[j].equals(max)) && !init[j].equals("*")){
						if(init[j].equals(max)){
							for(int k=j-7;k>i;k-=7){
								ninit[k]=max;
							}break;
						}
						else
							j+=7;
					}	
				}
				
				
				//check top left diagonal
				j=i;
				int[] tldiag = new int[] {17,18,19,20,21,22,23,24,25,33,41,49,57,65,73};
				j-=9;
				if(init[j].equals(min) && !contains(tldiag,i)){       // check only if the first neighbor is a min
					while ((!contains(tldiag,j) || init[j].equals(max)) && !init[j].equals("*")){
						if(init[j].equals(max)){
							for(int k=j+9;k<i;k+=9){
								ninit[k]=max;
							}break;
						}
						else
							j-=9;
					}	
				}
				
				
				//check bottom right diagonal
				j=i;
				int[] brdiag = new int[] {24,32,40,48,56,64,72,73,74,75,76,77,78,79,80};
				j+=9;
				if(init[j].equals(min) && !contains(brdiag,i)){       // check only if the first neighbor is a min
					while ((!contains(brdiag,j) || init[j].equals(max)) && !init[j].equals("*")){
						if(init[j].equals(max)){
							for(int k=j-9;k>i;k-=9){
								ninit[k]=max;
							}break;
						}
						else
							j+=9;
					}	
				}
				ninit[i]=max;
				return ninit;
			}
			
			
			int EvalFunction(String[] l, String maximum){
				String[] x = l;
				String max = maximum;
				String min;
				if(max.equals("X"))
					min = "O";
				else if(max.equals("O"))
					min = "X";
				else min = "";
				int value = 0;
				for(int i=17;i<=80;i++){
					if(x[i].equals(max))
						value += eval[i];
					else if (x[i].equals(min))
						value -= eval[i];
				}
				return value;
			}
			
			
			ArrayList<Integer> NextPossibleMoves(String[] initpos, String maxvalue){
				String[] init = initpos;
				String max = maxvalue;
				String min;
				if(max.equals("X"))
					min = "O";
				else if(max.equals("O"))
					min = "X";
				else min = "";
				
				ArrayList<Integer> nextmoves = new ArrayList<Integer>();
				for(int i=17;i<=80;i++){
					if(init[i].equals("*")){    // check all the empty places for next move feasibility
						//check right horizontals
						int j=i;
						int[] rhori = new int[] {24,32,40,48,56,64,72,80};
						j++;
						if(init[j].equals(min) && !contains(rhori,i)){       // check only if the first neighbor is a min
							while ((!contains(rhori,j) || init[j].equals(max)) && !init[j].equals("*")){
								int maxs=0;
								if(init[j].equals(max))
									maxs++;
								// add the i spot to possible moves if there is atleast one max to make the move feasible
								if(maxs!=0){
									if(!nextmoves.contains(i)){
										nextmoves.add(i);
									}	
								}
								j++;
							}
						}
						
						
						//check left horizontals
						j=i;
						int[] lhori = new int[] {17,25,33,41,49,57,65,73};
						j--;
						if(init[j].equals(min) && !contains(lhori,i)){       // check only if the first neighbor is a min
							while ((!contains(lhori,j) || init[j].equals(max)) && !init[j].equals("*")){
								int maxs=0;
								if(init[j].equals(max))
									maxs++;
								// add the i spot to possible moves if there is atleast one max to make the move feasible
								if(maxs!=0){
									if(!nextmoves.contains(i)){
										nextmoves.add(i);
									}	
								}
								j--;
							}	
						}
						
						
						
						//check top vertical
						j=i;
						int[] tverti = new int[] {17,18,19,20,21,22,23,24};
						j-=8;
						if(init[j].equals(min) && !contains(tverti,i)){       // check only if the first neighbor is a min
							while ((!contains(tverti,j) || init[j].equals(max)) && !init[j].equals("*")){
								int maxs=0;
								if(init[j].equals(max))
									maxs++;
								
								// add the i spot to possible moves if there is atleast one max to make the move feasible
								if(maxs!=0){
									if(!nextmoves.contains(i)){
										nextmoves.add(i);
									}	
								}
								j-=8;
							}	
						}
						
						
						//check bottom vertical
						j=i;
						int[] bverti = new int[] {73,74,75,76,77,78,79,80};
						j+=8;
						if(init[j].equals(min) && !contains(bverti,i)){       // check only if the first neighbor is a min
							while ((!contains(bverti,j) || init[j].equals(max)) && !init[j].equals("*")){
								int maxs=0;
								if(init[j].equals(max))
									maxs++;
								
								// add the i spot to possible moves if there is atleast one max to make the move feasible
								if(maxs!=0){
									if(!nextmoves.contains(i)){
										nextmoves.add(i);
									}	
								}
								j+=8;
							}	
						}
						
						
						//check top right diagonal
						j=i;
						int[] trdiag = new int[] {17,18,19,20,21,22,23,24,32,40,48,56,64,72,80};
						j-=7;
						if(init[j].equals(min) && !contains(trdiag,i)){       // check only if the first neighbor is a min
							while ((!contains(trdiag,j) || init[j].equals(max)) && !init[j].equals("*")){
								int maxs=0;
								if(init[j].equals(max))
									maxs++;
								
								// add the i spot to possible moves if there is atleast one max to make the move feasible
								if(maxs!=0){
									if(!nextmoves.contains(i)){
										nextmoves.add(i);
									}	
								}
								j-=7;
							}	
						}
						
						
						//check bottom left diagonal
						j=i;
						int[] bldiag = new int[] {17,25,33,41,49,57,65,73,74,75,76,77,78,79,80};
						j+=7;
						if(init[j].equals(min) && !contains(bldiag,i)){       // check only if the first neighbor is a min
							while ((!contains(bldiag,j) || init[j].equals(max)) && !init[j].equals("*")){
								int maxs=0;
								if(init[j].equals(max))
									maxs++;
								
								// add the i spot to possible moves if there is atleast one max to make the move feasible
								if(maxs!=0){
									if(!nextmoves.contains(i)){
										nextmoves.add(i);
									}	
								}
								j+=7;
							}	
						}
						
						
						//check top left diagonal
						j=i;
						int[] tldiag = new int[] {17,18,19,20,21,22,23,24,25,33,41,49,57,65,73};
						j-=9;
						if(init[j].equals(min) && !contains(tldiag,i)){       // check only if the first neighbor is a min
							while ((!contains(tldiag,j) || init[j].equals(max)) && !init[j].equals("*")){
								int maxs=0;
								if(init[j].equals(max))
									maxs++;
								
								// add the i spot to possible moves if there is atleast one max to make the move feasible
								if(maxs!=0){
									if(!nextmoves.contains(i)){
										nextmoves.add(i);
									}	
								}
								j-=9;
							}	
						}
						
						
						//check bottom right diagonal
						j=i;
						int[] brdiag = new int[] {24,32,40,48,56,64,72,73,74,75,76,77,78,79,80};
						j+=9;
						if(init[j].equals(min) && !contains(brdiag,i)){       // check only if the first neighbor is a min
							while ((!contains(brdiag,j) || init[j].equals(max)) && !init[j].equals("*")){
								int maxs=0;
								if(init[j].equals(max))
									maxs++;
								
								// add the i spot to possible moves if there is atleast one max to make the move feasible
								if(maxs!=0){
									if(!nextmoves.contains(i)){
										nextmoves.add(i);
									}	
								}
								j+=9;
							}	
						}	
					}	
				}
				return nextmoves; //returns the list of postitions possible for next move. Retunrs empty string if no move exists.
			}
			
			String p = "";
			int dp = 0;
			int vp = 0;
			int minimax(String[] x, int dept,String max, String min,boolean maxim){
				ArrayList<Integer> nextmoves = new ArrayList<Integer>();
				int d=depth-dept+1;
				if(maxim){
					nextmoves = NextPossibleMoves(x,max);
					if(nextmoves.isEmpty()){
						//log
						log+="pass"+","+d+",";
						if(vp==-999999)
							log+="-Infinity\n";
						else if(vp==999999)
							log+="Infinity\n";
						else
							log+=vp+"\n";
						nextmoves = NextPossibleMoves(x,min);
						maxim=false;
					}
					
				}
				else{
					nextmoves = NextPossibleMoves(x,min);
					if(nextmoves.isEmpty()){
						//log
						log+="pass"+","+d+",";
						if(vp==-999999)
							log+="-Infinity\n";
						else if(vp==999999)
							log+="Infinity\n";
						else
							log+=vp+"\n";
						nextmoves = NextPossibleMoves(x,max);
						maxim=true;
					}
					
					
				}
				
				
				if(dept == 0 || nextmoves.isEmpty()){
					vp = EvalFunction(x,max);
					return EvalFunction(x,max);
				}
				
				if (maxim){
					int v = -999999;
					//System.out.println("root"+","+0+","+v);
					for(int i:nextmoves){
						
						//log1
						log+="root"+","+0+",";
						if(v==-999999)
							log+="-Infinity\n";
						else if(v==999999)
							log+="Infinity\n";
						else
							log+=v+"\n";
						
						
						String[] nextinit = x.clone();
						nextinit = ImplementMove(nextinit, i, min, max);
						p=nodename[i];
						dp = d;
						int temp = minimax(nextinit,dept-1,max,min,false);
						if(temp > v)
							v = temp;
						
						//log2
						log+=nodename[i]+","+d+",";
						if(v==-999999)
							log+="-Infinity\n";
						else if(v==999999)
							log+="Infinity\n";
						else
							log+=v+"\n";
						
						
						storageList.add(i);
						storageList.add(v);
						
					}
					
					//log3
					log+="root"+","+0+",";
					if(v==-999999)
						log+="-Infinity\n";
					else if(v==999999)
						log+="Infinity\n";
					else
						log+=v+"\n";
					
					
					return v;	
				}
				else{
					int v = 999999;
					for(int i:nextmoves){
						String[] nextinit = x.clone();
						nextinit = ImplementMove(nextinit, i, max, min);
						
						//log4
						log+=p+","+dp+",";
						if(v==-999999)
							log+="-Infinity\n";
						else if(v==999999)
							log+="Infinity\n";
						else
							log+=v+"\n";
						
						
						int temp = minimax(nextinit,dept-1,max,min,false);
						if(temp < v)
							v = temp;
						
						//log5
					    log+=nodename[i]+","+d+",";
					    if(vp==-999999)
							log+="-Infinity\n";
						else if(vp==999999)
							log+="Infinity\n";
						else
							log+=vp+"\n";
					    
					    
					}
					return v;	
				}
			}
			
		
		}
		
		compute c = new compute();
		ArrayList<Integer> nextmoves=new ArrayList<Integer>();
		nextmoves = c.NextPossibleMoves(init,max);
		

		// Call the MiniMax algorithm to choose the best next move
		int maxvalue = c.minimax(init, depth, max, min, true);
		int next = c.next(maxvalue,init,max);
		String[] newinit = init.clone();
		if(!nextmoves.isEmpty())
			newinit = c.ImplementMove(newinit, next, min, max);
		for(int i=17;i<=80;i++){
			out.print(newinit[i]);
			if ((i%8) == 0)
				out.println();	
		}
		out.println("Node,Depth,Value");
		out.println(c.log);
		out.close();
		
	}
	
	

	public static void Alphabeta(String currentpl,String[] initpos,int[] evaluate,int dep) throws IOException{
		String max = currentpl;
		String[] init = initpos;
		final int[] eval = evaluate;
		final int depth = dep;
		final PrintWriter out = new PrintWriter(new FileWriter("output.txt"));
		String min;
		if(max.equals("X"))
			min = "O";
		else if(max.equals("O"))
			min = "X";
		else min = "";
		
		
		class compute{
			// method to compute all possible moves next to min color
			ArrayList<Integer> storageList = new ArrayList<Integer>();
			String[] nodename = {"",
					"","","","","","","","",
					"","","","","","","","",
					"a1","b1","c1","d1","e1","f1","g1","h1",
					"a2","b2","c2","d2","e2","f2","g2","h2",
					"a3","b3","c3","d3","e3","f3","g3","h3",
					"a4","b4","c4","d4","e4","f4","g4","h4",
					"a5","b5","c5","d5","e5","f5","g5","h5",
					"a6","b6","c6","d6","e6","f6","g6","h6",
					"a7","b7","c7","d7","e7","f7","g7","h7",
					"a8","b8","c8","d8","e8","f8","g8","h8",
					};
			String log = "";
			
			int next(int mmvalue,String [] l,String maximum){
				
				ArrayList<Integer> nextmove=new ArrayList<Integer>();
				nextmove = NextPossibleMoves(l,maximum);
				
				for(int j:nextmove){
					for(int i=0;i<storageList.toArray().length;i+=2){
						if (storageList.get(i) == j){
							if(storageList.get(i+1) == mmvalue){
								return j;
								
							}
								
						}	
					}
				}
				return 0;	
			}
			
			
			boolean contains(int[] x,int y){
				for(int i:x){
					if(i==y)
						return true;
				}
				return false;	
			}
			
			String[] ImplementMove(String[] x,int i, String minimum, String maximum){
				String[] init = x;
				String[] ninit = init.clone();
				String max = maximum;
				String min = minimum;
				int j=i;
				int[] rhori = new int[] {24,32,40,48,56,64,72,80};
				j++;
				if(init[j].equals(min) && !contains(rhori,i)){       // check only if the first neighbor is a min
					while ((!contains(rhori,j) || init[j].equals(max)) && !init[j].equals("*")){
						if(init[j].equals(max)){
							for(int k=j-1;k>i;k--){
								ninit[k]=max;
							}break;
						}
						else
							j++;
					}
				}
				
				
				//check left horizontals
				j=i;
				int[] lhori = new int[] {17,25,33,41,49,57,65,73};
				j--;
				if(init[j].equals(min) && !contains(lhori,i)){       // check only if the first neighbor is a min
					while ((!contains(lhori,j) || init[j].equals(max)) && !init[j].equals("*")){
						if(init[j].equals(max)){
							for(int k=j+1;k<i;k++){
								ninit[k]=max;
							}break;
						}
						else
							j--;
					}	
				}
				
				
				
				//check top vertical
				j=i;
				int[] tverti = new int[] {17,18,19,20,21,22,23,24};
				j-=8;
				if(init[j].equals(min) && !contains(tverti,i)){       // check only if the first neighbor is a min
					while ((!contains(tverti,j) || init[j].equals(max)) && !init[j].equals("*")){
						if(init[j].equals(max)){
							for(int k=j+8;k<i;k+=8){
								ninit[k]=max;
							}break;	
						}
						else
							j-=8;
					}	
				}
				
				
				//check bottom vertical
				j=i;
				int[] bverti = new int[] {73,74,75,76,77,78,79,80};
				j+=8;
				if(init[j].equals(min) && !contains(bverti,i)){       // check only if the first neighbor is a min
					while ((!contains(bverti,j) || init[j].equals(max)) && !init[j].equals("*")){
						if(init[j].equals(max)){
							for(int k=j-8;k>i;k-=8){
								ninit[k]=max;
							}break;	
						}
						else
							j+=8;
					}	
				}
				
				
				//check top right diagonal
				j=i;
				int[] trdiag = new int[] {17,18,19,20,21,22,23,24,32,40,48,56,64,72,80};
				j-=7;
				if(init[j].equals(min) && !contains(trdiag,i)){       // check only if the first neighbor is a min
					while ((!contains(trdiag,j) || init[j].equals(max)) && !init[j].equals("*")){
						if(init[j].equals(max)){
							for(int k=j+7;k<i;k+=7){
								ninit[k]=max;
							}break;
						}
						else
							j-=7;
					}	
				}
				
				
				//check bottom left diagonal
				j=i;
				int[] bldiag = new int[] {17,25,33,41,49,57,65,73,74,75,76,77,78,79,80};
				j+=7;
				if(init[j].equals(min) && !contains(bldiag,i)){       // check only if the first neighbor is a min
					while ((!contains(bldiag,j) || init[j].equals(max)) && !init[j].equals("*")){
						if(init[j].equals(max)){
							for(int k=j-7;k>i;k-=7){
								ninit[k]=max;
							}break;
						}
						else
							j+=7;
					}	
				}
				
				
				//check top left diagonal
				j=i;
				int[] tldiag = new int[] {17,18,19,20,21,22,23,24,25,33,41,49,57,65,73};
				j-=9;
				if(init[j].equals(min) && !contains(tldiag,i)){       // check only if the first neighbor is a min
					while ((!contains(tldiag,j) || init[j].equals(max)) && !init[j].equals("*")){
						if(init[j].equals(max)){
							for(int k=j+9;k<i;k+=9){
								ninit[k]=max;
							}break;
						}
						else
							j-=9;
					}	
				}
				
				
				//check bottom right diagonal
				j=i;
				int[] brdiag = new int[] {24,32,40,48,56,64,72,73,74,75,76,77,78,79,80};
				j+=9;
				if(init[j].equals(min) && !contains(brdiag,i)){       // check only if the first neighbor is a min
					while ((!contains(brdiag,j) || init[j].equals(max)) && !init[j].equals("*")){
						if(init[j].equals(max)){
							for(int k=j-9;k>i;k-=9){
								ninit[k]=max;
							}break;
						}
						else
							j+=9;
					}	
				}
				ninit[i]=max;
				return ninit;
			}
			
			
			int EvalFunction(String[] l, String maximum){
				String[] x = l;
				String max = maximum;
				String min;
				if(max.equals("X"))
					min = "O";
				else if(max.equals("O"))
					min = "X";
				else min = "";
				int value = 0;
				for(int i=17;i<=80;i++){
					if(x[i].equals(max))
						value += eval[i];
					else if (x[i].equals(min))
						value -= eval[i];
				}
				return value;
			}
			
			
			ArrayList<Integer> NextPossibleMoves(String[] initpos, String maxvalue){
				String[] init = initpos;
				String max = maxvalue;
				String min;
				if(max.equals("X"))
					min = "O";
				else if(max.equals("O"))
					min = "X";
				else min = "";
				
				ArrayList<Integer> nextmoves = new ArrayList<Integer>();
				for(int i=17;i<=80;i++){
					if(init[i].equals("*")){    // check all the empty places for next move feasibility
						//check right horizontals
						int j=i;
						int[] rhori = new int[] {24,32,40,48,56,64,72,80};
						j++;
						if(init[j].equals(min) && !contains(rhori,i)){       // check only if the first neighbor is a min
							while ((!contains(rhori,j) || init[j].equals(max)) && !init[j].equals("*")){
								int maxs=0;
								if(init[j].equals(max))
									maxs++;
								// add the i spot to possible moves if there is atleast one max to make the move feasible
								if(maxs!=0){
									if(!nextmoves.contains(i)){
										nextmoves.add(i);
									}	
								}
								j++;
							}
						}
						
						
						//check left horizontals
						j=i;
						int[] lhori = new int[] {17,25,33,41,49,57,65,73};
						j--;
						if(init[j].equals(min) && !contains(lhori,i)){       // check only if the first neighbor is a min
							while ((!contains(lhori,j) || init[j].equals(max)) && !init[j].equals("*")){
								int maxs=0;
								if(init[j].equals(max))
									maxs++;
								// add the i spot to possible moves if there is atleast one max to make the move feasible
								if(maxs!=0){
									if(!nextmoves.contains(i)){
										nextmoves.add(i);
									}	
								}
								j--;
							}	
						}
						
						
						
						//check top vertical
						j=i;
						int[] tverti = new int[] {17,18,19,20,21,22,23,24};
						j-=8;
						if(init[j].equals(min) && !contains(tverti,i)){       // check only if the first neighbor is a min
							while ((!contains(tverti,j) || init[j].equals(max)) && !init[j].equals("*")){
								int maxs=0;
								if(init[j].equals(max))
									maxs++;
								
								// add the i spot to possible moves if there is atleast one max to make the move feasible
								if(maxs!=0){
									if(!nextmoves.contains(i)){
										nextmoves.add(i);
									}	
								}
								j-=8;
							}	
						}
						
						
						//check bottom vertical
						j=i;
						int[] bverti = new int[] {73,74,75,76,77,78,79,80};
						j+=8;
						if(init[j].equals(min) && !contains(bverti,i)){       // check only if the first neighbor is a min
							while ((!contains(bverti,j) || init[j].equals(max)) && !init[j].equals("*")){
								int maxs=0;
								if(init[j].equals(max))
									maxs++;
								
								// add the i spot to possible moves if there is atleast one max to make the move feasible
								if(maxs!=0){
									if(!nextmoves.contains(i)){
										nextmoves.add(i);
									}	
								}
								j+=8;
							}	
						}
						
						
						//check top right diagonal
						j=i;
						int[] trdiag = new int[] {17,18,19,20,21,22,23,24,32,40,48,56,64,72,80};
						j-=7;
						if(init[j].equals(min) && !contains(trdiag,i)){       // check only if the first neighbor is a min
							while ((!contains(trdiag,j) || init[j].equals(max)) && !init[j].equals("*")){
								int maxs=0;
								if(init[j].equals(max))
									maxs++;
								
								// add the i spot to possible moves if there is atleast one max to make the move feasible
								if(maxs!=0){
									if(!nextmoves.contains(i)){
										nextmoves.add(i);
									}	
								}
								j-=7;
							}	
						}
						
						
						//check bottom left diagonal
						j=i;
						int[] bldiag = new int[] {17,25,33,41,49,57,65,73,74,75,76,77,78,79,80};
						j+=7;
						if(init[j].equals(min) && !contains(bldiag,i)){       // check only if the first neighbor is a min
							while ((!contains(bldiag,j) || init[j].equals(max)) && !init[j].equals("*")){
								int maxs=0;
								if(init[j].equals(max))
									maxs++;
								
								// add the i spot to possible moves if there is atleast one max to make the move feasible
								if(maxs!=0){
									if(!nextmoves.contains(i)){
										nextmoves.add(i);
									}	
								}
								j+=7;
							}	
						}
						
						
						//check top left diagonal
						j=i;
						int[] tldiag = new int[] {17,18,19,20,21,22,23,24,25,33,41,49,57,65,73};
						j-=9;
						if(init[j].equals(min) && !contains(tldiag,i)){       // check only if the first neighbor is a min
							while ((!contains(tldiag,j) || init[j].equals(max)) && !init[j].equals("*")){
								int maxs=0;
								if(init[j].equals(max))
									maxs++;
								
								// add the i spot to possible moves if there is atleast one max to make the move feasible
								if(maxs!=0){
									if(!nextmoves.contains(i)){
										nextmoves.add(i);
									}	
								}
								j-=9;
							}	
						}
						
						
						//check bottom right diagonal
						j=i;
						int[] brdiag = new int[] {24,32,40,48,56,64,72,73,74,75,76,77,78,79,80};
						j+=9;
						if(init[j].equals(min) && !contains(brdiag,i)){       // check only if the first neighbor is a min
							while ((!contains(brdiag,j) || init[j].equals(max)) && !init[j].equals("*")){
								int maxs=0;
								if(init[j].equals(max))
									maxs++;
								
								// add the i spot to possible moves if there is atleast one max to make the move feasible
								if(maxs!=0){
									if(!nextmoves.contains(i)){
										nextmoves.add(i);
									}	
								}
								j+=9;
							}	
						}	
					}	
				}
				return nextmoves; //returns the list of postitions possible for next move. Retunrs empty string if no move exists.
			}
			
			String p = "root";
			int dp = 0;
			int vp = 0;
			int bp = 0;
			int ap = 0;
			
			

			int alphabeta(String[] x, int dept,String max, String min,boolean maxim,int alpha,int beta){
				ArrayList<Integer> nextmoves = new ArrayList<Integer>();
				int d=depth-dept+1;
				if(maxim){
					nextmoves = NextPossibleMoves(x,max);
					if(nextmoves.isEmpty()){
						//log
						log+="pass"+","+d+",";
						if(vp==-999999)
							log+="-Infinity,";
						else if(vp==999999)
							log+="Infinity,";
						else
							log+=vp+",";
						if(alpha==-999999)
							log+="-Infinity,";
						else if(alpha==999999)
							log+="Infinity,";
						else
							log+=alpha+",";
						if(beta==-999999)
							log+="-Infinity\n";
						else if(beta==999999)
							log+="Infinity\n";
						else
							log+=beta+"\n";
						nextmoves = NextPossibleMoves(x,min);
						maxim=false;
					}
					
				}
				else{
					nextmoves = NextPossibleMoves(x,min);
					if(nextmoves.isEmpty()){
						//log
						log+="pass"+","+d+",";
						if(vp==-999999)
							log+="-Infinity,";
						else if(vp==999999)
							log+="Infinity,";
						else
							log+=vp+",";
						if(alpha==-999999)
							log+="-Infinity,";
						else if(alpha==999999)
							log+="Infinity,";
						else
							log+=alpha+",";
						if(beta==-999999)
							log+="-Infinity\n";
						else if(beta==999999)
							log+="Infinity\n";
						else
							log+=beta+"\n";
						nextmoves = NextPossibleMoves(x,max);
						maxim=true;
					}
					
					
				}
					
				
				if(dept == 0 || nextmoves.isEmpty()){
					vp = EvalFunction(x,max);
					ap = alpha;
					bp = beta;
					return EvalFunction(x,max);
				}
				
				if (maxim){
					for(int i:nextmoves){
						
						//log1
						log+="root"+","+0+",";
						if(alpha==-999999)
							log+="-Infinity,";
						else if(alpha==999999)
							log+="Infinity,";
						else
							log+=alpha+",";
						if(alpha==-999999)
							log+="-Infinity,";
						else if(alpha==999999)
							log+="Infinity,";
						else
							log+=alpha+",";
						if(beta==-999999)
							log+="-Infinity\n";
						else if(beta==999999)
							log+="Infinity\n";
						else
							log+=beta+"\n";
						
						
						String[] nextinit = x.clone();
						nextinit = ImplementMove(nextinit, i, min, max);
						p=nodename[i];
						dp = d;
						
						
						int temp = alphabeta(nextinit,dept-1,max,min,false,alpha,beta);
						if(temp > alpha)
							alpha = temp;
						if(alpha >=beta)
							break;
							
							
						//log2
						log+=nodename[i]+","+d+",";
						if(alpha==-999999)
							log+="-Infinity,";
						else if(alpha==999999)
							log+="Infinity,";
						else
							log+=alpha+",";
						if(ap==-999999)
							log+="-Infinity,";
						else if(ap==999999)
							log+="Infinity,";
						else
							log+=ap+",";
						if(bp==-999999)
							log+="-Infinity\n";
						else if(bp==999999)
							log+="Infinity\n";
						else
							log+=bp+"\n";
						
						
						
						storageList.add(i);
						storageList.add(alpha);
					
					}
					
					//log3
					log+="root"+","+0+",";
					if(alpha==-999999)
						log+="-Infinity,";
					else if(alpha==999999)
						log+="Infinity,";
					else
						log+=alpha+",";
					if(alpha==-999999)
						log+="-Infinity,";
					else if(alpha==999999)
						log+="Infinity,";
					else
						log+=alpha+",";
					if(beta==-999999)
						log+="-Infinity\n";
					else if(beta==999999)
						log+="Infinity\n";
					else
						log+=beta+"\n";
					
					
					return alpha;	
				}
				else{
					for(int i:nextmoves){
						String[] nextinit = x.clone();
						nextinit = ImplementMove(nextinit, i, max, min);
						
						//log4
						log+=p+","+dp+",";
						if(beta==-999999)
							log+="-Infinity,";
						else if(beta==999999)
							log+="Infinity,";
						else
							log+=beta+",";
						if(alpha==-999999)
							log+="-Infinity,";
						else if(alpha==999999)
							log+="Infinity,";
						else
							log+=alpha+",";
						if(beta==-999999)
							log+="-Infinity\n";
						else if(beta==999999)
							log+="Infinity\n";
						else
							log+=beta+"\n";
						
					
						
						int temp = alphabeta(nextinit,dept-1,max,min,true,alpha,beta);
						
							
						//log5
						log+=nodename[i]+","+d+",";
						if(vp==-999999)
							log+="-Infinity,";
						else if(vp==999999)
							log+="Infinity,";
						else
							log+=vp+",";
						if(alpha==-999999)
							log+="-Infinity,";
						else if(alpha==999999)
							log+="Infinity,";
						else
							log+=alpha+",";
						if(beta==-999999)
							log+="-Infinity\n";
						else if(beta==999999)
							log+="Infinity\n";
						else
							log+=beta+"\n";
						
						if(temp < beta)
							beta = temp;
						if(alpha >= beta){
							break;
						}
						
					}
					return beta;	
				}
			}
		}
		
		compute c = new compute();
		ArrayList<Integer> nextmoves=new ArrayList<Integer>();
		nextmoves = c.NextPossibleMoves(init,max);
		
		
		// Call the Alphabeta algorithm to choose the best next move
		int maxvalue = c.alphabeta(init, depth, max, min, true,-999999,999999);
		int next = c.next(maxvalue,init,max);
		String[] newinit = init.clone();
		if(!nextmoves.isEmpty())
			newinit = c.ImplementMove(newinit, next, min, max);
		for(int i=17;i<=80;i++){
			out.print(newinit[i]);
			if ((i%8) == 0)
				out.println();	
		}
		out.println("Node,Depth,Value,Alpha,Beta");
		out.println(c.log);
		out.close();
		
		
		}
}




