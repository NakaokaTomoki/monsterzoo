import java.util.stream.Stream;
import java.util.stream.IntStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.OptionalInt;


public class Egg {
    //卵は最大9個まで持てる．卵を取得するとeggにtrueが代入され，
    //移動するたびに,eggDistanceに1.0kmずつ加算される．
    //3km移動するとランダムでモンスターが孵る
    List<Double> eggDistance = Stream.generate(() -> 0.0)
	.limit(9)
	.collect(Collectors.toList());

    List<Boolean> getEggs = Stream.generate(() -> false)
	.limit(9)
	.collect(Collectors.toList());

    MonsterZukan monsterZukan;

    //int e = (int)(Math.random() * 2);
    int e;

    public Egg(MonsterZukan monsterZukan) {
	//コンストラクタ
	this.monsterZukan = monsterZukan;
    }

    public int generateNewEgg(){
	this.e = (int)(Math.random() * 2);
	return this.e;
    }

    public boolean judgeRandomEggState(){
	return this.e >= 1;
    }

    public void checkEggState(){
	List<Integer> egg_size_range = IntStream.range(0, this.getEggs.size())
	    .mapToObj(Integer::valueOf)
	    .collect(Collectors.toList());

	for(Integer i: egg_size_range){
	    if(this.getEggs.get(i) == true && this.eggDistance.get(i) >= 3){
		System.out.println("卵が孵った！");
		int m = (int)(this.monsterZukan.monsterZukan.size() * Math.random());

		System.out.println(this.monsterZukan.monsterZukan.get(m) + "が産まれた！");

		IntStream.range(0, this.monsterZukan.userMonster.size())
		    .filter(j -> this.monsterZukan.userMonster.get(j) == "")
		    .findFirst()
		    .ifPresent(j -> this.monsterZukan.userMonster.set(j, this.monsterZukan.monsterZukan.get(m)));

		this.getEggs.set(i, false);
		this.eggDistance.set(i, 0.0);
	    }
	}
    }

    public void newEggSet(){
	OptionalInt egg_is_flase = IntStream.range(0, this.eggDistance.size())
	    .filter(i -> this.getEggs.get(i) == false)
	    .findFirst();
	egg_is_flase.ifPresent(i -> this.getEggs.set(i, true));
	egg_is_flase.ifPresent(i -> this.eggDistance.set(i, 0.0));

    }

    public void updateEggDistance(){
	IntStream.range(0, this.getEggs.size())
	    .filter(i -> this.getEggs.get(i) == true)
	    .forEach(i -> this.eggDistance.set(i, this.eggDistance.get(i)+1));

    }
}
