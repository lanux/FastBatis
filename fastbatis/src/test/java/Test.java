import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.lx.mybatis.BaseDataTest;
import org.lx.mybatis.example.Blog;
import org.lx.mybatis.example.BlogMapper;

import javax.sql.DataSource;
import java.io.InputStream;

public class Test extends BaseDataTest{

    public static void main(String[] args) throws Exception {
        System.out.println("BlogMapper.class.getGenericSuperclass() = " + BlogMapper.class.getGenericInterfaces());
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        DataSource dataSource = session.getConfiguration().getEnvironment().getDataSource();
        runScript(dataSource,"");
        try {
            BlogMapper mapper = session.getMapper(BlogMapper.class);
            Blog blog = mapper.selectBlog(101);
        } finally {
            session.close();
        }
//        DataSource dataSource = BlogDataSourceFactory.getBlogDataSource();
//        TransactionFactory transactionFactory = new JdbcTransactionFactory();
//        Environment environment = new Environment("development", transactionFactory, dataSource);
//        Configuration configuration = new Configuration(environment);
//        configuration.addMapper(BlogMapper.class);
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }
}
