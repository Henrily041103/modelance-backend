// package com.helloworld.demo.student;

// import org.springframework.web.bind.annotation.RestController;
// import java.util.concurrent.ExecutionException;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.PutMapping;

// @RestController
// public class StudentController {
//     public StudentService stdService;
//     public StudentController(StudentService stdService) {
//         this.stdService = stdService;
//     }
//     @PostMapping("/create")
//     public String createStudent(@RequestBody Student student) throws InterruptedException, ExecutionException {
//         return stdService.createStudent(student);
//     }
//     @GetMapping("/get")
//     public Student getStudent(@RequestParam String id) throws InterruptedException, ExecutionException {
//         return stdService.getStudent(id);
//     }
//     @PutMapping("/update")
//     public String updateStudent(@RequestBody Student student) throws InterruptedException, ExecutionException { 
//         return stdService.updateStudent(student);
//     }
//     @PutMapping("/delete")
//     public String deleteStudent(@RequestParam String id) throws InterruptedException, ExecutionException { 
//         return stdService.deleteStudent(id);
//     }
// }
