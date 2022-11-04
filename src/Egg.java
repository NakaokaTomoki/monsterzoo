import java.util.stream.Stream;
import java.util.stream.IntStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.OptionalInt;


public class Egg {
    //卵は最大9個まで持てる．卵を取得するとeggにtrueが代入され，
    //移動するたびに,eggDistanceに1.0kmずつ加算される．
    //3km移動するとランダムでモンスターが孵る
    private List<Double> eggDistance = Stream.generate(() -> 0.0)
	.limit(9)
	.collect(Collectors.toList());

    private List<Boolean> getEggs = Stream.generate(() -> false)
	.limit(9)
	.collect(Collectors.toList());

    private MonsterZukan monsterZukan;
    private int e;
    private int m;

    public Egg(MonsterZukan monsterZukan) {
	//コンストラクタ
	this.monsterZukan = monsterZukan;
    }

    public void checkEggState(){
	for(Integer i: generateEggRange()){
	    if(judgeEggState(this.getEggs.get(i), this.eggDistance.get(i))){
		this.Printer(Stream.of("卵が孵った！"));
		this.m = this.monsterZukan.generateRandomMonster();

		this.Printer(Stream.of(
			    this.monsterZukan.monsterZukan.get(this.m),
			    "が産まれた！"
			    ));

		this.monsterZukan.updateUserMonster(this.m);

		this.getEggs.set(i, false);
		this.eggDistance.set(i, 0.0);
	    }
	}
    }

    public int generateNewEgg(){
	this.e = (int)(Math.random() * 2);
	return this.e;
    }

    public boolean judgeEggState(boolean egg, double eggDist){
	return egg == true && eggDist >= 3;
    }

    public boolean judgeRandomEggState(){
	return this.e >= 1;
    }

    private List<Integer> generateEggRange(){
	return IntStream.range(0, this.getEggs.size())
	    .mapToObj(Integer::valueOf)
	    .collect(Collectors.toList());
    }

    public void newEggSet(){
	OptionalInt eggIsFlase = IntStream.range(0, this.eggDistance.size())
	    .filter(i -> this.getEggs.get(i) == false)
	    .findFirst();
	eggIsFlase.ifPresent(i -> this.getEggs.set(i, true));
	eggIsFlase.ifPresent(i -> this.eggDistance.set(i, 0.0));

    }

    public void updateEggDistance(){
	IntStream.range(0, this.getEggs.size())
	    .filter(i -> this.getEggs.get(i) == true)
	    .forEach(i -> this.eggDistance.set(i, this.eggDistance.get(i)+1));

    }

    private void Printer(Stream<String> st){
	System.out.println(st.collect(Collectors.joining()));
    }
}
