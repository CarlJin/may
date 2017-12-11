package wss.reflect;

/**
 * @ClassName:
 * @desc:
 * @author: jintao
 * @date: 2017-12-11 上午11:11
 **/
public class RealSubject implements Subject {
    @Override
    public void rent() {
        System.out.println("I want to rent my house");
    }

    @Override
    public void hello(String str) {
        System.out.println("hello: " + str);
    }
}
