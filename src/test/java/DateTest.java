import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTest {
    public static void main(String[] args) {
        System.out.println(new Date());

        var dateTime = ZonedDateTime.now();
        var f = DateTimeFormatter.ofPattern("E MMM dd hh:mm:ss zzz yyyy");
        System.out.println(f.format(dateTime));

        System.out.println(dateTime.format(DateTimeFormatter.RFC_1123_DATE_TIME));
    }
}
