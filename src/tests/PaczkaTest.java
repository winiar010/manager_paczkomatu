package tests;

import components.Kurier;
import components.Paczka;
import components.Sortownia;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class PaczkaTest {

    private Paczka p;

    @BeforeEach
    public void setUp() throws Exception {
        Thread.sleep(200);
    }

    @Test
    @DisplayName("Generate 24 digit pack code")
    public void shouldReturn24DigitPackCode() {
        String packCode = Paczka.generateInt();
        int dlugoscPackCode = packCode.length();
        assertEquals(24,dlugoscPackCode);
    }

    @Test
    @DisplayName("Generate 6 digit open code")
    public void shouldReturn6DigitOpenCode() {
        String openCode = Paczka.openCode();
        int dlugoscOpenCode = openCode.length();
        assertEquals(6,dlugoscOpenCode);
    }


    //CustomerDelivering
    @Test
    @DisplayName("Pay for box machine parcel")
    public void shouldSetCustomerDeliveringStatus() throws IOException {
        String input = "630552709503443737832598";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals("Paczka o numerze: 630552709503443737832598 została poprawnie opłacona.", Paczka.setCustomerDelivering());
    }

    @Test
    @DisplayName("Pay for box machine parcel - wrong parcel status")
    public void shouldReturnCustomerDeliveringExceptionWrongStatus() throws IOException {
        String input = "630556853099097116042343";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals("Paczka znajduje się w statusie CustomerDelivering i nie może zostać opłacona. Paczka powinna być w statusie Created.", Paczka.setCustomerDelivering());
    }

    @Test
    @DisplayName("Pay for box machine parcel - no packCode in database")
    public void shouldReturnCustomerDeliveringExceptionNoParcel() throws IOException {
        String input = "6305527095034437378325981";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals("Podana paczka nie istnieje w bazie!", Paczka.setCustomerDelivering());
    }

    //CustomerStored
    @Test
    @DisplayName("Client sent parcel in parcel locker")
    public void shouldSetCustomerStoredStatus() throws IOException {
        String input = "630556853099097116015812";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals("Paczka o numerze: 630556853099097116015812 została poprawnie nadana w paczkomacie. Możesz śledzić jej status w naszej aplikacji!", Paczka.setCustomerStored());
    }

    @Test
    @DisplayName("Client sent parcel in parcel locker - wrong parcel status")
    public void shouldReturnCustomerStoredExceptionWrongStatus() throws IOException {
        String input = "630551544658211423545545";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals("Paczka znajduje się w statusie CustomerStored i nie może zostać nadana. Paczka powinna być w statusie CustomerDelivering.", Paczka.setCustomerStored());
    }

    @Test
    @DisplayName("Client sent parcel in parcel locker - no packCode in database")
    public void shouldReturnCustomerStoredExceptionNoParcel() throws IOException {
        String input = "6305568530990971160158121";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals("Podana paczka nie istnieje w bazie!", Paczka.setCustomerStored());
    }

    //CustomerSent
    @Test
    @DisplayName("Courier received parcel from parcel locker")
    public void shouldSetCustomerSentStatus() throws IOException {
        String input = "630551544658211083511173";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals("Paczka o numerze: 630551544658211083511173 została odebrana przez kuriera i wyruszyła w dalszą drogę.", Kurier.setCustomerSent());
    }

    @Test
    @DisplayName("Courier received parcel from parcel locker - wrong parcel status")
    public void shouldReturnCustomerSentExceptionWrongStatus() throws IOException {
        String input = "630552709503443545454545";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals("Paczka znajduje się w statusie CustomerSent i nie może zostać odebrana. Paczka powinna być w statusie CustomerStored.", Kurier.setCustomerSent());
    }

    @Test
    @DisplayName("Courier received parcel from parcel locker - no packCode in database")
    public void shouldReturnCustomerSentExceptionNoParcel() throws IOException {
        String input = "6305515446582110835111731";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals("Podana paczka nie istnieje w bazie!", Kurier.setCustomerSent());
    }

    //PosortowanaWSortowaniNadawczej
    @Test
    @DisplayName("Parcel sorted in the sender sorting plant")
    public void shouldSetPosortowanaWSortowaniNadawczejStatus() throws IOException {
        String input = "630552709503443737832123";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals("Paczka o numerze: 630552709503443737832123 została poprawnie posortowana w sortowni nadawczej.", Sortownia.setPosortowanaWSortowaniNadawczej());
    }

    @Test
    @DisplayName("Parcel sorted in the sender sorting plant - wrong parcel status")
    public void shouldReturnPosortowanaWSortowaniNadawczejExceptionWrongStatus() throws IOException {
        String input = "630551544658211083266727";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals("Paczka znajduje się w statusie PosortowanaWSortowaniNadawczej i nie może zostać posortowana. Paczka powinna być w statusie CustomerSent.", Sortownia.setPosortowanaWSortowaniNadawczej());
    }

    @Test
    @DisplayName("Parcel sorted in the sender sorting plant - no packCode in database")
    public void shouldReturnPosortowanaWSortowaniNadawczejExceptionNoParcel() throws IOException {
        String input = "6305527095034437378321231";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals("Podana paczka nie istnieje w bazie!", Sortownia.setPosortowanaWSortowaniNadawczej());
    }

    //InTransit
    @Test
    @DisplayName("Parcel is on its way to the receiving sorting")
    public void shouldSetInTransitStatus() throws IOException {
        String input = "630551544658211083432434";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals("Paczka o numerze: 630551544658211083432434 została poprawnie przekazana do sortowni odbiorczej.", Kurier.setInTransit());
    }

    @Test
    @DisplayName("Parcel is on its way to the receiving sorting - wrong parcel status")
    public void shouldReturnInTransitExceptionWrongStatus() throws IOException {
        String input = "630552709503443738432845";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals("Paczka znajduje się w statusie InTransit i nie może zostać przekazana do sortowni nadawczej. Paczka powinna być w statusie PosortowanaWSortowaniNadawczej.", Kurier.setInTransit());
    }

    @Test
    @DisplayName("Parcel is on its way to the receiving sorting - no packCode in database")
    public void shouldReturnInTransitExceptionNoParcel() throws IOException {
        String input = "6305515446582110834324341";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals("Podana paczka nie istnieje w bazie!", Kurier.setInTransit());
    }

    //PosortowanaWSortowaniOdbiorczej
    @Test
    @DisplayName("Parcel is on its way to the receiving sorting")
    public void shouldSetPosortowanaWSortowaniOdbiorczejStatus() throws IOException {
        String input = "630552709503443737837654";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals("Paczka o numerze: 630552709503443737837654 została poprawnie posortowana w sortowni odbiorczej.", Sortownia.setPosortowanaWSortowaniOdbiorczej());
    }

    @Test
    @DisplayName("Parcel is on its way to the receiving sorting - wrong parcel status")
    public void shouldReturnPosortowanaWSortowaniOdbiorczejExceptionWrongStatus() throws IOException {
        String input = "630556853099097117676678";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals("Paczka znajduje się w statusie PosortowanaWSortowaniOdbiorczej i nie może zostać posortowana. Paczka powinna być w statusie InTransit.", Sortownia.setPosortowanaWSortowaniOdbiorczej());
    }

    @Test
    @DisplayName("Parcel is on its way to the receiving sorting - no packCode in database")
    public void shouldReturnPosortowanaWSortowaniOdbiorczejExceptionNoParcel() throws IOException {
        String input = "6305527095034437378376541";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals("Podana paczka nie istnieje w bazie!", Sortownia.setPosortowanaWSortowaniOdbiorczej());
    }

    //DoDoreczenia
    @Test
    @DisplayName("Parcel released for delivery")
    public void shouldSetDoDoreczeniaStatus() throws IOException {
        String input = "630556853099097116034343";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals("Paczka o numerze: 630556853099097116034343 została poprawnie wydana do doręczenia.", Sortownia.setDoDoreczenia());
    }

    @Test
    @DisplayName("Parcel released for delivery - wrong parcel status")
    public void shouldReturnDoDoreczeniaExceptionWrongStatus() throws IOException {
        String input = "630551544658211085885337";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals("Paczka znajduje się w statusie DoDoreczenia i nie może zostać wydana do doręczenia. Paczka powinna być w statusie PosortowanaWSortowaniOdbiorczej.", Sortownia.setDoDoreczenia());
    }

    @Test
    @DisplayName("Parcel released for delivery - no packCode in database")
    public void shouldReturnDoDoreczeniaExceptionNoParcel() throws IOException {
        String input = "6305568530990971160343431";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals("Podana paczka nie istnieje w bazie!", Sortownia.setDoDoreczenia());
    }

    //Stored
    @Test
    @DisplayName("Parcel is stored in parcel locker")
    public void shouldSetStoredStatus() throws IOException {
        String input = "630551544658211085656673";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals("Paczka o numerze: 630551544658211085656673 została poprawnie umieszczona w paczkomacie przez kuriera.", Kurier.setStored());
    }

    @Test
    @DisplayName("Parcel is stored in parcel locker - wrong parcel status")
    public void shouldReturnStoredExceptionWrongStatus() throws IOException {
        String input = "630556853099097116576768";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals("Paczka znajduje się w statusie Stored i nie może zostać umieszczona w paczkomacie. Paczka powinna być w statusie DoDoreczenia.", Kurier.setStored());
    }

    @Test
    @DisplayName("Parcel is stored in parcel locker - no packCode in database")
    public void shouldReturnStoredExceptionNoParcel() throws IOException {
        String input = "6305515446582110856566731";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals("Podana paczka nie istnieje w bazie!", Kurier.setStored());
    }

    //Problem z podwójnym inputem w teście: 1 input - packCode, 2 input - openCode
    @Test
    @DisplayName("Parcel received from parcel locker by customer")
    public void shouldSetDeliveredStatus() throws IOException {
        String input = "630556853099097116546738";
        String openCode = "788903";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        InputStream kod = new ByteArrayInputStream(openCode.getBytes());
        System.setIn(in);
        System.setIn(kod);
//        in.reset();
//        String openCode = "788903";
//        InputStream kod = new ByteArrayInputStream(openCode.getBytes());
//        System.setIn(kod);

        assertEquals("Paczka o numerze: 630556853099097116546738 została poprawnie odebrana. Dziękujemy za skorzystanie z naszych usług!", Paczka.setDelivered());
    }

}
