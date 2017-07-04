import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

/**
 * Created by jiangzhiwen592 on 2017/5/31.
 */
public class ZKTest {
    /**
     *
     */
    @Test
    public void getTest() throws IOException, KeeperException, InterruptedException {
        System.out.println("start");
        ZooKeeper zk = new ZooKeeper("localhost:2181", 60000, null);
        System.out.println("ok");
        while(true) {
            try {
                List<String> children = zk.getChildren("/", false);
                System.out.println(children);
            }catch (Throwable t){
                System.out.println(t);
            }finally {
                Thread.sleep(1000);

            }
        }
    }
}
