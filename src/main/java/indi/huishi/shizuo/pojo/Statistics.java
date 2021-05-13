package indi.huishi.shizuo.pojo;

/**
 * @Author: Huishi
 * @Date: 2021/4/17 22:31
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 统计
 * @author Huishi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Statistics {
    private String className;
    private Double maxScore;
    private Double minScore;
    private Double avgScore;
    private Integer countStudent;// 学生人数

}
