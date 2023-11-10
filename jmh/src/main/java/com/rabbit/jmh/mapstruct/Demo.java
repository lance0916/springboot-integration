package com.rabbit.jmh.mapstruct;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WuQinglong
 * @since 2023/11/7 10:00 AM
 */
public class Demo {

    public static void main(String[] args) {
        DataBean bean = new DataBean();
        bean.setFieldName("Tom");
        bean.setItemBean(new ItemBean("c", 3));

        Map<String, ItemBean> map = new HashMap<>();
        map.put("a", new ItemBean("a", 1));
        map.put("b", new ItemBean("b", 2));
        bean.setMap(map);

        List<String> list = Arrays.asList("1", "2");
        bean.setList(list);

        DataBean copy = DataMapper.INSTANCE.dataBeanToDataBean(bean);

        System.out.println(copy);


    }

}
