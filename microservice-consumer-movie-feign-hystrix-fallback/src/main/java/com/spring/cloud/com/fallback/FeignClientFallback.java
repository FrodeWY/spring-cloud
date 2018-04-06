package com.spring.cloud.com.fallback;

import com.spring.cloud.com.domain.User;
import com.spring.cloud.com.feign_client.UserFeignClient;
import org.springframework.stereotype.Component;

@Component
public class FeignClientFallback implements UserFeignClient {
    @Override
    public User findById(Long id) {
        User user=new User(-1L,"默认");
        return user;
    }

    @Override
    public User getUser(Long id, String username) {
        User user=new User(-1L,"默认");
        return user;
    }

    @Override
    public User getUser(User user) {
        return new User(-1L,"默认");
    }
}
