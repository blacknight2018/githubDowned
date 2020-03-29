package com.example.demo.mapper;

import com.example.demo.javabean.dao.List;
import org.springframework.stereotype.Repository;

/**
 * ListDAO继承基类
 */
@Repository
public interface ListDAO extends MyBatisBaseDao<List, Integer> {
}