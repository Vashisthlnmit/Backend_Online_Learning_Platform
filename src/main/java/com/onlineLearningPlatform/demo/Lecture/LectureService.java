package com.onlineLearningPlatform.demo.Lecture;

import com.onlineLearningPlatform.demo.Common.PageResponse;
import com.onlineLearningPlatform.demo.Course.Course;
import com.onlineLearningPlatform.demo.Course.CourseRepository;
import com.onlineLearningPlatform.demo.File.FileUploadService;
import com.onlineLearningPlatform.demo.User.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.naming.OperationNotSupportedException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;
    private final CourseRepository courseRepository;
    private final FileUploadService fileUploadService;
    private final LectureMapper lectureMapper;
    public String addLecture(LectureRequest request, int courseId, Authentication connectedUser) throws Exception {
        User user=((User) connectedUser.getPrincipal());
        Course course=courseRepository.findById(courseId).orElseThrow(
                ()->new IllegalStateException("Course not found")
        );
        if(!Objects.equals(user.getId(),course.getOwner().getId())){
            throw new  OperationNotSupportedException("you are not owner of this course so you cannot add lecture to it");
        }
        String Lectureurlfile=fileUploadService.saveFile(request.getLectureUrl(),courseId);
        if(Lectureurlfile.isEmpty()){
            throw new Exception("some internal server error while uploading the file");
        }
        String LectureThumbnailfile=fileUploadService.saveFile(request.getLectureThumbnail(),courseId);
        if(LectureThumbnailfile.isEmpty()){
            throw new Exception("some internal server error while uploading the file");
        }
        Lecture lecture=Lecture.builder()
                .LectureTitle(request.getLectureTitle())
                .LectureDescription(request.getLectureDescription())
                .LectureUrl(Lectureurlfile)
                .LectureThumbnail(LectureThumbnailfile)
                .course(course)
                .build();
        lectureRepository.save(lecture);
        return "lecture added successfully";
    }
    public LectureResponse findOneLecture(int lectureId, Authentication connectedUser) {
        User user=((User) connectedUser.getPrincipal());
        Lecture lecture=lectureRepository.findById(lectureId).orElseThrow(
                ()->new IllegalStateException("Lecture not found")
        );
        return lectureMapper.toLectureResponse(lecture);
    }
    public String deleteoneLecture(int lectureId, Authentication connectedUser) throws Exception {
        User user=((User) connectedUser.getPrincipal());
        Lecture lecture=lectureRepository.findById(lectureId).orElseThrow(
                ()->new IllegalStateException("Lecture not found")
        );
        if(!Objects.equals(user.getId(),lecture.getCourse().getOwner().getId())){
            throw new Exception("you are not authorize to delete this lecture");
        }
        lectureRepository.deleteById(lectureId);
        return "Lecture deleted successfully";
    }

    public String updateoneLecture(LectureRequest request, int lectureId, Authentication connectedUser, int courseId) throws Exception {
        User user=((User) connectedUser.getPrincipal());
        Lecture lecture=lectureRepository.findById(lectureId).orElseThrow(
                ()->new IllegalStateException("Lecture not found")
        );
        if(!Objects.equals(user.getId(),lecture.getCourse().getOwner().getId())){
            throw new Exception("you are not authorize to delete this lecture");
        }
        String Lectureurlfile=fileUploadService.saveFile(request.getLectureUrl(),courseId);
        if(Lectureurlfile.isEmpty()){
            throw new Exception("some internal server error while uploading the file");
        }
        String LectureThumbnailfile=fileUploadService.saveFile(request.getLectureThumbnail(),courseId);
        if(LectureThumbnailfile.isEmpty()){
            throw new Exception("some internal server error while uploading the file");
        }
        lecture.setLectureTitle(request.getLectureTitle());
        lecture.setLectureDescription(request.getLectureDescription());
        lecture.setLectureUrl(Lectureurlfile);
        lecture.setLectureThumbnail(LectureThumbnailfile);
        lectureRepository.save(lecture);
        return "lecture updated successfully";
    }

    public PageResponse<LectureResponse> getLectureofAllCourse(int courseId, Authentication connectedUser,int page,int size) {
        User user=((User) connectedUser.getPrincipal());
        Pageable pageable= PageRequest.of(page,size, Sort.by("creationDate"));
        Page<Lecture> lectures=lectureRepository.findAllbyCourse(pageable,courseId);
        List<LectureResponse> allectures=lectures.stream()
                .map(lectureMapper::toLectureResponse)
                .toList();
        return new PageResponse<>(
                allectures,
                lectures.getNumber(),
                lectures.getSize(),
                lectures.getTotalElements(),
                lectures.getTotalPages(),
                lectures.isFirst(),
                lectures.isLast()
        );
    }
}
