package model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.zpf.animmenu.BR;

/**
 * created by zpf on 2018/11/5
 * <p>
 * 数据绑定model
 */
public class BindingModel extends BaseObservable {

    private String firstName;
    private String lastName;
    private int age;

    public BindingModel(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    @Bindable
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        notifyPropertyChanged(BR.firstName);
    }

    @Bindable
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        notifyPropertyChanged(BR.lastName);
    }

    @Bindable
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
        notifyPropertyChanged(BR.age);
    }
}
