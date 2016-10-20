package Exercise9;

public class LoopPart4 {

	public static void main(String[] args){
		for(int i = 0 ; i < 40; i++){
			Part4 k = new Part4(i);
			k.nonThread();
			k.thread();
		}
	}
}
