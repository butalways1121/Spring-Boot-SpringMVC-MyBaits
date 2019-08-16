package com.DeptManage.service;

import java.util.List;

import com.DeptManage.entity.Dept;

public interface DeptService {
	List<Dept> findAll();//查询所有数据
	Dept findByName(String name);//根据部门名称查询数据
	boolean update(Dept dept);//修改部门信息
	boolean add(Dept dept);//增加部门信息
	boolean delete(int id);//删除部门信息
}
