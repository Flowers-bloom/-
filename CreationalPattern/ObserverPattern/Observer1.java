import java.util.Observable;
import java.util.Observer;

/**
 * @Author neuq-xjh
 * @Date 2019/12/18 0018
 * @Description 观察者
 **/
public class Observer1 implements Observer{

	// 订阅订阅号
	public void registerSubject(Observable observable) {
		observable.addObserver(this);
	}

	// 监听消息通知方法
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof WorkSubject) {
			System.out.println(((WorkSubject) o).getMsg());
		}else if (o instanceof ReadSubject) {
			System.out.println(((ReadSubject) o).getMsg());
		}
	}


	public static void main(String[] args) {
		WorkSubject workSubject = new WorkSubject();
		ReadSubject readSubject = new ReadSubject();
		Observer1 observer1 = new Observer1();
		observer1.registerSubject(workSubject);
		observer1.registerSubject(readSubject);

		workSubject.setMsg("就业号开始服务了...");
		readSubject.setMsg("读书号今日开始推书...");
	}
}
