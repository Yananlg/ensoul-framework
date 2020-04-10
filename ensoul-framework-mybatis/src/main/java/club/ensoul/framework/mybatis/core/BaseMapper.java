package club.ensoul.framework.mybatis.core;

import tk.mybatis.mapper.additional.aggregation.AggregationMapper;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.additional.update.differ.UpdateByDifferMapper;
import tk.mybatis.mapper.additional.update.force.UpdateByPrimaryKeySelectiveForceMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface BaseMapper<T, PK> extends Mapper<T>,
        MySqlMapper<T>,
        IdListMapper<T, PK>,
        UpdateByDifferMapper<T>,
        UpdateByPrimaryKeySelectiveForceMapper<T>,
        AggregationMapper<T> {
}