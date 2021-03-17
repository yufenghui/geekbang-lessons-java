package cn.yufenghui.lession.user.management;

/**
 * @author Yu Fenghui
 * @date 2021/3/17 16:09
 * @since
 */
public interface UserManagerMBean {

    Long getId();

    void setId(Long id);

    String getName();

    void setName(String name);

    String getPassword();

    void setPassword(String password);

    String getEmail();

    void setEmail(String email);

    String getPhoneNumber();

    void setPhoneNumber(String phoneNumber);

    @Override
    String toString();

}
