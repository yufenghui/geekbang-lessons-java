package cn.yufenghui.lession.mybatis.entity;

/**
 * @author Yu Fenghui
 * @date 2021/5/11 15:52
 * @since
 */
public class UserEntity {

    private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
