package com.DeptManage.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DeptManage.dao.DeptDao;
import com.DeptManage.entity.Dept;
import com.DeptManage.service.DeptService;

@Service
public class DeptServiceImpl implements DeptService {
	@Autowired
	private DeptDao deptDao;

	@Override
	//查询所有数据
	public List<Dept> findAll() {
		// TODO 自动生成的方法存根
		return deptDao.findAll();
	}

	@Override
	//根据部门名称查询数据
	public Dept findByName(String name) {
		// TODO 自动生成的方法存根
		return deptDao.findByName(name);
	}

	@Override
	//修改部门信息
	public boolean update(Dept dept) {
		// TODO 自动生成的方法存根
		boolean flag=false;
		try {
			deptDao.update(dept);
			flag=true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	//增加部门信息
	public boolean add(Dept dept) {
		// TODO 自动生成的方法存根
		boolean flag=false;
		try {
			deptDao.add(dept);
			flag=true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	//删除部门信息
	public boolean delete(int id) {
		// TODO 自动生成的方法存根
		boolean flag=false;
		try {
			deptDao.delete(id);
			flag=true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

}
