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


public class Main {
    public static void main(String[] args) {
	MonsterZukan monsterZukan = new MonsterZukan();
	monsterZukan.setMonsterZukan();

	MonsterZoo monsterZoo = new MonsterZoo(monsterZukan);

	//手持ちのボールが無くなったら終了
	monsterZoo.run();
    }
}
