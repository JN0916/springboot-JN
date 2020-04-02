package com.example.springboot.service;

import com.example.springboot.dao.BlogDao;
import com.example.springboot.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BlogServiceTest {
    @Mock
    BlogDao blogDao;
    @InjectMocks
    BlogService blogService;

    @Test
    public void getBlogsFromDb() {
        blogService.getBlogs(1, 10, null);
        verify(blogDao).getBlogs(1, 10, null);
    }

    @Test
    public void returnOkWhenGetAtIndexBlogs() {
        Result result = blogService.getBlogs(1, 10, null);
        Assertions.assertEquals("ok", result.getStatus());
    }

    @Test
    public void returnFailureWhenBlogNotFound() {
        when(blogDao.selectBlogById(1)).thenReturn(null);

        BlogResult result = blogService.deleteBlog(mock(User.class), 1);

        Assertions.assertEquals("fail", result.getStatus());
        Assertions.assertEquals("博客不存在", result.getMsg());
    }

    @Test
    public void returnFailureWhenBlogUserIdNotMatch() {
        User blogAuthor = new User(123, "blogAuthor", "");
        User operator = new User(456, "operator", "");

        Blog targetBlog = new Blog();
        targetBlog.setId(1);
        targetBlog.setUser(operator);

        Blog blogInDb = new Blog();
        blogInDb.setUser(blogAuthor);

        when(blogDao.selectBlogById(1)).thenReturn(blogInDb);
        BlogResult result = blogService.updateBlog(1, targetBlog);

        Assertions.assertEquals("fail", result.getStatus());
        Assertions.assertEquals("无法修改别人的博客", result.getMsg());
    }
}
