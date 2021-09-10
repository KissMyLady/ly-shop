package top.mylady.item.mappers;
import top.mylady.item.pojo.User;


public interface User_UserMapper {

    User queryUserById(Long id);

    User feignQueryUserById(Long id);

}
