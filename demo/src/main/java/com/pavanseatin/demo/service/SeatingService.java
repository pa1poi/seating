package com.pavanseatin.demo.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pavanseatin.demo.dto.AllocationRequest;
import com.pavanseatin.demo.entity.Room;
import com.pavanseatin.demo.entity.Seating;
import com.pavanseatin.demo.entity.Student;
import com.pavanseatin.demo.entity.Teacher;
import com.pavanseatin.demo.entity.TeacherAllocation;
import com.pavanseatin.demo.repository.RoomRepository;
import com.pavanseatin.demo.repository.SeatingRepository;
import com.pavanseatin.demo.repository.StudentRepository;
import com.pavanseatin.demo.repository.TeacherAllocationRepository;
import com.pavanseatin.demo.repository.TeacherRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeatingService {

    private final StudentRepository studentRepo;
    private final RoomRepository roomRepo;
    private final SeatingRepository seatingRepo;
    private final TeacherRepository teacherRepo;
    private final TeacherAllocationRepository teacherAllocationRepo;

    public String allocateSeats(AllocationRequest req) {

        System.out.println("===== ALLOCATION STARTED =====");

        seatingRepo.deleteAll();
        teacherAllocationRepo.deleteAll();

        String course = req.getCourse().trim().toUpperCase();

        List<Student> students =
                studentRepo.findBySemesterAndYearAndCourse(
                        req.getSemester(),
                        req.getYear(),
                        course
                );

        List<Room> rooms = roomRepo.findAll();
        List<Teacher> teachers = teacherRepo.findAll();

        System.out.println("Students Found: " + students.size());
        System.out.println("Rooms Found: " + rooms.size());
        System.out.println("Teachers Found: " + teachers.size());

        if (students.isEmpty()) {
            return "No students found for selected criteria ❌";
        }

        // SORT
        students.sort(
                Comparator.comparing(Student::getCourse)
                        .thenComparing(Student::getBranch)
                        .thenComparing(Student::getRollno)
        );

        int studentIndex = 0;
        int studentsPerBench = req.getStudentsPerBench();
        int allocatedCount = 0;

        // ================= STUDENT SEATING =================
        for (Room room : rooms) {

            for (int r = 1; r <= room.getRowsCount(); r++) {

                for (int b = 1; b <= room.getBenchesPerRow(); b++) {

                    for (int seatPos = 1; seatPos <= studentsPerBench; seatPos++) {

                        if (studentIndex >= students.size())
                            break;

                        Student st = students.get(studentIndex++);

                        Seating seat = new Seating();

                        seat.setRollno(st.getRollno());
                        seat.setRoomNo(room.getRoomNo());
                        seat.setRowNo(r);
                        seat.setBenchNo(b);
                        seat.setSeatNo(seatPos);

                        if (seatPos == 1)
                            seat.setSide("LEFT");
                        else if (seatPos == 2)
                            seat.setSide("RIGHT");
                        else
                            seat.setSide("CENTER");

                        seatingRepo.save(seat);
                        allocatedCount++;
                    }
                }
            }
        }

        System.out.println("Students Allocated: " + allocatedCount);

        // ================= TEACHER ALLOCATION =================
        int teacherIndex = 0;

        for (Room room : rooms) {

            if (teacherIndex >= teachers.size())
                break;

            Teacher teacher = teachers.get(teacherIndex++);

            TeacherAllocation ta = new TeacherAllocation();
            ta.setTeacher(teacher);
            ta.setRoomNo(room.getRoomNo());

            teacherAllocationRepo.save(ta);
        }

        System.out.println("===== ALLOCATION COMPLETE =====");

        return "Allocation Completed Successfully ✅";
    }
}