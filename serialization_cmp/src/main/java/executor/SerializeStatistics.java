package executor;

import lombok.Data;

/**
 * Created by jiangzhiwen on 17/4/7.
 */
@Data
public class SerializeStatistics {
    private int times;
    private long totalCost;
    private double avgCost;
    private int byteSize;
}
