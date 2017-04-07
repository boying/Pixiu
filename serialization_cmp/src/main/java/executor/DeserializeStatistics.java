package executor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * Created by jiangzhiwen on 17/4/7.
 */
@Data
@AllArgsConstructor
@ToString
public class DeserializeStatistics {
    private int times;
    private long totalCost;
    private double avgCost;
}
