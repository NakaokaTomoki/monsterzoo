import java.util.stream.Stream;
import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.util.List;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.OptionalInt;


public class MonsterZoo {
    private MonsterZukan monsterZukan;
    private Egg egg;
    private Objects object;
    private int r;
    private int m;
    private int flg1;

    public MonsterZoo(MonsterZukan monsterZukan) {
	this.monsterZukan = monsterZukan;
	this.egg = new Egg(this.monsterZukan);
	this.object = new Objects();
    }

    public void run(){
	//1000ミリ秒（1秒）ずつ止まりながらpz.move()を呼び出し続ける
	//手持ちのボールが無くなったら終了
	while(true){
	    try{
		Thread.sleep(1000);
		if(this.object.judgeBallsPositive()){
		    this.move();
		    this.object.resultsPrinter();
		}
		else{
		    break;
		}
	    }
	    catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
	this.resultsPrinter();
    }

    private void resultsPrinter(){
	this.Printer(Stream.of("ボールがなくなった！"));

	this.Printerln(this.monsterZukan.userMonster.stream()
		.filter(value -> value != "")
		.map(value -> value + "を捕まえた．")
		);
    }

    //呼び出すと1km distanceが増える
    private void move(){
	this.object.distanceInc();
	this.egg.updateEggDistance();

	//0,1の場合はズーstation，7~9の場合はモンスター
	this.flg1 = this.generateRandomNumber(10);

	if(flg1 <= 1){
	    this.moveToZooStationPhase();
	}
	else if(flg1 >= 7){
	    this.moveToGanerateMonsterPhase();
	}
	this.egg.checkEggState();
    }

    private void moveToGanerateMonsterPhase(){
	// monsterZukanからランダムにモンスターを出す
	this.m = this.monsterZukan.generateRandomMonster();

	this.Printer(Stream.of(
		    this.monsterZukan.monsterZukan.get(this.m),
		    "が現れた！"));
	//捕まえる or 3回ボールを投げるまで繰り返す
	for(int i=0; i<3 && this.object.judgeBallsPositive(); i++){
		this.r = this.generateRandomNumber(6);

	        if(this.object.judgeFruitsPositive()){
	            this.Printer(Stream.of(
				"フルーツを投げた！捕まえやすさが倍になる！"));
		    this.object.fruitsDec();
		    this.r *= 2;
	        }

		this.Printer(Stream.of(
			    this.monsterZukan.monsterZukan.get(this.m),
			    "にボールを投げた"));

		this.object.ballsDec();

		//monsterRare[m]の値がr以下の場合
	        if(this.judgeRareState()){
		    this.Printer(Stream.of(
				this.monsterZukan.monsterZukan.get(this.m),
				"を捕まえた！"));

		    this.monsterZukan.updateUserMonster(this.m);

		    break; //ボール投げ終了
		}
		else{
		    this.Printer(Stream.of(
				this.monsterZukan.monsterZukan.get(this.m),
				"に逃げられた！"));
		}
	}
    }

    private boolean judgeRareState(){
	return this.monsterZukan.monsterRare.get(this.m) <= this.r;
    }

    private int generateRandomNumber(int num){
	//0~5までの数字をランダムに返す
	return (int)(num * Math.random());
    }

    private void generateObjectAndEgg(){
	int b = object.generateNewBalls();
	int f = object.generateNewFruits();
	int e = egg.generateNewEgg();

	this.Printer(Stream.of(
		    "ボールを",
		    String.valueOf(b),
		    "個，",
		    "フルーツを",
		    String.valueOf(f),
		    "個",
		    "卵を",
		    String.valueOf(e),
		    "個Getした！"));
    }

    private void moveToZooStationPhase(){
	this.Printer(Stream.of("ズーstationを見つけた！"));

	this.generateObjectAndEgg();
	this.object.updateObjects();

	this.setEggs();
    }

    private void setEggs(){
	//卵を1つ以上Getしたら
	if(this.egg.judgeRandomEggState()){
	    //egg[]に10個以上卵がない場合は新しい卵データをセットする
	    this.egg.newEggSet();
	}
    }

    private void Printerln(Stream<String> st){
	st.forEach(System.out::println);
    }

    private void Printer(Stream<String> st){
	System.out.println(st.collect(Collectors.joining()));
    }
}
