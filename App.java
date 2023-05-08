import com.github.kadika38.J2J;

public class App {

    public static void main(String[] args) {
        J2J j2j = new J2J();
        String s = j2j.getJson();
        J2J.convert(s);
    }
}
