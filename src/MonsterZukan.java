import java.util.stream.Stream;
import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.util.List;
//import java.util.ArrayList;
import java.util.Arrays;


public class MonsterZukan {
    List<String> monsterZukan =  Stream.generate(() -> "")
	.limit(22)
	.collect(Collectors.toList());

    List<Double> monsterRare = Stream.generate(() -> 0.0)
	.limit(22)
	.collect(Collectors.toList());

    //ユーザがGetしたモンスター一覧
    List<String> userMonster = Stream.generate(() -> "")
	.limit(100)
        .collect(Collectors.toList());

    public void updateUserMonster(int m){
	IntStream.range(0, this.userMonster.size())
	    .filter(j -> this.userMonster.get(j) == "")
	    .findFirst()
	    .ifPresent(j -> this.userMonster.set(j, this.monsterZukan.get(m)));
    }

    public int generateRandomMonster(){
	return (int)(this.monsterZukan.size() * Math.random());
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
}
