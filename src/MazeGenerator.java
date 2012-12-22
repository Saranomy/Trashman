import java.util.Random;

public class MazeGenerator {

	private static Random r;

	public void init(){
		if(r == null){
			r = new Random(System.currentTimeMillis());
		}
	}

}
