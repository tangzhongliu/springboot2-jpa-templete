package com.lemon.templete.controller;

import com.lemon.templete.constant.ApiConstant;
import com.lemon.templete.domain.SysUser;
import com.lemon.templete.service.SysUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(ApiConstant.SYS_USER)
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    /**
     * 新增用户
     * @param sysUser 用户实体
     */
    @PostMapping(ApiConstant.ADD)
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody SysUser sysUser) {
        sysUserService.add(sysUser);
    }

    /**
     * 编辑用户
     * @param id 数据主键
     * @param sysUser 用户实体
     */
    @PutMapping(ApiConstant.MODIFY)
    @ResponseStatus(HttpStatus.CREATED)
    public void modify(@PathVariable Integer id, @RequestBody SysUser sysUser) {
        sysUserService.modify(id, sysUser);
    }

    /**
     * 获取所有用户信息
     * @return List
     */
    @GetMapping(ApiConstant.FIND_ALL)
    public Object findAll() {
        List<SysUser> sysUsers = sysUserService.findAll();
        return sysUsers;
    }

    /**
     * 获取所有用户信息（分页模式）
     * @param page 第几页，默认由0开始
     * @param size 每页显示几条记录
     * @return Page
     */
    @GetMapping(ApiConstant.FIND_PAGE)
    public Page<SysUser> findAllByPage(@RequestParam Integer page,
                                         @RequestParam Integer size) {
        Page<SysUser> sysUsers = sysUserService.findAll(PageRequest.of(page, size));
        return sysUsers;
    }

    /**
     * 获取用户信息
     * @param id 数据主键
     * @return SysUser
     */
    @GetMapping(ApiConstant.FIND_ONE)
    public SysUser findById(@PathVariable Integer id) {
        SysUser sysUser = sysUserService.findById(id);
        return sysUser;
    }

    /**
     * 编辑数据状态
     * @param id 数据主键
     * @param status 数据状态（0:无效  1:有效）
     */
    @PutMapping(ApiConstant.MODIFY_STATUS)
    @ResponseStatus(HttpStatus.CREATED)
    public void modifyStatus(@PathVariable Integer id, @RequestParam Integer status) {
        sysUserService.modifyStatus(id, status);
    }

    /**
     * 删除用户
     * @param id 数据主键
     */
    @DeleteMapping(ApiConstant.DEL)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        sysUserService.delete(id);
    }
}
