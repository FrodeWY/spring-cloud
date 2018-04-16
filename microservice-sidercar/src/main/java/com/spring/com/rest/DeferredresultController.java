package com.spring.com.rest;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
public class DeferredresultController {


    private ConcurrentLinkedDeque<DeferredResult<String>> deferredResults =
            new ConcurrentLinkedDeque<DeferredResult<String>>();


    @RequestMapping("/getResult")
    @ResponseBody
    public DeferredResult<String> getDeferredResultController() {

        //设置 5秒就会超时
        final DeferredResult<String> stringDeferredResult = new DeferredResult<String>(1000L);

        //将请求加入到队列中
        deferredResults.add(stringDeferredResult);

        final String message = "{username:wangbinghua}";

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.submit(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(2010);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //业务处理
                System.out.println("业务处理");
                stringDeferredResult.setResult(message);
            }
        });


        //setResult完毕之后，调用该方法
        stringDeferredResult.onCompletion(new Runnable() {
            @Override
            public void run() {
                System.out.println("异步调用完成");
                //响应完毕之后，将请求从队列中去除掉
                deferredResults.remove(stringDeferredResult);
            }
        });

        stringDeferredResult.onTimeout(new Runnable() {
            @Override
            public void run() {
                System.out.println("业务处理超时");
                stringDeferredResult.setResult("error:timeOut");
            }
        });
        return stringDeferredResult;
    }

//    开启线程定时扫描队列，响应客户端
    @Scheduled(fixedRate = 1000)
    public void scheduleResult() {
        System.out.println(new Date());
        for (int i = 0; i < deferredResults.size(); i++) {
            DeferredResult<String> deferredResult = deferredResults.getFirst();
            deferredResult.setResult("result:" + i);
        }


    }
}