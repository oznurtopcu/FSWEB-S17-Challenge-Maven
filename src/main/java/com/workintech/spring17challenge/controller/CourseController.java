package com.workintech.spring17challenge.controller;

import com.workintech.spring17challenge.entity.Course;
import com.workintech.spring17challenge.entity.HighCourseGpa;
import com.workintech.spring17challenge.entity.LowCourseGpa;
import com.workintech.spring17challenge.entity.MediumCourseGpa;
import com.workintech.spring17challenge.exceptions.ApiException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private Map<Integer,Course> courses;
    private LowCourseGpa lowCourseGpa;
    private MediumCourseGpa mediumCourseGpa;
    private HighCourseGpa highCourseGpa;

    @Autowired
    public CourseController(LowCourseGpa lowCourseGpa, MediumCourseGpa mediumCourseGpa, HighCourseGpa highCourseGpa) {
        this.lowCourseGpa = lowCourseGpa;
        this.mediumCourseGpa = mediumCourseGpa;
        this.highCourseGpa = highCourseGpa;
    }

    @PostConstruct
    public void init() {
        this.courses = new HashMap<>();
    }

    @GetMapping
    public List<Course> findAll() {
        return courses.values().stream().toList();
    }

    @GetMapping("/{name}")
    public Course findByName(@PathVariable String name) {
        List<Course> matching = new ArrayList<>();
        return courses.values().stream()
                .filter(course -> course.getName()
                        .equals(name)).findFirst()
                .orElseThrow(()-> new ApiException("ilgili isimle eşleşen kayıt bulunamadı", HttpStatus.NOT_FOUND));

//        for (Course course: courses.values()) {
//            if(course.getName().trim().equalsIgnoreCase(name.trim())) {
//                matching.add(course);
//            }
//        }

    }

    @PostMapping
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        courses.put(course.getId(),course);
        double totalGpa;
        if(course.getCredit()<=2) {
            totalGpa = course.getGrade().getCoefficient() * course.getCredit() * lowCourseGpa.getGpa();
        }else if(course.getCredit()<=3) {
            totalGpa = course.getGrade().getCoefficient() * course.getCredit() * mediumCourseGpa.getGpa();
        }else if(course.getCredit()<=4) {
            totalGpa = course.getGrade().getCoefficient() * course.getCredit() * highCourseGpa.getGpa();
        }

        //CourseResponse response = new CourseResponse(course, totalGpa);
        return new ResponseEntity<>(courses.get(course.getId()), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public Course update(@PathVariable int id, @RequestBody Course newCourse) {
        courses.replace(id, newCourse);
        return courses.get(id);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable int id) {
        courses.remove(id);

    }


}
