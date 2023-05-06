package com.snailwu.mongo;

import cn.hutool.json.JSONUtil;
import com.snailwu.mongo.entity.Student;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.Resource;

@SpringBootApplication
public class MongoApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(MongoApplication.class, args);
    }

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        Student student = new Student();
//        student.setId(4);
//        student.setName("Marry");
//        student.setAge(18);
//        student = mongoTemplate.insert(student);
//        System.out.println(JSONUtil.toJsonPrettyStr(student));

        // 查询
//        Student student = mongoTemplate.findById(4, Student.class);
//        System.out.println(JSONUtil.toJsonPrettyStr(student));

//        List<Student> list = mongoTemplate.findAll(Student.class);
//        System.out.println(JSONUtil.toJsonPrettyStr(list));

//        List<Student> list = mongoTemplate.find(
//                Query.query(Criteria
//                        .where("age").gt(18)
//                        .and("name").ne("Tom")
//                ),
//                Student.class
//        );
//        System.out.println(JSONUtil.toJsonPrettyStr(list));

//        long count = mongoTemplate.count(
//                Query.query(Criteria
//                        .where("age").gt(18)
//                        .and("name").ne("Tom")
//                ),
//                Student.class
//        );
//        System.out.println(count);

        // 只会更新第一个
//        Student student = mongoTemplate.findAndModify(
//                Query.query(Criteria
//                        .where("age").gt(18)
//                        .and("name").ne("Tom")
//                ),
//                Update.update("name", "newName"),
//                Student.class
//        );
//        System.out.println(JSONUtil.toJsonPrettyStr(student));

//        DeleteResult result = mongoTemplate.remove(
//                Query.query(Criteria
//                        .where("age").gt(18)
//                        .and("name").ne("Tom")
//                ),
//                Student.class
//        );
//        System.out.println(result.getDeletedCount());

    }

}
