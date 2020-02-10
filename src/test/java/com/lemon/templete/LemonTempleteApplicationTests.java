package com.lemon.templete;

import com.lemon.templete.enums.QueryTypeEnum;
import com.lemon.templete.domain.SysUser;
import com.lemon.templete.repository.SysUserRepository;
import com.lemon.templete.service.SysUserService;
import com.lemon.templete.utils.ServiceUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LemonTempleteApplicationTests {

	@Resource
	private SysUserService sysUserService;
	@Resource
	private RedisTemplate redisTemplate;
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	@Resource
	private SysUserRepository sysUserRepository;

	@Test
	public void contextLoads() {
	}

	/**
	 * 测试Redis的基本CRUD
	 * 1.RedisTemplate 可以存储实现Serializable接口的对象
	 * 2.StringRedisTemplate 主要用来存储字符串
	 */
	@Test
	public void testRedis() {
		// 新增 key: name, value: ay
		redisTemplate.opsForValue().set("user_name", "Bob");
		String name = (String)redisTemplate.opsForValue().get("user_name");
		System.out.println(name);

		// 删除
		redisTemplate.delete("user_name");

		// 更新
		redisTemplate.opsForValue().set("user_name", "Tom");
		name = (String)redisTemplate.opsForValue().get("user_name");
		System.out.println(name);

		// StringRedisTemplate更新&查询
		stringRedisTemplate.opsForValue().set("user_name", "Ben");
		name = stringRedisTemplate.opsForValue().get("user_name");
		System.out.println(name);
	}

	/**
	 * 测试PasswordEncoder加密
	 * 本系统集成了Spring-Security，login认证授权成功后会通过该加密方式加密登录密码存储到DB中
	 */
	@Test
	public void testPasswordEncoder() {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String password = passwordEncoder.encode("123");
		System.out.println(password);
	}

	/**
	 * 测试自定义的JPA的动态查询方法
	 */
	@Test
	public void testQueryConditionAnd() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(ServiceUtils.getQueryKey("userName", QueryTypeEnum.NE.name()), "123");
		param.put(ServiceUtils.getQueryKey("createTime", QueryTypeEnum.GT.name()), Timestamp.valueOf("2019-07-07 23:59:59"));
		param.put(ServiceUtils.getQueryKey("createTime", QueryTypeEnum.LT.name()), Timestamp.valueOf("2019-10-08 00:00:00"));

		// 多条件查询(多个条件间是and关系)，返回结果列表
		List<SysUser> sysUsers1 = sysUserService.findByConditionAnd(param);
		// 多条件查询(多个条件间是and关系)，指定字段排序，返回结果列表
		List<SysUser> sysUsers2 = sysUserService.findByConditionAnd(param, Sort.by(Sort.Direction.ASC, "createTime"));
		// 多条件分页查询(多个条件间是and关系)，指定字段排序，返回分页结果
		Page<SysUser> sysUsers3 = sysUserService.findByConditionAnd(param, PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "createTime")));
		System.out.println("done");
	}

	/**
	 * 测试SysUserRepository中自定义的方法
	 */
	@Test
	public void testRepository() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);

		List<SysUser> sysUsers1 = sysUserRepository.findByUserNameLike("%t%");
		List<SysUser> sysUsers2 = sysUserRepository.findByIdIn(ids);
		List<SysUser> sysUsers3 = sysUserRepository.findBySysRolesRoleName("ROLE_ADMIN");
		Page<SysUser> sysUsers4 = sysUserRepository.findByUserNameAndStatusWithJPQL("t", 1, PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "createTime")));
		Page<SysUser> sysUsers5 = sysUserRepository.findByUserNameAndStatusWithSQL("t", 1, PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "create_time")));
		System.out.println("done");
	}
}
