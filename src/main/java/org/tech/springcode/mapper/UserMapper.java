package org.tech.springcode.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.tech.springcode.pojo.User;

@Mapper
public interface UserMapper {
    @Insert("insert into user(username,password,create_time,update_time)" +
            " values(#{username},#{password},now(),now())")
      void register(User user);

    @Select("select * from user where username=#{username}")
    User findByUserName(String username);

    void deleteUser(User user);
}
