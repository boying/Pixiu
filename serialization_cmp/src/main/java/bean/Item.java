package bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by jiangzhiwen on 17/4/7.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private int type;
    private String desc;
}
