package org.tech.springcode.model.resp;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class LoginResp implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String token;
}
