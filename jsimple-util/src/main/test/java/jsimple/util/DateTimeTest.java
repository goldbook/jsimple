package jsimple.util;

import jsimple.unit.UnitTest;
import org.junit.Test;

/**
 * @author Bret Johnson
 * @author Bong Kwon
 * @since 5/5/13 8:06 PM
 */
public class DateTimeTest extends UnitTest {
    @Test public void testParseRFC3339DateTime() {
        validateRFC3339Parsing("2007-05-01T15:43:26Z");
        validateRFC3339Parsing("2007-05-01T15:43:26.0Z", "2007-05-01T15:43:26Z");
        validateRFC3339Parsing("2007-05-01T15:43:26.00000Z", "2007-05-01T15:43:26Z");
        validateRFC3339Parsing("2007-05-01T15:43:26.00001Z", "2007-05-01T15:43:26Z");

        // Test rounding milliseconds
        validateRFC3339Parsing("2007-05-01T15:43:26.0004Z", "2007-05-01T15:43:26Z");
        validateRFC3339Parsing("2007-05-01T15:43:26.0005Z", "2007-05-01T15:43:26.001Z");
        validateRFC3339Parsing("2007-05-01T15:43:26.0254Z", "2007-05-01T15:43:26.025Z");
        validateRFC3339Parsing("2007-05-01T15:43:26.0255Z", "2007-05-01T15:43:26.026Z");
        validateRFC3339Parsing("2007-05-01T15:43:26.0256Z", "2007-05-01T15:43:26.026Z");

        // Test various number of leading zeros in milliseconds
        validateRFC3339Parsing("2007-05-01T15:43:26.010Z");
        validateRFC3339Parsing("2007-05-01T15:43:26.01Z", "2007-05-01T15:43:26.010Z");
        validateRFC3339Parsing("2007-05-01T15:43:26.099Z");
        validateRFC3339Parsing("2007-05-01T15:43:26.100Z");
        validateRFC3339Parsing("2007-05-01T15:43:26.999Z");

        // Test various number of leading zeros in other elements
        validateRFC3339Parsing("2007-01-01T00:00:00Z");
        validateRFC3339Parsing("2007-09-01T00:00:00Z");
        validateRFC3339Parsing("2007-10-01T00:00:00Z");
        validateRFC3339Parsing("2007-01-01T00:00:00Z");
        validateRFC3339Parsing("2007-01-09T00:00:00Z");
        validateRFC3339Parsing("2007-01-10T00:00:00Z");
        validateRFC3339Parsing("2007-01-01T00:00:00Z");
        validateRFC3339Parsing("2007-01-01T09:00:00Z");
        validateRFC3339Parsing("2007-01-01T10:00:00Z");
        validateRFC3339Parsing("2007-01-01T23:00:00Z");
        validateRFC3339Parsing("2007-01-01T00:00:00Z");
        validateRFC3339Parsing("2007-01-01T00:09:00Z");
        validateRFC3339Parsing("2007-01-01T00:10:00Z");
        validateRFC3339Parsing("2007-01-01T00:59:00Z");
        validateRFC3339Parsing("2007-01-01T00:00:09Z");
        validateRFC3339Parsing("2007-01-01T00:00:10Z");
        validateRFC3339Parsing("2007-01-01T00:00:59Z");

        // Test time zone parsing
        validateRFC3339Parsing("2007-01-01T00:00:00-01:00", "2007-01-01T01:00:00Z");
        validateRFC3339Parsing("2007-01-01T00:00:00+01:00", "2006-12-31T23:00:00Z");
        validateRFC3339Parsing("2007-01-01T00:00:00-01:15", "2007-01-01T01:15:00Z");
        validateRFC3339Parsing("2007-01-01T00:00:00-01:30", "2007-01-01T01:30:00Z");

        validateRFC3339Parsing("2007-01-01T12:00:00+05:01", "2007-01-01T06:59:00Z");
        validateRFC3339Parsing("2007-01-01T14:00:00+13:00", "2007-01-01T01:00:00Z");
        validateRFC3339Parsing("2007-01-01T14:00:00.1289+13:00", "2007-01-01T01:00:00.129Z");

        validateRFC3339Parsing("2007-01-01T14:02:03.1289-13:00", "2007-01-02T03:02:03.129Z");

        validateRFC3339Parsing("2007-01-01T14:02:03+00:00", "2007-01-01T14:02:03Z");
        validateRFC3339Parsing("2007-01-01T14:02:03-00:00", "2007-01-01T14:02:03Z");
        validateRFC3339Parsing("2007-01-01T14:02:03.0000-00:00", "2007-01-01T14:02:03Z");

        /*
        validateRFC3339Parsing("2007-05-01T15:43:26-07:00");

        assertEquals(expected, Utils.parseRFC3339Long("2007-05-01T15:43:26-07:00"));

        System.out.println(Utils.parseRFC3339Date("2007-05-01T15:43:26.3-07:00"));
        System.out.println(Utils.parseRFC3339Long("2007-05-01T15:43:26.3-07:00"));

        expected = Long.parseLong("1178059406003");
        assertEquals(expected, Utils.parseRFC3339Long("2007-05-01T15:43:26.3-07:00"));

        System.out.println(Utils.parseRFC3339Date("2007-05-01T15:43:26.3452-07:00"));
        System.out.println(Utils.parseRFC3339Long("2007-05-01T15:43:26.3452-07:00"));
        expected = Long.parseLong("1178059409452");
        assertEquals(expected, Utils.parseRFC3339Long("2007-05-01T15:43:26.3452-07:00"));

        System.out.println(Utils.parseRFC3339Date("2007-05-01T15:43:26.3452Z"));
        System.out.println(Utils.parseRFC3339Long("2007-05-01T15:43:26.3452Z"));

        System.out.println(Utils.parseRFC3339Date("2007-05-01T15:43:26.3Z"));
        System.out.println(Utils.parseRFC3339Long("2007-05-01T15:43:26.3Z"));

        System.out.println(Utils.parseRFC3339Date("2007-05-01T15:43:26Z"));
        System.out.println(Utils.parseRFC3339Long("2007-05-01T15:43:26Z"));
        */
    }

