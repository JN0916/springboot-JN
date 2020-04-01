package com.example.springboot.service;

import com.example.springboot.dao.BlogDao;
import com.example.springboot.entity.Blog;
import com.example.springboot.entity.BlogResult;
import com.example.springboot.entity.Result;
import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class BlogService {
    private BlogDao blogDao;
    private UserService userService;

    @Inject
    public BlogService(BlogDao blogDao, UserService userService) {
        this.blogDao = blogDao;
        this.userService = userService;
    }

    public BlogResult getBlogs(Integer page, Integer pageSize, Integer userId) {
        try {
            List<Blog> blogs = blogDao.getBlogs(page, pageSize, userId);

            blogs.forEach(blog -> blog.setUser(userService.getUserById(blog.getUserId())));

            int count = blogDao.count(userId);

            int pageCount = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
            return BlogResult.newBlogs(blogs, count, page, pageCount);
        } catch (Exception e) {
            return BlogResult.failure("系统异常");
        }
    }

}
