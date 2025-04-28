package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    /**
     * 根据菜品ID查询是否关联套餐
     * @param dishIds
     * @return
     */
    List<Long> getSetmealDishIdsByDishId(List<Long> dishIds);
}
