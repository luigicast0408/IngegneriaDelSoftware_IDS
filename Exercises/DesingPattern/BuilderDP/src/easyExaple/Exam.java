package easyExaple;

import java.util.Date;

public class Exam {
    private final String courseName;
    private final String professor;
    private final int CFU;
    private final Date date;
    private final int voto;
    private final boolean laude;

    Exam(ExamBuilder examBuilder) {
        this.laude = examBuilder.laude;
        this.voto = examBuilder.vote;
        this.date = examBuilder.date;
        this.CFU = examBuilder.CFU;
        this.professor = examBuilder.professor;
        this.courseName = examBuilder.courseName;
    }

    public String getCourseName() { return courseName; }
    public String getProfessor() { return professor; }
    public int getCFU() { return CFU; }
    public Date getDate() { return date; }
    public int getVoto() { return voto; }
    public boolean isLaude() { return laude; }

    @Override
    public String toString() {
        return "Exam{" +
                "courseName='" + courseName + '\'' +
                ", professor='" + professor + '\'' +
                ", CFU=" + CFU +
                ", date=" + date +
                ", voto=" + voto +
                ", laude=" + laude +
                '}';
    }
    public static  class ExamBuilder  implements IBuilder{
        private String courseName;
        private String professor;
        private int CFU;
        private Date date;
        private int vote;
        private boolean laude;

        public ExamBuilder setCourseName(String courseName) {
            this.courseName = courseName;
            return this;
        }

        public ExamBuilder setProfessor(String professor) {
            this.professor = professor;
            return this;
        }

        public ExamBuilder setCFU(int CFU) {
            this.CFU = CFU;
            return this;
        }

        public ExamBuilder setDate(Date date) {
            this.date = date;
            return this;
        }

        public ExamBuilder setVote(int vote) {
            this.vote = vote;
            return this;
        }

        public ExamBuilder setLaude(boolean laude) {
            this.laude = laude;
            return this;
        }

        @Override
        public Exam build() {
            return new Exam(this);
        }
    }
}
