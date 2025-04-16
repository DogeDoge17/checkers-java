public class Vector2 {	
	byte x, y;

	public Vector2(byte x, byte y){
		this.x = x;
		this.y = y;
	}
	public Vector2(int x, int y){
		this.x = (byte)x;
		this.y = (byte)y;
	}

	public Vector2(){
		this.x = 0;
		this.y = 0;
	}

	public byte getX(){
		return x;
	}

	public byte getY(){
		return y;
	}

	public byte valueOf(byte[][] arr){
		if(y >= arr.length)
			return -3;
		if(x >= arr[y].length)
			return -3;
		if(x < 0)
			return -3;

		return arr[y][x];
	}

	public Vector2 add(byte x, byte y){
		return new Vector2((byte)(this.x+x),(byte)(this.y + y));
	}

	public String toString() {		
		return String.format("%c%d", Main.coordToLetter(y), x+1); //String.format("(%d, %d)", x, y);
	}
}