    void validateRFC3339Parsing(String dateTimeString) {
        DateTime dateTime = DateTime.parseRFC3339DateTime(dateTimeString);
        String regeneratedDateTimeString = dateTime.toRFC3339String();

        assertEquals(dateTimeString, regeneratedDateTimeString);
    }

    void validateRFC3339Parsing(String dateTimeString, String expectedRegeneratedString) {
        DateTime dateTime = DateTime.parseRFC3339DateTime(dateTimeString);
        String regeneratedDateTimeString = dateTime.toRFC3339String();

        assertEquals(expectedRegeneratedString, regeneratedDateTimeString);
    }

    /**
     * Run tests taken from http://ideone.com/7BADb
     */
    @Test public void testDateTimeFromMillis() {
        validateEpochSecondsToDateTime(-1, "1969-12-31T23:59:59Z");
        validateEpochSecondsToDateTime(0, "1970-01-01T00:00:00Z");
        validateEpochSecondsToDateTime(1, "1970-01-01T00:00:01Z");

        validateEpochSecondsToDateTime(946684799, "1999-12-31T23:59:59Z");
        validateEpochSecondsToDateTime(946684799 + 1, "2000-01-01T00:00:00Z");
        validateEpochSecondsToDateTime(946684799 + 2, "2000-01-01T00:00:01Z");

        validateEpochSecondsToDateTime(978307199, "2000-12-31T23:59:59Z");
        validateEpochSecondsToDateTime(978307199 + 1, "2001-01-01T00:00:00Z");
        validateEpochSecondsToDateTime(978307199 + 2, "2001-01-01T00:00:01Z");

        /*
        printf("%s",   asctime(SecondsSinceEpochToDateTime(&t, tt = time(0))));
        printf("%s",   asctime(gmtime(&tt)));


        Wed Dec 31 23:59:59 1969
        Thu Jan  1 00:00:00 1970
        Thu Jan  1 00:00:00 1970
        Thu Jan  1 00:00:01 1970

        Fri Dec 31 23:59:59 1999
        Sat Jan  1 00:00:00 2000
        Sat Jan  1 00:00:01 2000

        Sun Dec 31 23:59:59 2000
        Mon Jan  1 00:00:00 2001
        Mon Jan  1 00:00:01 2001

        Wed Apr 17 13:45:50 2013
        Wed Apr 17 13:45:50 2013
        */
    }

    private void validateEpochSecondsToDateTime(long secondsSinceEpoch, String expectedDatedTimeString) {
        long millis = secondsSinceEpoch * 1000;

        DateTime dateTime = new DateTime(millis);
        String dateTimeString = dateTime.toRFC3339String();
        assertEquals(expectedDatedTimeString, dateTimeString);

        assertEquals(millis, DateTime.parseRFC3339DateTime(dateTimeString).getMillis());
    }

    /**
     * Test round tripping all dates from roughly 1697 to 2243.
     */
    @Test public void testRoundTripRangeOfDates() {
        for (int daysSinceEpoch = -100000; daysSinceEpoch < 100000; ++daysSinceEpoch)
            validateRoundTrip(daysSinceEpoch);
    }

    private void validateRoundTrip(long daysSinceEpoch) {
        long millis = daysSinceEpoch * 24 * 3600 * 1000;
        assertEquals(millis, DateTime.parseRFC3339DateTime(new DateTime(millis).toRFC3339String()).getMillis());
    }

    @Test public void testGetDayOfWeek() {
        validateDayOfWeek("1969-12-28T00:01:00Z", 7);
        validateDayOfWeek("1969-12-29T00:00:00Z", 1);
        validateDayOfWeek("1969-12-30T11:59:59Z", 2);
        validateDayOfWeek("1969-12-31T03:59:59Z", 3);

        validateDayOfWeek("1969-12-31T23:59:59Z", 3);
        validateDayOfWeek("1970-01-01T00:00:00Z", 4);
        validateDayOfWeek("1970-01-01T00:00:01Z", 4);

        validateDayOfWeek("1999-12-31T23:59:59Z", 5);
        validateDayOfWeek("2000-01-01T00:00:00Z", 6);
        validateDayOfWeek("2000-01-01T00:00:01Z", 6);

        validateDayOfWeek("2000-12-31T23:59:59Z", 7);
        validateDayOfWeek("2001-01-01T00:00:00Z", 1);
        validateDayOfWeek("2001-01-01T00:00:01Z", 1);

        validateDayOfWeek("1601-01-01T00:00:01Z", 1);
        validateDayOfWeek("1832-08-01T00:00:01Z", 3);
        validateDayOfWeek("1832-10-14T00:00:01Z", 7);
        validateDayOfWeek("1832-10-15T00:00:01Z", 1);

        validateDayOfWeek("2013-05-05T23:00:01Z", 7);
    }

    private void validateDayOfWeek(String dateTimeString, int dayOfWeek) {
        assertEquals(dayOfWeek, DateTime.parseRFC3339DateTime(dateTimeString).getDayOfWeek());
    }
}
