//import java.util.stream.Stream;
import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.util.List;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.OptionalInt;


public class MonsterZoo {
    double distance = 0.0; //歩いた距離
    int balls = 10; //モンスターを捕まえられるボールの数
    int fruits = 0; //ぶつけるとモンスターが捕まえやすくなるフルーツ

    MonsterZukan monsterZukan;

    Egg egg;

    public MonsterZoo(MonsterZukan monsterZukan) {
	this.monsterZukan = monsterZukan;
	this.egg = new Egg(this.monsterZukan);
    }

    public void run(){
	//1000ミリ秒（1秒）ずつ止まりながらpz.move()を呼び出し続ける
	//手持ちのボールが無くなったら終了
	while(true){
	    try{
		Thread.sleep(1000);
		if(this.balls > 0){
		    this.move();
		    System.out.println("手持ちのボールは" + this.balls + "個，フルーツは" + this.fruits + "個");
		    System.out.println(this.distance + "km歩いた．");
		}
		else{
		    break;
		}
	    }
	    catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}

	System.out.println("ボールがなくなった！");

	this.monsterZukan.userMonster.stream()
	    .filter(value -> value != "")
	    .forEach(value -> System.out.println(value + "を捕まえた．"));
    }

    //呼び出すと1km distanceが増える
    private void move(){
	this.distance++;

	this.egg.updateEggDistance();

	int flg1 = (int)(Math.random() * 10); //0,1の場合はズーstation，7~9の場合はモンスター

	if(flg1 <= 1){
	    this.moveToZooStationPhase();
	}
	else if(flg1 >= 7){
	    this.moveToGanerateMonsterPhase();
	}
	this.egg.checkEggState();
    }

    private void moveToGanerateMonsterPhase(){
	int m = (int)(this.monsterZukan.monsterZukan.size() * Math.random()); //monsterZukanからランダムにモンスターを出す
	System.out.println(this.monsterZukan.monsterZukan.get(m) + "が現れた！");

	List<Integer> loop_range = IntStream.rangeClosed(0, 3)
	    .filter(i -> i < 3 && this.balls > 0)
	    .mapToObj(Integer::valueOf)
	    .collect(Collectors.toList());

	for(Integer i: loop_range){
	        int r = (int)(6 * Math.random()); //0~5までの数字をランダムに返す

	        if(this.fruits > 0){
	            System.out.println("フルーツを投げた！捕まえやすさが倍になる！");
	            this.fruits--;
	            r = r * 2;
	        }
	        System.out.println(this.monsterZukan.monsterZukan.get(m) + "にボールを投げた");

	        this.balls--;
	        if(this.monsterZukan.monsterRare.get(m) <= r){ //monsterRare[m]の値がr以下の場合
	            System.out.println(this.monsterZukan.monsterZukan.get(m) + "を捕まえた！");

	            IntStream.range(0, this.monsterZukan.userMonster.size())
			.filter(j -> this.monsterZukan.userMonster.get(j) == "")
			.findFirst()
			.ifPresent(j -> this.monsterZukan.userMonster.set(j, this.monsterZukan.monsterZukan.get(m)));
		    break; //ボール投げ終了
		}
		else{
	            System.out.println(this.monsterZukan.monsterZukan.get(m)+"に逃げられた！");
		}
	}
    }

    private void moveToZooStationPhase(){
	System.out.println("ズーstationを見つけた！");
	int b = (int)(Math.random() * 3); //ball,fruits,eggがランダムに出る
	int f = (int)(Math.random() * 2);
	int e;
	//int e = (int)(Math.random() * 2);
	e = egg.generateNewEgg();

	System.out.println("ボールを" + b + "個，" + "フルーツを" + f + "個" + "卵を" + e + "個Getした！");
	this.balls = this.balls + b;
	this.fruits = this.fruits + f;

	if(this.egg.judgeRandomEggState()){//卵を1つ以上Getしたら
	    //egg[]に10個以上卵がない場合は新しい卵データをセットする
	    this.egg.newEggSet();
	}
    }
}
