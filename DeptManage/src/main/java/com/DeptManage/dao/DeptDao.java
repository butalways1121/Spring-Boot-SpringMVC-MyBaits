package com.DeptManage.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.DeptManage.entity.Dept;

@Mapper
public interface DeptDao {
	List<Dept> findAll(); //查询所有数据
	Dept findByName(String name);//根据部门名称查询数据
	void update(Dept dept);//修改部门信息
	void add(Dept dept);//增加部门信息
	void delete(int id);//删除部门信息
}
