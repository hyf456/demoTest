package com.han.spel;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class Dept implements Serializable {
    private static final long serialVersionUID = 8197115123957737590L;
    private List<User> userList;
}