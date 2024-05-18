import java.io.*;
import java.util.*;

public class DataParser {

    public static Map<Integer, List<Integer>> parseStudents(String filename) throws IOException {
        Map<Integer, List<Integer>> studentExams = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        int studentId = 0;

        while ((line = reader.readLine()) != null) {
            String[] exams = line.split(" ");
            List<Integer> examList = new ArrayList<>();
            for (String exam : exams) {
                examList.add(Integer.parseInt(exam));
            }
            studentExams.put(studentId++, examList);
        }
        reader.close();
        return studentExams;
    }

    public static Map<Integer, Integer> parseCourses(String filename) throws IOException {
        Map<Integer, Integer> courses = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        int courseId = 0;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");
            courses.put(courseId++, Integer.parseInt(parts[1]));
        }
        reader.close();
        return courses;
    }

    public static void main(String[] args) throws IOException {
        String studentFile = "kfu-s-93.stu";
        String courseFile = "kfu-s-93.crs";

        Map<Integer, List<Integer>> studentExams = parseStudents(studentFile);
        Map<Integer, Integer> courses = parseCourses(courseFile);

        System.out.println("Student Exams: " + studentExams);
        System.out.println("Courses: " + courses);
    }
}
