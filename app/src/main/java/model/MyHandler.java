package model;

/**
 * created by zpf on 2018/11/5
 */
public class MyHandler {

    public void changeAge(BindingModel model) {

        model.setAge(model.getAge() + 1);
    }
}
