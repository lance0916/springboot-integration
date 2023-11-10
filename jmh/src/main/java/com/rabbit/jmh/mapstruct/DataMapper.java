package com.rabbit.jmh.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.control.DeepClone;
import org.mapstruct.factory.Mappers;

/**
 * @author WuQinglong
 * @since 2023/11/7 9:59 AM
 */
@Mapper(mappingControl = DeepClone.class)
public interface DataMapper {

    DataMapper INSTANCE = Mappers.getMapper(DataMapper.class);

    DataBean dataBeanToDataBean(DataBean bean);

    ItemBean itemBeanToItemBean(ItemBean bean);

}
