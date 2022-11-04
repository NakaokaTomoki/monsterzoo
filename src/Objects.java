public class Objects {
    private double distance; //歩いた距離
    private int balls; //モンスターを捕まえられるボールの数
    private int fruits; //ぶつけるとモンスターが捕まえやすくなるフルーツ
    private int b;
    private int f;

    public Objects() {
	this.distance = 0.0;
	this.balls = 10;
	this.fruits = 0;
    }

    public void resultsPrinter(){
	System.out.println("手持ちのボールは" + this.balls + "個，フルーツは" + this.fruits + "個");
	System.out.println(this.distance + "km歩いた．");
    }

    public void distanceInc(){
	this.distance++;
    }

    public void fruitsDec(){
	this.fruits--;
    }

    public void ballsDec(){
	this.balls--;
    }

    public boolean judgeFruitsPositive(){
	return this.fruits > 0;
    }

    public boolean judgeBallsPositive(){
	return this.balls > 0;
    }

    public int generateNewBalls(){
	//ballがランダムに出る
	this.b = (int)(Math.random() * 3);
	return this.b;
    }

    public int generateNewFruits(){
	//fruitsがランダムに出る
	this.f = (int)(Math.random() * 2);
	return this.f;
    }

    public void updateObjects(){
	this.balls += this.b;
	this.fruits += this.f;
    }
}
