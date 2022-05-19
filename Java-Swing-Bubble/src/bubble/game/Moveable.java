package bubble.game;

/**
 * 캐릭터 방향을 설정하기 위해 인터페이스 생성
 */
public interface Moveable {
	public void left();
	public void right();
	public void up();
	default public void down() {};//물방을 down()메서드의 강제성을 생략하기 위해 default
	default public void attack() {}; //플레이어가 물방울을 발사하는 행위
	default public void die() {}; //플레이어 사망
}//Moveable
