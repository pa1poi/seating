package com.pavanseatin.demo.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pavanseatin.demo.entity.Exam;
import com.pavanseatin.demo.entity.Room;
import com.pavanseatin.demo.entity.Student;
import com.pavanseatin.demo.entity.Teacher;
import com.pavanseatin.demo.repository.ExamRepository;
import com.pavanseatin.demo.repository.RoomRepository;
import com.pavanseatin.demo.repository.StudentRepository;
import com.pavanseatin.demo.repository.TeacherRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExcelService {

    private final StudentRepository studentRepo;
    private final RoomRepository roomRepo;
    private final ExamRepository examRepo;
    private final TeacherRepository teacherRepo;

    // =====================================================
    // STUDENTS
    // =====================================================
    public void uploadStudents(MultipartFile file) throws Exception {

        Workbook wb = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = wb.getSheetAt(0);

        List<Student> list = new ArrayList<>();
        DataFormatter fmt = new DataFormatter();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {

            Row r = sheet.getRow(i);
            if (r == null) continue;

            Student s = new Student();

            s.setRollno((int) r.getCell(0).getNumericCellValue());
            s.setAttendance(r.getCell(1).getNumericCellValue());
            s.setCourse(fmt.formatCellValue(r.getCell(2)));
            s.setBranch(fmt.formatCellValue(r.getCell(3)));
            s.setYear((int) r.getCell(4).getNumericCellValue());
            s.setSemester((int) r.getCell(5).getNumericCellValue());
            s.setMailId(fmt.formatCellValue(r.getCell(6)));
            s.setEligible(true);

            list.add(s);
        }

        studentRepo.saveAll(list);
        wb.close();
    }


    // =====================================================
    // ROOMS
    // =====================================================
    public void uploadRooms(MultipartFile file) throws Exception {

        Workbook wb = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = wb.getSheetAt(0);

        List<Room> list = new ArrayList<>();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {

            Row r = sheet.getRow(i);
            if (r == null) continue;

            Room room = new Room();

            room.setRoomNo((int) r.getCell(0).getNumericCellValue());
            room.setRowsCount((int) r.getCell(1).getNumericCellValue());
            room.setBenchesPerRow((int) r.getCell(2).getNumericCellValue());
            room.setSeatsPerBench((int) r.getCell(3).getNumericCellValue());
            room.setOccupStat(true);

            list.add(room);
        }

        roomRepo.saveAll(list);
        wb.close();
    }


    // =====================================================
    // TEACHERS
    // =====================================================
    public void uploadTeachers(MultipartFile file) throws Exception {

        Workbook wb = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = wb.getSheetAt(0);

        List<Teacher> list = new ArrayList<>();
        DataFormatter fmt = new DataFormatter();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {

            Row r = sheet.getRow(i);
            if (r == null) continue;

            Teacher t = new Teacher();

            t.setTeacherCode(fmt.formatCellValue(r.getCell(0))); // PK
            t.setName(fmt.formatCellValue(r.getCell(1)));
            t.setEmail(fmt.formatCellValue(r.getCell(2)));
            t.setDepartment(fmt.formatCellValue(r.getCell(3)));
            t.setPhone(fmt.formatCellValue(r.getCell(4)));

            list.add(t);
        }

        teacherRepo.saveAll(list);
        wb.close();
    }


    // =====================================================
    // EXAM
    // =====================================================
    public void uploadExam(MultipartFile file) throws Exception {

        Workbook wb = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = wb.getSheetAt(0);

        List<Exam> list = new ArrayList<>();
        DataFormatter fmt = new DataFormatter();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {

            Row r = sheet.getRow(i);
            if (r == null) continue;

            Exam e = new Exam();

            e.setSubjectCode(fmt.formatCellValue(r.getCell(0)));
            e.setSubjectName(fmt.formatCellValue(r.getCell(1)));

            Cell dateCell = r.getCell(2);
            LocalDate date;

            if (dateCell.getCellType() == CellType.NUMERIC) {
                date = dateCell.getDateCellValue()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
            } else {
                date = LocalDate.parse(fmt.formatCellValue(dateCell),
                        DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            }

            e.setExamDate(date);
            e.setExamTime(fmt.formatCellValue(r.getCell(3)));

            list.add(e);
        }

        examRepo.saveAll(list);
        wb.close();
    }
}
