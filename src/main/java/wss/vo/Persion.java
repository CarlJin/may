package wss.vo;

/**
 * @ClassName:
 * @desc:
 * @author: jintao
 * @date: 2017-12-11 上午9:49
 **/
public class Persion {
    private String name;
    private Integer age;
    private String gender;

    public Persion() {
    }

    public Persion(String name, Integer age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Persion{");
        sb.append("name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append(", gender='").append(gender).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
