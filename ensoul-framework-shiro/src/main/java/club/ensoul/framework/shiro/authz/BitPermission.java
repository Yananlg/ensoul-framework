package club.ensoul.framework.shiro.authz;

import org.apache.shiro.authz.Permission;
import org.springframework.util.StringUtils;

/**
 * <pre>
 * 实现位移方式的权限，如规则是：
 * 权限字符串格式：+ 资源字符串 + 权限位 + 实例 ID；以 + 开头中间通过 + 分割；
 * 权限：
 *      0 表示所有权限；
 *      1 新增（二进制：0001）
 *      2 修改（二进制：0010）
 *      4 删除（二进制：0100）
 *      8 查看（二进制：1000）
 * 如 +user+10 表示对资源 user 拥有修改 / 查看权限。
 *      +user1+2     新增权限
 *      +user1+8     查看权限
 *      +user2+10    新增及查看
 *      +user1+4     没有删除权限
 * </pre>
 */
public class BitPermission implements Permission {

    private String resourceIdentify;
    private int permissionBit;
    private String instanceId;

    public BitPermission(String permissionString) {
        String[] array = permissionString.split("\\+");
        if (array.length > 1) {
            resourceIdentify = array[1];
        }
        if (StringUtils.isEmpty(resourceIdentify)) {
            resourceIdentify = "*";
        }
        if (array.length > 2) {
            permissionBit = Integer.valueOf(array[2]);
        }
        if (array.length > 3) {
            instanceId = array[3];
        }
        if (StringUtils.isEmpty(instanceId)) {
            instanceId = "*";
        }
    }

    @Override
    public boolean implies(Permission p) {
        if (!(p instanceof BitPermission)) {
            return false;
        }
        BitPermission other = (BitPermission) p;
        if (!("*".equals(this.resourceIdentify) || this.resourceIdentify.equals(other.resourceIdentify))) {
            return false;
        }
        if (!(this.permissionBit == 0 || (this.permissionBit & other.permissionBit) != 0)) {
            return false;
        }
        if (!("*".equals(this.instanceId) || this.instanceId.equals(other.instanceId))) {
            return false;
        }
        return true;
    }
}