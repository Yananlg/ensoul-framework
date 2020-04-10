package club.ensoul.framework.mybatis.support;

import com.google.common.base.Joiner;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;

@Data
public class Pageable {
    
    @ApiModelProperty("查询页码，从0开始计算，默认0")
    private int page = 0;
    
    @ApiModelProperty("每页显示条数，默认20")
    private int size = 20;
    
    @ApiModelProperty("排序信息，格式 sort=xxx,desc&sort=xxx1,asc。asc表示正序，desc表示倒序，多个按数组格式传递即可")
    private String[] sort;
    
    /**
     * 获取排序字符串
     *
     * @return 排序字符串
     */
    public String order() {
        String orderSQL = "";
        if(ArrayUtils.isNotEmpty(sort)) {
            String[] orders = new String[sort.length];
            for(int i = 0; i < sort.length; i++) {
                orders[i] = sort[i].replace(",", " ");
            }
            orderSQL = Joiner.on(", ").join(orders);
        }
        return orderSQL;
    }
    
}
