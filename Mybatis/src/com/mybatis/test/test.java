package com.mybatis.test;

import java.io.Reader;
import java.sql.SQLPermission;
import java.util.List;

import javax.websocket.Session;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.mybatis.inter.IUserOperation;
import com.mybatis.model.Article;
import com.mybatis.model.User;

public class test {
    private static SqlSessionFactory sqlSessionFactory;
    private static Reader reader; 

    static{
        try{
            reader= Resources.getResourceAsReader("com/mybatis/resources/Configuration.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static SqlSessionFactory getSession(){
        return sqlSessionFactory;
    }
   @Test
    public void TestselectUserByID() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
        //User user = (User) session.selectOne("com.mybatis.model.UserMapper.selectUserByID", 1);
        //接口方式
       IUserOperation userOperation=session.getMapper(IUserOperation.class);
       User user=userOperation.selectUserByID(1);
       
       System.out.println(user.getUserAddress());
        System.out.println(user.getUserName());
        } finally {
        session.close();
        }
    }
   @Test
   public void getUserList()
   {
	   SqlSession sqlSession=sqlSessionFactory.openSession();
	   try
	   {
		   IUserOperation userOperation=sqlSession.getMapper(IUserOperation.class);
		   List<User> users=userOperation.selectUsers("%");
		   for(User user:users)
		   {
			   System.out.println(user.getUserName()+" "+user.getUserAddress()+"");
		   }
	   }catch (Exception e) {
		sqlSession.close();
	}
   }
   @Test
   public void addUser()
   {
	   System.out.println("开始执行添加函数");
	   User user=new User();
	   user.setUserAddress("海淀区");
	   user.setUserAge("12");
	   user.setUserName("马尔泰若曦");
	   
	   SqlSession session=sqlSessionFactory.openSession();
	   try{

		   System.out.println("开始执行添加函数1");
		   IUserOperation userOperation=session.getMapper(IUserOperation.class);
		   userOperation.addUser(user);
		   session.commit();
		   System.out.println("当前增加用户的id为"+user.getId());
		   System.out.println("成功添加一位用户");
	   }catch (Exception e) {
		   System.out.println("开始执行添加函数2");
		   e.printStackTrace();
		   /*session.close();*/
	   }
   }
   @Test
   public void updateUser()
   {
	   //先得到用户，然后修改，然后提交
	   SqlSession session=sqlSessionFactory.openSession();
	   try{
		   IUserOperation userOperation=session.getMapper(IUserOperation.class);
		   User user=userOperation.selectUserByID(5);
		   user.setUserAddress("上海浦东");
		   userOperation.updateUser(user);
		   session.commit();
	   }catch (Exception e) {
		   e.printStackTrace();
	}finally {
		session.close();
	}
   }
   @Test
   public void testDeleteUser()

   {
	   SqlSession session=sqlSessionFactory.openSession();
	   try{
		   IUserOperation userOperation=session.getMapper(IUserOperation.class);
		   userOperation.deleteUser(5);
		   session.commit();
	   }catch (Exception e) {
		   e.printStackTrace();
	   }finally {
		session.close();
	}
   }
   
   @Test
   public void testgetUserArticles()
   {
	   SqlSession session=sqlSessionFactory.openSession();
	   try{
		   IUserOperation userOperation=session.getMapper(IUserOperation.class);
		   List<Article> articles=userOperation.getUserArticles(1);
		   for(Article article:articles){
               System.out.println(article.getTitle()+":"+article.getContent()+
                       ":作者是:"+article.getUser().getUserName()+":地址:"+
                        article.getUser().getUserAddress());
           }
	   }catch (Exception e) {
		   e.printStackTrace();
	   }finally {
		session.close();
	}
   }
   
}