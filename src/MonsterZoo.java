import java.util.stream.Stream;
import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.OptionalInt;


public class MonsterZoo {
    double distance = 0.0; //歩いた距離
    int balls = 10; //モンスターを捕まえられるボールの数
    int fruits = 0; //ぶつけるとモンスターが捕まえやすくなるフルーツ

    //卵は最大9個まで持てる．卵を取得するとeggにtrueが代入され，
    //移動するたびに,eggDistanceに1.0kmずつ加算される．
    //3km移動するとランダムでモンスターが孵る
    List<Double> eggDistance = Stream.generate(() -> 0.0)
	.limit(9)
	.collect(Collectors.toList());

    List<Boolean> egg = Stream.generate(() -> false)
	.limit(9)
	.collect(Collectors.toList());

    //ユーザがGetしたモンスター一覧
    List<String> userMonster = Stream.generate(() -> "")
	.limit(100)
	.collect(Collectors.toList());

    //モンスター図鑑．モンスターの名前とレア度(0.0~9.0)がそれぞれの配列に保存されている
    //レア度が高いほうが捕まえにくい
    List<String> monsterZukan =  Stream.generate(() -> "")
	.limit(22)
	.collect(Collectors.toList());

    List<Double> monsterRare = Stream.generate(() -> 0.0)
	.limit(22)
	.collect(Collectors.toList());

    void run(){
	//1000ミリ秒（1秒）ずつ止まりながらpz.move()を呼び出し続ける
	//手持ちのボールが無くなったら終了
	while(true){
	    try{
		Thread.sleep(1000);
		if(this.getBalls() > 0){
		    this.move();
		    System.out.println("手持ちのボールは" + this.getBalls() + "個，フルーツは" + this.getFruits() + "個");
		    System.out.println(this.getDistance() + "km歩いた．");
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

	this.getUserMonster().stream()
	    .filter(value -> value != "")
	    .forEach(value -> System.out.println(value + "を捕まえた．"));
    }

    //呼び出すと1km distanceが増える
    void move(){
	this.distance++;

	IntStream.range(0, this.egg.size())
	    .filter(i -> this.egg.get(i) == true)
	    .forEach(i -> this.eggDistance.set(i, this.eggDistance.get(i)+1));

	int flg1 = (int)(Math.random() * 10); //0,1の場合はズーstation，7~9の場合はモンスター

	if(flg1 <= 1){
	    System.out.println("ズーstationを見つけた！");
	    int b = (int)(Math.random() * 3); //ball,fruits,eggがランダムに出る
	    int f = (int)(Math.random() * 2);
	    int e = (int)(Math.random() * 2);
	    System.out.println("ボールを" + b + "個，" + "フルーツを" + f + "個" + "卵を" + e + "個Getした！");
	    this.balls = this.balls + b;
	    this.fruits = this.fruits + f;

	    if(e >= 1){//卵を1つ以上Getしたら
		     //egg[]に10個以上卵がない場合は新しい卵データをセットする
		OptionalInt egg_is_flase = IntStream.range(0, this.eggDistance.size())
		    .filter(i -> this.egg.get(i) == false)
		    .findFirst();
		egg_is_flase.ifPresent(i -> this.egg.set(i, true));
		egg_is_flase.ifPresent(i -> this.eggDistance.set(i, 0.0));
	    }
	}
	else if(flg1 >= 7){
	    int m = (int)(this.monsterZukan.size() * Math.random()); //monsterZukanからランダムにモンスターを出す
	    System.out.println(this.monsterZukan.get(m) + "が現れた！");

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
		    System.out.println(this.monsterZukan.get(m) + "にボールを投げた");

		    this.balls--;
		    if(this.monsterRare.get(m) <= r){ //monsterRare[m]の値がr以下の場合
		        System.out.println(this.monsterZukan.get(m) + "を捕まえた！");

		        IntStream.range(0, userMonster.size())
			    .filter(j -> this.userMonster.get(j) == "")
			    .findFirst()
			    .ifPresent(j -> this.userMonster.set(j, this.monsterZukan.get(m)));

			break; //ボール投げ終了
		    }
		    else{
		        System.out.println(this.monsterZukan.get(m)+"に逃げられた！");
		    }
	    }
	}

	List<Integer> egg_size_range = IntStream.range(0, this.egg.size())
	    .mapToObj(Integer::valueOf)
	    .collect(Collectors.toList());

	for(Integer i: egg_size_range){
	    if(this.egg.get(i) == true && this.eggDistance.get(i) >= 3){
		System.out.println("卵が孵った！");
		int m = (int)(this.monsterZukan.size() * Math.random());

		System.out.println(this.monsterZukan.get(m) + "が産まれた！");

		IntStream.range(0, this.userMonster.size())
		    .filter(j -> this.userMonster.get(j) == "")
		    .findFirst()
		    .ifPresent(j -> this.userMonster.set(j, this.monsterZukan.get(m)));

		this.egg.set(i, false);
		this.eggDistance.set(i, 0.0);
	    }
	}
    }

    public void setMonsterZukan(){
	List<String> tempMonster = Arrays.asList(
		"イガキン", "ナマチュウ", "イノウエン", "リョージィ", "アキモトン", "ゴージマ", "チュウデン", "ヅカホン", "ニシムラー", "サコーデン", "ウッチー",
		"ハヤッシー", "キーチー", "リョクン", "デコポン", "カミサン", "シスイ", "ジョナ", "ギダギダ", "ミッツー", "ゾエサン", "キタバー");

	List<Double> tempMonsterRare = Arrays.asList(
		9.0, 3.0, 1.0, 2.0, 5.0, 4.0, 6.0, 8.0, 7.0, 2.0, 5.0,
		4.0, 3.0, 1.0, 6.0, 5.0, 1.0, 7.0, 2.0, 8.0, 5.0, 3.0);

	this.setMonsterZukan(tempMonster);
	this.setMonsterRare(tempMonsterRare);
    }

    private void setMonsterZukan(List<String> monsterZukan) {
	this.monsterZukan = monsterZukan;
    }

    private void setMonsterRare(List<Double> monsterRare) {
	this.monsterRare = monsterRare;
    }



    public double getDistance() {
	return distance;
    }

    public int getBalls() {
	return balls;
    }

    public int getFruits() {
	return fruits;
    }

    public List<String> getUserMonster() {
	return userMonster;
    }
}
