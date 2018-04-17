package com.spring.com.rest;

import com.google.common.collect.Maps;
import com.spring.com.domain.User;
import com.spring.com.service.AggregationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import rx.Observable;
import rx.Observer;

import java.util.HashMap;
import java.util.Map;


@RestController
public class AggregationController {
    public final  Logger LOGGER=LoggerFactory.getLogger(AggregationController.class);
    @Autowired
    private AggregationService aggregationService;

    @GetMapping(value = "/aggregation/{id}" ,produces = "application/json;charset=UTF-8",consumes = "application/json;charset=UTF-8")
    public DeferredResult<HashMap<String ,User>> aggregation(@PathVariable Long id){
        Observable<HashMap<String,User>> result =aggregationObservable(id);
        return toDeferrendResult(result);
    }
    public Observable<HashMap<String,User>> aggregationObservable(Long id){
        return Observable.zip(
                this.aggregationService.getUserById(id),
                this.aggregationService.getMovieUserByUserId(id),
                (user,movieUser)->{
                 HashMap<String,User> hashMap=Maps.newHashMap();
                 hashMap.put("user",user);
                 hashMap.put("movieUser",movieUser);
                 return hashMap;
                }
        );
    }
    public DeferredResult<HashMap<String,User>> toDeferrendResult(Observable<HashMap<String,User>> observable){
        DeferredResult<HashMap<String,User>> deferredResult=new DeferredResult<>();
        observable.subscribe(new Observer<HashMap<String, User>>() {
            @Override
            public void onCompleted() {
                LOGGER.info("完成。。。");
            }

            @Override
            public void onError(Throwable throwable) {
                LOGGER.info("发生错误。。"+throwable.getMessage());
            }

            @Override
            public void onNext(HashMap<String, User> stringUserHashMap) {
                deferredResult.setResult(stringUserHashMap);
            }
        });
        return deferredResult;
    }
}
