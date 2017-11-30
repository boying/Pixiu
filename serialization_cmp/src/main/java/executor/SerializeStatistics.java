package executor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * Created by boying on 17/4/7.
 */
@Data
@AllArgsConstructor
@ToString
public class SerializeStatistics {
    private int times;
    private long totalCost;
    private double avgCost;
    private int byteSize;
}
