package bubble.player;

/**
 * ĳ���� ������ �����ϱ� ���� �������̽� ����
 */
public interface Moveable {
	public void left();
	public void right();
	public void up();
	default public void down() {};//������ down()�޼����� �������� �����ϱ� ���� default
}//Moveable
