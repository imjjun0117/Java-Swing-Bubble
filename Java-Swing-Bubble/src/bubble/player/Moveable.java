package bubble.player;

/**
 * 캐릭터 방향을 설정하기 위해 인터페이스 생성
 */
public interface Moveable {
	public void left();
	public void right();
	public void up();
	default public void down() {};//물방을 down()메서드의 강제성을 생략하기 위해 default
}//Moveable
