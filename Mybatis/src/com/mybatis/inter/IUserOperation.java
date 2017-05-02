package com.mybatis.inter;

import java.util.List;

import com.mybatis.model.Article;
import com.mybatis.model.User;

public interface IUserOperation {
  public User selectUserByID(int id);
  public List<User> selectUsers(String userName);
  public void addUser(User user);
  public void updateUser(User user);
  public void deleteUser(int id);
  
  public List<Article> getUserArticles(int id);
}
