package bubble.move;

/**
 * ĳ���� ������ �����ϱ� ���� �������̽� ����
 */
public interface Moveable {
	public void left();
	public void right();
	public void up();
	default public void down() {};//������ down()�޼����� �������� �����ϱ� ���� default
	default public void attack() {}; //�÷��̾ ������� �߻��ϴ� ����
}//Moveable