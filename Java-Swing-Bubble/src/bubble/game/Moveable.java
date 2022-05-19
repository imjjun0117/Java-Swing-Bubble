package bubble.game;

/**
 * ĳ���� ������ �����ϱ� ���� �������̽� ����
 */
public interface Moveable {
	public void left();
	public void right();
	public void up();
	default public void down() {};//������ down()�޼����� �������� �����ϱ� ���� default
	default public void attack() {}; //�÷��̾ ������� �߻��ϴ� ����
	default public void die() {}; //�÷��̾� ���
}//Moveable
