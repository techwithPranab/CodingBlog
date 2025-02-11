package com.blog.bo;

import com.blog.model.User;

public class UserResponseBO {
    
    private User user;
    private ErrorBO errorBO;
    private String userToken;
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public ErrorBO getErrorBO() {
        return errorBO;
    }
    public void setErrorBO(ErrorBO errorBO) {
        this.errorBO = errorBO;
    }
    public String getUserToken() {
        return userToken;
    }
    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}
