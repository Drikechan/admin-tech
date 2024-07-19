package org.tech.springcode.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    @NotNull
    private Long id; // 用户id

    private String username; // 用户名

//    @JsonIgnore
    private String password; // 密码

    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private String nickname; // 昵称

    private String avatar; // 头像

    @Email
    @NotEmpty
    private String email; // 邮箱

    private String phone; // 手机号

    private LocalDateTime createTime; // 创建时间

    private LocalDateTime updateTime; // 更新时间
}
