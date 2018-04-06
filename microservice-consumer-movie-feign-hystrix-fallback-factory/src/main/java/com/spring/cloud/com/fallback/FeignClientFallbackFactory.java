package com.spring.cloud.com.fallback;

import com.spring.cloud.com.domain.User;
import com.spring.cloud.com.feign_client.UserFeignClient;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FeignClientFallbackFactory implements FallbackFactory<UserFeignClient> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FeignClientFallbackFactory.class);

    @Override
    public UserFeignClient create(Throwable throwable) {

        return new UserFeignClient() {
            @Override
            public User findById(Long id) {
                LOGGER.info("fallback;reason was:{}", throwable.getMessage());
                User user = new User(-1L, "默认");
                return user;
            }

            @Override
            public User getUser(Long id, String username) {
                LOGGER.info("fallback;reason was:{}", throwable.getMessage());

                User user = new User(-1L, "默认");
                return user;
            }

            @Override
            public User getUser(User user) {
                LOGGER.info("fallback;reason was:{}", throwable.getMessage());

                return new User(-1L, "默认");
            }
        };
    }
}
