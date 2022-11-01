/*
# monsterzoo仕様
#
- Main.javaではMonsterZooクラスをnewしたあと，1秒毎にmove()を呼び出し，balls変数が0になるまで（初期値は10）呼び出しを繰り返す．

- move内では，呼び出しごとにdistanceが1.0km増え，ランダムにズーstationでボールやフルーツ，卵をゲットしたり，モンスターと遭遇してボールやフルーツをぶつけて捕まえたりする．

- 卵はGetしてから3kmあるくと孵り，ランダムにモンスターをGetできる

- モンスターはレア度を持っており，ボールをぶつけた際の乱数とレア度を比較して，レア度のほうが小さければGetできる

- なお，乱数の値はフルーツをぶつけると倍になる（捕まえやすくなる）
最終的にballsの値が0になると終了し，何キロ歩いたか，捕まえたモンスターは何かを表示する．
 * */

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.IntStream;


public class Main {
    // static MonsterZoo pz = new MonsterZoo();
    //
    public static void main(String[] args) {
	MonsterZoo pz = new MonsterZoo();

	setMonsterZukan(pz);

	//1000ミリ秒（1秒）ずつ止まりながらpz.move()を呼び出し続ける
	//手持ちのボールが無くなったら終了
	while(true){
	    try {
		Thread.sleep(1000);
		if(pz.getBalls() > 0){
		    pz.move();
		    System.out.println("手持ちのボールは" + pz.getBalls() + "個，フルーツは" + pz.getFruits() + "個");
		    System.out.println(pz.getDistance() + "km歩いた．");
		}else{
		    break;
		}
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}

	System.out.println("ボールがなくなった！");

	pz.getUserMonster().stream()
	    .filter(value -> value != "")
	    .forEach(value -> System.out.println(value + "を捕まえた．"));
    }

    //テスト用のモンスターデータを登録するメソッド
    public static void setMonsterZukan(MonsterZoo pz){
	List<String> tempMonster = Arrays.asList(
		"イガキン", "ナマチュウ", "イノウエン", "リョージィ", "アキモトン", "ゴージマ", "チュウデン", "ヅカホン", "ニシムラー", "サコーデン", "ウッチー",
		"ハヤッシー", "キーチー", "リョクン", "デコポン", "カミサン", "シスイ", "ジョナ", "ギダギダ", "ミッツー", "ゾエサン", "キタバー");

	List<Double> tempMonsterRare = Arrays.asList(
		9.0, 3.0, 1.0, 2.0, 5.0, 4.0, 6.0, 8.0, 7.0, 2.0, 5.0,
		4.0, 3.0, 1.0, 6.0, 5.0, 1.0, 7.0, 2.0, 8.0, 5.0, 3.0);

	pz.setMonsterZukan(tempMonster);
	pz.setMonsterRare(tempMonsterRare);
    }
}
