package com.rabbit.jmh.mapstruct;

import java.util.List;
import java.util.Map;

/**
 * @author WuQinglong
 * @since 2023/11/7 9:57 AM
 */
public class DataBean {

    private String fieldName;
    private ItemBean itemBean;

    private List<String> list;
    private Map<String, ItemBean> map;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public Map<String, ItemBean> getMap() {
        return map;
    }

    public void setMap(Map<String, ItemBean> map) {
        this.map = map;
    }

    public ItemBean getItemBean() {
        return itemBean;
    }

    public void setItemBean(ItemBean itemBean) {
        this.itemBean = itemBean;
    }
}
