import com.github.kadika38.J2J;

public class App {

    public static void main(String[] args) {
        J2J j2j = new J2J();
        String s = j2j.getJson();
        System.out.println(s);
        String j = j2j.convert(s);
        System.out.println(j);
    }
}
