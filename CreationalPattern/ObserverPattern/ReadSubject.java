import java.util.Observable;

/**
 * @Author neuq-xjh
 * @Date 2019/12/18 0018
 * @Description 读书订阅号
 **/
public class ReadSubject extends Observable {
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
        // 设置改变状态为true，并通知观察者
        setChanged();
        notifyObservers();
    }
}
