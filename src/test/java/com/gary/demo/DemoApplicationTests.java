package com.gary.demo;

//import static org.springframework.http.ResponseEntity.status;

import static org.hamcrest.core.IsEqual.equalTo;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.gary.demo.webservice.UserController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    private MockMvc mvc;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new UserController()).build();
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void testUserController() throws Exception {
        /**
         * // 1、get查一下user列表，应该为空
         *         request = get("/users/");
         *         mvc.perform(request)
         *                 .andExpect(status().isOk())
         *                 .andExpect(content().string(equalTo("[]")));
         *
         *         // 2、post提交一个user
         *         request = post("/users/")
         *                 .param("id", "1")
         *                 .param("name", "测试大师")
         *                 .param("age", "20");
         *         mvc.perform(request)
         *                 .andExpect(content().string(equalTo("success")));
         *
         *         // 3、get获取user列表，应该有刚才插入的数据
         *         request = get("/users/");
         *         mvc.perform(request)
         *                 .andExpect(status().isOk())
         *                 .andExpect(content().string(equalTo("[{\"id\":1,\"name\":\"测试大师\",\"age\":20}]")));
         *
         *         // 4、put修改id为1的user
         *         request = put("/users/1")
         *                 .param("name", "测试终极大师")
         *                 .param("age", "30");
         *         mvc.perform(request)
         *                 .andExpect(content().string(equalTo("success")));
         *
         *         // 5、get一个id为1的user
         *         request = get("/users/1");
         *         mvc.perform(request)
         *                 .andExpect(content().string(equalTo("{\"id\":1,\"name\":\"测试终极大师\",\"age\":30}")));
         *
         *         // 6、del删除id为1的user
         *         request = delete("/users/1");
         *         mvc.perform(request)
         *                 .andExpect(content().string(equalTo("success")));
         *
         *         // 7、get查一下user列表，应该为空
         *         request = get("/users/");
         *         mvc.perform(request)
         *                 .andExpect(status().isOk())
         *                 .andExpect(content().string(equalTo("[]")));
         */
        RequestBuilder request = null;
        request = get("/users");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("[]")));
    }

}
