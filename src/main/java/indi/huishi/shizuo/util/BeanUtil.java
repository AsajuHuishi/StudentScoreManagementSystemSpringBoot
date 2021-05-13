package indi.huishi.shizuo.util;

import indi.huishi.shizuo.pojo.Statistics;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Huishi
 * @Date: 2021/5/12 23:44
 */

/**
 * 基于反射，将obj转为map
 */
public class BeanUtil {
    /**
     * Pojo -> Map<String, Object>
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String,Object> object2Map(Object obj) throws Exception{
        Map<String,Object> map = new HashMap<String, Object>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for(Field field:fields){
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }
        return map;
    }

    /**
     * List<T> --> List<Map<String, Object>>
     * @param objectList
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<Map<String, Object>> objectList2ListMap(List<T> objectList) throws Exception {
        ArrayList<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        for (T t : objectList) {
            resultList.add(object2Map(t));
        }
        return resultList;
    }

    /**
     * List<T> --> Map<String, List<Object>>
     * @param objectList
     * @param keyName
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> Map<String, List<Object>> objectList2MapList(List<T> objectList, String[] keyName) throws Exception{
        Map<String, List<Object>> resultMap = new HashMap<>();
        for(int i = 0; i < keyName.length; i++){
            List<Object> arrayList = new ArrayList<>();
            for (T t: objectList){// List有序，所以对每个对象依次变为map,然后得到对应的值，存入arrayList
                arrayList.add(object2Map(t).get(keyName[i]));
            }
            resultMap.put(keyName[i], arrayList);//将keyName和对应List集合存入resultMap
        }
        return resultMap;
    }


}
