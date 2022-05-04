package info.kgeorgiy.ja.Beliaev.student;

import info.kgeorgiy.java.advanced.student.GroupName;
import info.kgeorgiy.java.advanced.student.Student;
import info.kgeorgiy.java.advanced.student.StudentQuery;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentDB implements StudentQuery {

    private static final Comparator<Student> STUDENT_NAME_COMPARATOR = Comparator
            .comparing(Student::getLastName)
            .thenComparing(Student::getFirstName)
            .reversed().thenComparing(Student::getId);

    private <T> Stream<T> mapStream(Collection<Student> students, Function<Student, T> mapFunction) {
        return students
                .stream()
                .map(mapFunction);
    }

    private List<Student> filterSortCollectStream(Collection<Student> students, Predicate<? super Student> studentPredicate) {
        return students
                .stream()
                .filter(studentPredicate)
                .sorted(STUDENT_NAME_COMPARATOR)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getFirstNames(List<Student> students) {
        return mapStream(students, Student::getFirstName)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getLastNames(List<Student> students) {
        return mapStream(students, Student::getLastName)
                .collect(Collectors.toList());
    }

    @Override
    public List<GroupName> getGroups(List<Student> students) {
        return mapStream(students, Student::getGroup)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getFullNames(List<Student> students) {
        return mapStream(students, student -> student.getFirstName() + " " + student.getLastName())
                .collect(Collectors.toList());
    }

    @Override
    @SuppressWarnings("all")
    public Set<String> getDistinctFirstNames(List<Student> students) {
        return mapStream(students, Student::getFirstName)
                .sorted() // Collectors.toCollection(TreeSet::new)
                .collect(Collectors.toSet());
    }

    @Override
    public String getMaxStudentFirstName(List<Student> students) {
        return students
                .stream()
                .max(Student::compareTo)
                .map(Student::getFirstName)
                .orElse("");
    }

    @Override
    public List<Student> sortStudentsById(Collection<Student> students) {
        return students
                .stream()
                .sorted(Student::compareTo)
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> sortStudentsByName(Collection<Student> students) {
        return students
                .stream()
                .sorted(STUDENT_NAME_COMPARATOR)
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
        return filterSortCollectStream(students, student -> student.getFirstName().equals(name));
    }

    @Override
    public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
        return filterSortCollectStream(students, student -> student.getLastName().equals(name));
    }

    @Override
    public List<Student> findStudentsByGroup(Collection<Student> students, GroupName group) {
        return filterSortCollectStream(students, student -> student.getGroup().equals(group));
    }

    @Override
    public Map<String, String> findStudentNamesByGroup(Collection<Student> students, GroupName group) {
        List<Student> studentsOfGroup = findStudentsByGroup(students, group);
        return studentsOfGroup
                .stream()
                .collect(Collectors.toMap(Student::getLastName, Student::getFirstName, // BinaryOperator.maxBy(....)
                        (existing, replacement) -> existing.compareTo(replacement) < 0 ? existing : replacement));
    }
}
