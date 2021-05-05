import com.imooc.permission.App;
import com.imooc.permission.controller.SysUserController;
import com.imooc.permission.entity.param.UserParam;
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
    private SysUserController sysUserController;

    @Test
    public void test(){
        UserParam userParam = new UserParam();
        userParam.setDeptId(1);
        userParam.setUsername("lesleey");
        userParam.setMail("123@qq.com");
        userParam.setTelephone("15035107458");
        userParam.setPassword("123456");
        userParam.setStatus("0");
        sysUserController.saveUser(userParam);

    }

}
