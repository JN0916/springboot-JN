package com.example.springboot.controller;

import com.example.springboot.entity.*;
import com.example.springboot.service.AuthService;
import com.example.springboot.service.BlogService;
import com.example.springboot.utils.AssertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class BlogController {
    private final BlogService blogService;
    private final AuthService authService;

    public BlogController(BlogService blogService, AuthService authService) {
        this.blogService = blogService;
        this.authService = authService;
    }


    @GetMapping("/blog")
    @ResponseBody
    public BlogListResult getBlogs(@RequestParam("page") Integer page,
                                   @RequestParam(value = "userId", required = false) Integer userId) {
        if (page == null || page < 0) {
            page = 1;
        }
        return blogService.getBlogs(page, 10, userId);
    }

    @GetMapping("/blog/{blogId}")
    @ResponseBody
    public BlogResult getBlog(@PathVariable("blogId") int blogId) {
        return blogService.getBlogById(blogId);
    }

    @PostMapping("/blog")
    @ResponseBody
    public BlogResult newBlog(@RequestBody Map<String, String> param) {
        try {
            return (BlogResult) authService.getCurrentUser()
                    .map(user -> blogService.insertBlog(fromParam(param, user)))
                    .orElse(BlogResult.failure("登录后才能操作"));
        } catch (IllegalArgumentException e) {
            return BlogResult.failure(e);
        }
    }


    @PatchMapping("/blog/{blogId}")
    @ResponseBody
    public BlogResult updateBlog(@PathVariable("blogId") int blogId,
                                 @RequestBody(required = false) Map<String, String> param) {
        try {
            return authService.getCurrentUser()
                    .map(user -> blogService.updateBlog(blogId, (fromParam(param, user))))
                    .orElse(BlogResult.failure("登录后才能操作"));

        } catch (IllegalArgumentException e) {
            return BlogResult.failure(e);
        }
    }

    @DeleteMapping("/blog/{blogId}")
    @ResponseBody
    public Result deleteBlog(@PathVariable("blogId") int blogId) {
        try {
            return authService.getCurrentUser().map(user -> blogService.deleteBlog(user, blogId))
                    .orElse(BlogResult.failure("登录后才能操作"));
        } catch (IllegalArgumentException e) {
            return BlogResult.failure(e.getMessage());
        }
    }

    private Blog fromParam(Map<String, String> params, User user) {
        Blog blog = new Blog();
        String title = (String) params.get("title");
        String content = (String) params.get("content");
        String description = (String) params.get("description");


        AssertUtils.assertTrue(StringUtils.isNotBlank(title) && title.length() < 100, "标题错误");
        AssertUtils.assertTrue(StringUtils.isNotBlank(content) && content.length() < 1000, "内容错误");

        if (StringUtils.isBlank(description)) {
            description = content.substring(0, Math.min(description.length(), 10)) + "...";
        }

        blog.setTitle(title);
        blog.setContent(content);
        blog.setDescription(description);
        blog.setUser(user);
        return blog;
    }
}
