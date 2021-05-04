import com.imooc.permission.App;
import com.imooc.permission.controller.SysDeptController;
import com.imooc.permission.dao.SysUserMapper;
import com.imooc.permission.entity.param.DeptParam;
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
    private SysDeptController sysDeptController;

    @Test
    public void test(){
        DeptParam deptParam = new DeptParam();
        deptParam.setId(5);
        deptParam.setName("桌面部");
        deptParam.setSeq(3);
        deptParam.setParentId(0);
        sysDeptController.update(deptParam);
    }

}
