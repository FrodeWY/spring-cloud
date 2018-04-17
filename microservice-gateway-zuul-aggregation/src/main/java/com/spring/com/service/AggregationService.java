package com.spring.com.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.spring.com.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;


@Service
public class AggregationService {
    @Autowired
    private RestTemplate restTemplate;
    @HystrixCommand(fallbackMethod = "fallback")
    public Observable<User> getUserById(Long id){
        return Observable.create(observer->{
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            MediaType mediaType=MediaType.parseMediaType("application/json;charset=UTF-8");
            headers.add("accept",mediaType.toString());
            HttpEntity entity=new HttpEntity(headers);
            ResponseEntity<User> responseEntity=restTemplate.exchange("http://microservice-provider-user/user/{id}",HttpMethod.GET,entity,User.class,id) ;
           observer.onNext( responseEntity.getBody());
           observer.onCompleted();
        });
    }

    @HystrixCommand(fallbackMethod ="fallback" )
    public Observable<User> getMovieUserByUserId(Long id){
        return  Observable.create(observer->{
           User user=restTemplate.getForObject("http://microservice-consumer-movie/user/{id}",User.class,id);
           observer.onNext(user);
           observer.onCompleted();
        });
    }
    public User fallback(Long id){
        User user=new User();
        user.setId(-1L);
        return user;
    }
}
