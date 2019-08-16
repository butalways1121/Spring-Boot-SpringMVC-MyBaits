package com.DeptManage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.DeptManage.entity.Dept;
import com.DeptManage.service.DeptService;

@RestController
@RequestMapping(value = "/api")
public class DeptController {
	@Autowired
	private DeptService deptService;
	
	//查询所有信息
	@RequestMapping(value = "/findAll",method = RequestMethod.GET)
	public List<Dept> findAll(){
		System.out.println("开始查询所有信息！");
		return deptService.findAll();
	}
	
	
	//根据部门名称查询信息
	@RequestMapping(value = "/findByName",method = RequestMethod.GET)
	public Dept findByName(@RequestParam(value = "name",required = true) String name){
		System.out.println("开始根据部门名称查询信息！");
		return deptService.findByName(name);
	}
	
	//修改部门信息
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	public boolean update(Dept dept){
		System.out.println("开始修改部门信息！");
		return deptService.update(dept);
	}
	
	//增加部门信息
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	public boolean add(Dept dept){
		System.out.println("开始增加部门信息！");
		return deptService.add(dept);
	}
	
	//删除部门信息
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	public boolean delete(@RequestParam(value = "id",required = true) int id){
		System.out.println("开始删除部门信息！");
		return deptService.delete(id);
	}
}
