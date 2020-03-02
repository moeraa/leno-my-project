package proxyDao;

/**
 * @Auther: moer
 * @Date: 2019/9/24 08:47
 * @Description:
 */
public class UserDaoImpl implements UserDao {

  @Override
  public void query(String name){
    System.out.println("query name = " + name);
  }


}
