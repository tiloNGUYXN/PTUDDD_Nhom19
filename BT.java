import java.util.*;

class Student {
    String firstName;
    String lastName;
    String birthdate;
    String address;
    String className;
    Map<String, Double> scores;
    double averageScore;
    String rank;

    public Student(String firstName, String lastName, String birthdate, String address, String className) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.address = address;
        this.className = className;
        this.scores = RandomScores();
        this.averageScore = calculate();
        this.rank = Rank();
    }

    private Map<String, Double> RandomScores() {
        Map<String, Double> scores = new HashMap<>();
        String[] subjects = {
            "Lap trinh huong doi tuong",
            "Quan ly du an",
            "Hoc May",
            "Co so du lieu",
            "Lap trinh ung dung cho TBDD"
        };
        Random rand = new Random();
        for (String subject : subjects) {
            scores.put(subject, 1 + rand.nextDouble() * 9); // Điểm từ 1.0 - 10.0
        }
        return scores;
    }

    private double calculate() {
        double total = 0;
        for (double score : scores.values()) {
            total += score;
        }
        return total / scores.size();
    }

    private String Rank() {
        if (averageScore >= 8.0) return "A";
        else if (averageScore >= 7.0) return "B";
        else if (averageScore >= 5.5) return "C";
        else if (averageScore >= 4.0) return "D";
        else return "<D";
    }

    public void display() {
        System.out.println(lastName + " " + firstName + " | Ngay sinh: " + birthdate + " | Dia chi: " + address + " | Lop: " + className);
        System.out.println("Diem cac mon hoc:");
        scores.forEach((subject, score) -> System.out.println("  " + subject + ": " + String.format("%.2f", score)));
        System.out.println("Trung binh: " + String.format("%.2f", averageScore) + " | Rank: " + rank);
        System.out.println("----------------------------------------------------");
    }
}

class ClassRoom {
    String className;
    List<Student> students;

    public ClassRoom(String className) {
        this.className = className;
        this.students = generateStudents(className);
    }

    private List<Student> generateStudents(String className) {
        List<Student> students = new ArrayList<>();
        String[] firstNames = {"Phong", "Tam", "Duc", "Duy", "Huy", "Khoa", "Linh", "Minh", "Ngoc", "Quang"};
        String[] lastNames = {"Nguyen", "Tran", "Le", "Pham", "Dang", "Bui", "Do", "Vu"};
        String[] addresses = {"Ha Noi", "TP.HCM", "Da Nang", "Hai Phong", "Can Tho"};

        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            String firstName = firstNames[rand.nextInt(firstNames.length)];
            String lastName = lastNames[rand.nextInt(lastNames.length)];
            String birthdate = (rand.nextInt(28) + 1) + "-" + (rand.nextInt(12) + 1) + "-" + (rand.nextInt(10) + 1996);
            String address = addresses[rand.nextInt(addresses.length)];
            students.add(new Student(firstName, lastName, birthdate, address, className));
        }
        return students;
    }

    // Đếm số sinh viên theo rank
    private Map<String, Long> countRank() {
        Map<String, Long> rankCount = new HashMap<>();
        for (Student student : students) {
            rankCount.put(student.rank, rankCount.getOrDefault(student.rank, 0L) + 1);
        }
        return rankCount;
    }

    public void displayClass() {
        System.out.println("Lop: " + className);
        students.forEach(Student::display);

        Map<String, Long> rankCount = countRank();
        System.out.println("Tong ket:");
        rankCount.forEach((rank, count) -> System.out.println(rank + ": " + count));
    }
}

public class BT {
    public static void main(String[] args) {
        List<ClassRoom> classList = Arrays.asList(
            new ClassRoom("CNTT1"),
            new ClassRoom("CNTT2"),
            new ClassRoom("CNTT3")
        );

        Scanner scanner = new Scanner(System.in);
        System.out.println("Danh sach lop hoc:");
        classList.forEach(cls -> System.out.println("- " + cls.className));

        System.out.print("Nhap ma lop: ");
        String classCode = scanner.nextLine();

        Optional<ClassRoom> selectedClass = classList.stream()
            .filter(cls -> cls.className.equalsIgnoreCase(classCode))
            .findFirst();

        if (selectedClass.isPresent()) {
            selectedClass.get().displayClass();
        } else {
            System.out.println("Khong tim thay lop.");
        }

        scanner.close();
    }
}
