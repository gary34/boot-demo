package com.gary.demo.webservice;

import com.gary.demo.model.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;

/**
 * 平台名-子平台名-模块名:
 * <p>
 * [注释信息]
 *
 * @author gary.pu  2018-10-06
 */

@RestController
@RequestMapping("/users")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static Map<String, User> users = new HashMap<>();
    private static ExecutorService FIXED_THREAD_POOL = Executors.newFixedThreadPool(4);

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<User> users() {
        //        return Arrays.asList(users.values());
        return new ArrayList<>(users.values());
    }

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json")
    public List<User> addUser(@RequestBody User user) {
        users.put(user.getName(), user);
        return this.users();
    }

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = {"multipart/form-data",
                                                                         "application/x-www-form-urlencoded"})
    public List<User> addUser0(@ModelAttribute User user) {
        return this.addUser(user);
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public User getUser(@PathVariable String name) {
        if (users.containsKey(name)) {
            return users.get(name);
        }
        return new User();
    }

    //http://www.importnew.com/29849.html

    @RequestMapping(value = "/asyncList", method = RequestMethod.GET)
    public List<User> asyncUsers(HttpServletRequest request, HttpServletResponse response) throws InterruptedException {
        AsyncContext asyncContext = request.startAsync();
        logger.info("start async...");
        asyncContext.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent event) throws IOException {
                logger.info("onComplete");
            }

            @Override
            public void onTimeout(AsyncEvent event) throws IOException {
                logger.info("onTimeout");
            }

            @Override
            public void onError(AsyncEvent event) throws IOException {
                logger.info("onError");
            }

            @Override
            public void onStartAsync(AsyncEvent event) throws IOException {
                logger.info("onStartAsync");
            }
        });
        asyncContext.setTimeout(100);
        asyncContext.start(() -> {
            logger.info("async start...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            asyncContext.complete();
        });
        logger.info("线程：" + Thread.currentThread().getName());
        return this.users();
    }

    @RequestMapping(value = "/asyncList1", method = RequestMethod.GET)
    public Callable<List<User>> asyncUsers1() {
        logger.info("外部线程：" + Thread.currentThread().getName());
        return () -> {
            Thread.sleep(1000);
            logger.info("内部线程：" + Thread.currentThread().getName());
            return UserController.this.users();
        };
    }

    @RequestMapping(value = "/asyncList2", method = RequestMethod.GET)
    public DeferredResult<List<User>> asyncUsers2() {
        logger.info("外部线程：" + Thread.currentThread().getName());
        DeferredResult<List<User>> result = new DeferredResult<>(100L);
        result.onTimeout(() -> {
            logger.info("DeferResult 超时...");
            result.setResult(new ArrayList<>());
        });
        result.onCompletion(() -> {
            logger.info("DeferResult Complete");
        });
        FIXED_THREAD_POOL.execute(() -> {
            logger.info("内部线程：" + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result.setResult(UserController.this.users());
        });
        return result;
    }
    @RequestMapping(value = "/asyncList3", method = RequestMethod.GET)
    public WebAsyncTask<List<User>> asyncUsers3() {
        logger.info("外部线程：" + Thread.currentThread().getName());
        WebAsyncTask<List<User>> task = new WebAsyncTask<>(100L, () -> {
            logger.info("内部线程：" + Thread.currentThread().getName());
            Thread.sleep(1000);
            return UserController.this.users();
        });
        task.onCompletion(()->{
            logger.info("WebAsync complete");
        });
        task.onTimeout(()->{
            logger.info("WebAsync timeout");
            return new ArrayList<>();
        });
        return task;
    }
}
