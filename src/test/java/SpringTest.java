import com.imooc.permission.App;
import com.imooc.permission.dao.SysUserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Lesleey
 * @date 2021/5/3-17:43
 * @function
 */
@SpringBootTest(classes = App.class)
@RunWith(SpringRunner.class)
public class SpringTest {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Test
    public void test(){
        System.out.println(sysUserMapper.selectList(null));
    }

}
