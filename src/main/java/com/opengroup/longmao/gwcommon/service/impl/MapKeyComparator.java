/**
 * @Title: MapKeyComparator.java 
 * @Package com.opengroup.longmao.gwcommon.service.impl 
 * @Description:
 * @author Mr.Zhu
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.service.impl;

import java.util.Comparator;

/**
 * @ClassName: MapKeyComparator 
 * @Description: TODO
 * @author Mr.Zhu
 */
public class MapKeyComparator implements Comparator<String>{

    @Override
    public int compare(String str1, String str2) {

        return str1.compareTo(str2);
    }
}
