package club.ensoul.framework.mybatis.core;

import club.ensoul.framework.core.domain.IModel;
import club.ensoul.framework.mybatis.core.service.DeleteService;
import club.ensoul.framework.mybatis.core.service.InsertService;
import club.ensoul.framework.mybatis.core.service.SelectService;
import club.ensoul.framework.mybatis.core.service.UpdateService;

/**
 * 通用Service方法接口
 *
 * @param <T> 数据库model模型
 * @author wy_peng_chen6
 * @see InsertService
 * @see DeleteService
 * @see UpdateService
 * @see SelectService
 */
public interface BaseService<T extends IModel, PK> extends
        SelectService<T, PK>,
        InsertService<T, PK>,
        UpdateService<T, PK>,
        DeleteService<T, PK> {
    
    
}
