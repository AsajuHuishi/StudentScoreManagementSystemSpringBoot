package indi.huishi.shizuo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 建立stu_score表相同的字段
 +-----------+-------------+------+-----+---------+----------------+
 | Field     | Type        | Null | Key | Default | Extra          |
 +-----------+-------------+------+-----+---------+----------------+
 | NAME      | varchar(20) | YES  |     | NULL    |                |
 | score     | float       | YES  |     | NULL    |                |
 | class_name| int(11)     | YES  |     | NULL    |                |
 | no        | varchar(20) | YES  | UNI | NULL    |                |
 | id        | int(11)     | NO   | PRI | NULL    | auto_increment |
 +-----------+-------------+------+-----+---------+----------------+
 * @author AsajuHuishi
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Student {
	private Integer id;
	private String no;
	private String name;
	private Float score;
	private Integer className;
}
